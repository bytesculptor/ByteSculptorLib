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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.iap.PurchaseDataItem

@Composable
fun PurchaseItem(
    modifier: Modifier,
    purchase: PurchaseDataItem,
) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp),
                text = purchase.gpa,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = colorResource(id = com.bytesculptor.applib.R.color.std_font),
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp),
                text = purchase.itemName,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = colorResource(id = com.bytesculptor.applib.R.color.std_font),
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 3.dp),
                text = purchase.date,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = colorResource(id = com.bytesculptor.applib.R.color.std_font),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PurchaseView(modifier: Modifier, purchases: List<PurchaseDataItem>) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp, 8.dp, 10.dp, 8.dp),
    ) {
        items(purchases) { purch ->
            PurchaseItem(Modifier, purch)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PurchaseItemPreview() {
    BssMaterialTheme {
        val purchase = PurchaseDataItem(
            gpa = "GPA.1234-5678-9000-12345",
            date = "01/01/2022 - 12.15pm",
            itemName = "pro_v1",
        )
        PurchaseView(Modifier, listOf(purchase, purchase))
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PurchaseItemDarkPreview() {
    BssMaterialTheme {
        val purchase = PurchaseDataItem(
            gpa = "GPA.2324-5253-2240-12345",
            date = "01/01/2022 - 12.15pm",
            itemName = "pro_v1",
        )
        PurchaseView(Modifier, listOf(purchase, purchase))
    }
}
