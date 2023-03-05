/*
 * Copyright 2023 Byte Sculptor Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.bytesculptor.applib.iap

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.billingclient.api.*
import kotlinx.coroutines.*

/**
 * The [BillingClientWrapper] isolates the Google Play Billing's [BillingClient] methods needed
 * to have a simple implementation and emits responses to the data repository for processing.
 *
 */
class BillingClientWrapper(
    context: Context
) : PurchasesUpdatedListener, ProductDetailsResponseListener {

    // New ProductDetails
    private var _productWithProductDetails = MutableLiveData<Map<String, ProductDetails>>()
    val productWithProductDetails: LiveData<Map<String, ProductDetails>> get() = _productWithProductDetails

    // Current Purchases
    private val _purchases = MutableLiveData<List<Purchase>>()
    val purchases: LiveData<List<Purchase>> get() = _purchases

    private val _newPurchase = MutableLiveData<List<Purchase>>(listOf())
    val newPurchase: LiveData<List<Purchase>> get() = _newPurchase

    // Tracks new purchases acknowledgement state.
    // Set to true when a purchase is acknowledged and false when not.
    private val _isNewPurchaseAcknowledged = MutableLiveData<Purchase?>()
    val isNewPurchaseAcknowledged: LiveData<Purchase?> get() = _isNewPurchaseAcknowledged

    private var billingConnectionsRetryCounter = 0

    var billingConnectionResponseCode = -100

    // Initialize the BillingClient.
    private val billingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    // Establish a connection to Google Play.
    fun startBillingConnection(billingConnectionState: MutableLiveData<Boolean>) {

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                billingConnectionResponseCode = billingResult.responseCode
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    Log.d(TAG, "IAP Billing connection response OK")
                    billingConnectionState.postValue(true)
                    queryPurchaseHistory()
                    queryPurchases()
                    // queryProductDetails()
                } else {
                    billingConnectionState.postValue(false)
                    Log.d(
                        TAG,
                        "Failed connection: " + billingResult.debugMessage + " :: " + billingResult.responseCode
                    )
                }
            }

            override fun onBillingServiceDisconnected() {
                Log.d(TAG, "IAP Billing connection disconnected")
                billingConnectionState.postValue(false)
                billingConnectionsRetryCounter++
                if (billingConnectionsRetryCounter < 3) {
                    startBillingConnection(billingConnectionState)
                } else {
                    Log.d(TAG, "IAP Billing connection disconnected: more than 3 retries")
                }
            }
        })
    }

    // Query Google Play Billing for existing purchases.
    // New purchases will be provided to PurchasesUpdatedListener.onPurchasesUpdated().
    fun queryPurchases() {
        if (!billingClient.isReady) {
            Log.d(TAG, "IAP Billing Test: queryPurchases: BillingClient is not ready")
            return
        }
        // Query for existing products that have been purchased.
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.INAPP)
                .build()
        ) { billingResult, purchaseList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (purchaseList.isNotEmpty()) {
                    Log.d(TAG, "IAP onPurchase response: $purchaseList")
                    _purchases.postValue(purchaseList)
                } else {
                    Log.d(TAG, "IAP onPurchase response 2: $purchaseList")
                    _purchases.postValue(emptyList())
                }
            } else {
                Log.e(TAG, billingResult.debugMessage)
            }
        }
    }

    fun queryPurchaseHistory() {
        if (!billingClient.isReady) {
            Log.d(TAG, "IAP Billing Test: queryPurchases: BillingClient is not ready")
            return
        }
        val params = QueryPurchaseHistoryParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)

        billingClient.queryPurchaseHistoryAsync(
            params.build()
        ) { p0, p1 ->
            Log.d(TAG, "IAP onPurchaseHistoryResponse: $p0 - $p1")
        }
    }

    // Query Google Play Billing for products available to sell and present them in the UI
    fun queryProductDetails(prodId: List<String>) {
        val productList: MutableList<QueryProductDetailsParams.Product> = mutableListOf()
        for (item in prodId) {
            productList.add(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(item)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build()
            )
        }

        val params = QueryProductDetailsParams.newBuilder().setProductList(productList)

        billingClient.queryProductDetailsAsync(params.build(), this)

        Log.d(TAG, "IAP queryProductDetails: ")
    }

    // [ProductDetailsResponseListener] implementation
    // Listen to response back from [queryProductDetails] and emits the results
    // to [_productWithProductDetails].
    override fun onProductDetailsResponse(
        billingResult: BillingResult,
        productDetailsList: MutableList<ProductDetails>
    ) {
        val responseCode = billingResult.responseCode
        val debugMessage = billingResult.debugMessage
        Log.d(
            TAG,
            "IAP Billing: onProductDetailsResponse() code: $responseCode - message: $debugMessage"
        )
        when (responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                var newMap: Map<String, ProductDetails> = HashMap()
                if (productDetailsList.isNullOrEmpty()) {
                    Log.e(
                        TAG,
                        "IAP onProductDetailsResponse: " +
                                "Found null or empty ProductDetails. " +
                                "Check to see if the Products you requested are correctly " +
                                "published in the Google Play Console."
                    )
                } else {
                    newMap = productDetailsList.associateBy {
                        it.productId
                    }
                }
                Log.d(TAG, "IAP Billing: onProductDetailsResponse: map size ${newMap.size}")
                _productWithProductDetails.postValue(newMap)
            }

            else -> {
                // TODO
                Log.d(
                    TAG,
                    "IAP Billing: onProductDetailsResponse fail $responseCode ** $debugMessage"
                )
            }
        }
    }

    // Launch Purchase flow
    fun launchBillingFlow(activity: Activity, params: BillingFlowParams) {
        Log.d(TAG, "IAP Billing: launchBillingFlow: ")
        if (!billingClient.isReady) {
            Log.e(TAG, "IAP Billing: launchBillingFlow: BillingClient is not ready")
        }
        billingClient.launchBillingFlow(activity, params)
    }

    // PurchasesUpdatedListener that helps handle new purchases returned from the API
    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: List<Purchase>?
    ) {
        Log.d(TAG, "IAP Billing: onPurchasesUpdated: 1")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK &&
            !purchases.isNullOrEmpty()
        ) {
            _newPurchase.postValue(purchases!!)

            for (purchase in purchases) {
                acknowledgePurchases(purchase)
            }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
        }
    }

    fun acknowledgePurchases(purchase: Purchase?) {
        purchase?.let {
            if (!it.isAcknowledged) {
                val params = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(it.purchaseToken)
                    .build()

                billingClient.acknowledgePurchase(
                    params
                ) { billingResult ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK &&
                        it.purchaseState == Purchase.PurchaseState.PURCHASED
                    ) {
                        Log.d(TAG, "IAP acknowledgePurchases: ")
                        _isNewPurchaseAcknowledged.postValue(purchase)
                        val allPurchases = _purchases.value
                        if (allPurchases != null) {
                            _purchases.postValue(allPurchases + purchase)
                        } else {
                            _purchases.postValue(listOf(purchase))
                        }
                        // if(purchase.products.contains(...)) { //consumePurchase(purchase) }
                    }
                }
            }
        }
    }

    fun consumePurchase(purchase: Purchase) {
        val consumeParams =
            ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

        CoroutineScope(Dispatchers.IO).launch {
            val res = billingClient.consumePurchase(consumeParams)
            if (res.billingResult.responseCode == 0) {
                Log.d(TAG, "IAP consumePurchase: success")
            } else {
                Log.d(TAG, "IAP consumePurchase: failed")
            }
        }
    }

    fun terminateBillingConnection() {
        Log.d(TAG, "IAP Billing: Terminating connection")
        billingClient.endConnection()
    }

    companion object {
        const val TAG = "BillingClient"
    }
}
