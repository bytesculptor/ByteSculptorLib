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

package com.bytesculptor.applib.compose.iap

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.billingclient.api.BillingClient.BillingResponseCode.BILLING_UNAVAILABLE
import com.android.billingclient.api.BillingClient.BillingResponseCode.ERROR
import com.android.billingclient.api.BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED
import com.android.billingclient.api.BillingClient.BillingResponseCode.ITEM_UNAVAILABLE
import com.android.billingclient.api.BillingClient.BillingResponseCode.OK
import com.android.billingclient.api.BillingClient.BillingResponseCode.SERVICE_DISCONNECTED
import com.bytesculptor.applib.R
import com.bytesculptor.applib.compose.theme.BssMaterialTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    showErrorMessage: Boolean,
    billingConnectionResponseCode: Int,
    progressIndicatorColor: Int,
    backgroundColor: Color,
) {
    if (showErrorMessage) {
        Column(
            modifier = modifier
                .background(backgroundColor)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState(),
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .width(
                        dimensionResource(
                            id = com.bytesculptor.applib.R.dimen.circular_loading_size,
                        ),
                    )
                    .height(
                        dimensionResource(
                            id = com.bytesculptor.applib.R.dimen.circular_loading_size,
                        ),
                    ),
                color = colorResource(progressIndicatorColor),
            )
            Text(
                modifier = Modifier.padding(24.dp),
                text = getConnectionStateString(billingConnectionResponseCode),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.std_font),
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 32.dp, bottom = 64.dp),
                text = if (billingConnectionResponseCode != 0) "Error code $billingConnectionResponseCode" else "",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = colorResource(id = R.color.std_font),
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .width(dimensionResource(id = com.bytesculptor.applib.R.dimen.circular_loading_size))
                    .height(dimensionResource(id = com.bytesculptor.applib.R.dimen.circular_loading_size)),
                color = colorResource(progressIndicatorColor),
            )
        }
    }
}

@Composable
private fun getConnectionStateString(billingConnectionResponseCode: Int): String {
    return when (billingConnectionResponseCode) {
        OK -> ""

        BILLING_UNAVAILABLE -> {
            stringResource(id = com.bytesculptor.applib.R.string.iap_billing_unavailable)
        }

        FEATURE_NOT_SUPPORTED -> {
            stringResource(id = com.bytesculptor.applib.R.string.iap_feature_not_supported)
        }

        ITEM_UNAVAILABLE -> {
            stringResource(id = com.bytesculptor.applib.R.string.iap_item_unavailable)
        }

        SERVICE_DISCONNECTED, ERROR -> {
            stringResource(id = com.bytesculptor.applib.R.string.error_connect_server)
        }

        else -> ""
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL,
)
fun LoadingScreenPreview() {
    BssMaterialTheme {
        LoadingScreen(
            showErrorMessage = true,
            billingConnectionResponseCode = 3,
            progressIndicatorColor = android.R.color.holo_blue_dark,
            backgroundColor = Color.Black,
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL,
)
fun LoadingScreenSpinnerPreview() {
    BssMaterialTheme {
        LoadingScreen(
            showErrorMessage = false,
            billingConnectionResponseCode = 0,
            progressIndicatorColor = android.R.color.holo_blue_dark,
            backgroundColor = Color.Black,
        )
    }
}

@Preview(device = Devices.PIXEL_C)
@Composable
fun LoadingScreenPreviewTablet() {
    BssMaterialTheme {
        LoadingScreen(
            showErrorMessage = true,
            billingConnectionResponseCode = 3,
            progressIndicatorColor = android.R.color.holo_blue_dark,
            backgroundColor = Color.Black,
        )
    }
}
