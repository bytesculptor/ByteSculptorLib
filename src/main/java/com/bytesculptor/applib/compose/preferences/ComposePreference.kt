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

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.compose.theme.preferencePaddingStart

@Composable
fun ComposePreference(
    modifier: Modifier = Modifier,
    header: String,
    description: String
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(start = preferencePaddingStart, end = 16.dp)
                .weight(fill = true, weight = 0.5f)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = header,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = description,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ComposePreferenceDay() {
    BssMaterialTheme {
        ComposePreference(
            header = "Battery Temperature Warning",
            description = "Get a notification if the battery temperature exceeds a limit"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposePreferenceNight() {
    BssMaterialTheme(darkTheme = true) {
        Column {
            ComposePreference(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit"
            )
            ComposePreference(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit"
            )
        }
    }
}