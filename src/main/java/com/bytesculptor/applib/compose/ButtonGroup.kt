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

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytesculptor.applib.compose.theme.BssMaterialTheme

@Composable
fun ButtonGroup(
    modifier: Modifier = Modifier,
    buttonModels: List<ButtonModel>,
    buttonColor: Int,
    buttonBorderColor: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        for (buttonModel in buttonModels) {
            Row {
                OutlinedButton(
                    modifier = modifier.weight(1f),
                    onClick = buttonModel.onClick,
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 20.dp,
                        pressedElevation = 15.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 15.dp,
                        focusedElevation = 10.dp,
                    ),
                    border = BorderStroke(1.dp, colorResource(id = buttonBorderColor)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = buttonColor),
                        contentColor = Color.White,
                    ),
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(text = buttonModel.name, fontSize = 22.sp, textAlign = TextAlign.Start)
                        Text(text = buttonModel.description, textAlign = TextAlign.Start)
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = buttonModel.price ?: "---",
                            textAlign = TextAlign.End,
                            fontSize = 22.sp,
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier.height(
                    dimensionResource(id = com.bytesculptor.applib.R.dimen.spacer_height),
                ),
            )
        }
    }
}

@Composable
@Preview
fun ButtonPreviewSingle() {
    BssMaterialTheme {
        val buttonModel = ButtonModel("Title", "Description", "£1.50") {}
        ButtonGroup(
            buttonModels = listOf(buttonModel),
            buttonBorderColor = android.R.color.holo_blue_dark,
            buttonColor = android.R.color.background_dark,
        )
    }
}

@Composable
@Preview
fun ButtonPreviewDouble() {
    BssMaterialTheme {
        val buttonModel1 = ButtonModel(
            "Title",
            "This is a very long description text to test multiline preview",
            "£1.50",
        ) {}
        val buttonModel2 = ButtonModel(
            "Title without description",
            "",
            "£1.50",
        ) {}
        ButtonGroup(
            buttonModels = listOf(buttonModel1, buttonModel2),
            buttonBorderColor = android.R.color.holo_blue_dark,
            buttonColor = android.R.color.background_dark,
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ButtonDarkPreview() {
    BssMaterialTheme {
        val buttonModel = ButtonModel("Title", "Description", "£1.50") {}
        ButtonGroup(
            buttonModels = listOf(buttonModel, buttonModel, buttonModel),
            buttonBorderColor = android.R.color.holo_blue_dark,
            buttonColor = android.R.color.background_dark,
        )
    }
}

data class ButtonModel(
    val name: String,
    val description: String,
    val price: String?,
    val onClick: () -> Unit,
)
