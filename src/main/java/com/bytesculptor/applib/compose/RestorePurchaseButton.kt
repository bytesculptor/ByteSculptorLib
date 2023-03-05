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

package com.bytesculptor.applib.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytesculptor.applib.R

@Composable
fun RestorePurchaseButton(onPurchaseRestoreClick: () -> Unit, backgroundColor: Int) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 12.dp),
        onClick = onPurchaseRestoreClick,
        border = BorderStroke(
            1.dp,
            colorResource(id = R.color.std_font)
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = backgroundColor),
            contentColor = Color.White,
        )
    ) {
        Text(
            text = stringResource(id = R.string.iap_restore_purchases),
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.std_font),
            fontSize = 18.sp,
        )
    }
}
