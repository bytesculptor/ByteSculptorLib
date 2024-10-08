/*
 * Copyright 2022 Byte Sculptor Software
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

package com.bytesculptor.applib.compose.preferences

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.compose.theme.preferencePaddingEnd
import com.bytesculptor.applib.compose.theme.preferencePaddingStart

@Composable
fun ComposePreferenceHeader(
    modifier: Modifier = Modifier,
    header: String,
    color: Color,
) {
    Text(
        modifier = modifier.padding(
            start = preferencePaddingStart,
            end = preferencePaddingEnd,
            bottom = 4.dp,
            top = 0.dp,
        ),
        text = header,
        color = color,
        style = MaterialTheme.typography.titleLarge,
    )
}

@Preview (showBackground = true)
@Composable
fun ComposePreferenceHeaderPreview() {
    BssMaterialTheme {
        ComposePreferenceHeader(header = "Header", color = MaterialTheme.colorScheme.onPrimary)
    }
}
