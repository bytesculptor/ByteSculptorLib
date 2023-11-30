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
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.R
import com.bytesculptor.applib.compose.helpers.ComposeCloseButton
import com.bytesculptor.applib.compose.theme.BssMaterialTheme

@Composable
fun HelpHintBottomSheet(
    titleText: String = "",
    titleSubText: String = "",
    hintText: String,
    backgroundColor: Int,
    onClose: () -> Unit,
) {
    val bottomSheetContentDescription =
        stringResource(id = R.string.help_bottomsheet)
    Column(
        modifier = Modifier
            .verticalScroll(ScrollState(0))
            .background(
                color = colorResource(id = backgroundColor),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            )
            .fillMaxSize()
            .semantics {
                contentDescription = bottomSheetContentDescription
            },

    ) {
        val contentCloseButton = stringResource(R.string.help)
        ComposeCloseButton(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 0.dp)
                .focusable(true)
                .align(Alignment.End)
                .semantics {
                    contentDescription = contentCloseButton
                },
            onClick = onClose,
        )

        if (titleText.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 8.dp)
                    .background(color = colorResource(id = backgroundColor)),
                text = titleText,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        if (titleSubText.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
                    .background(color = colorResource(id = backgroundColor)),
                text = titleSubText,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleMedium,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 24.dp)
                .background(color = colorResource(id = backgroundColor)),
            text = hintText,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
fun HelpHintBottomSheetPreview() {
    BssMaterialTheme {
        HelpHintBottomSheet(
            titleText = "Title",
            titleSubText = "Sub Title",
            backgroundColor = R.color.white,
            hintText = "This is a preview text.",
            onClose = {},
        )
    }
}

@Preview
@Composable
fun HelpHintBottomSheetNoTitlePreview() {
    BssMaterialTheme {
        HelpHintBottomSheet(
            hintText = "This is a preview text.",
            backgroundColor = R.color.white,
            onClose = {},
        )
    }
}

@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HelpHintBottomSheetPreviewNight() {
    BssMaterialTheme(darkTheme = true) {
        HelpHintBottomSheet(
            titleText = "Title",
            hintText = "This is a preview text.",
            backgroundColor = R.color.black,
            onClose = {},
        )
    }
}
