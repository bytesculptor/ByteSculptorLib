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
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.compose.theme.preferencePaddingStart

@Composable
fun ComposePreferenceSwitch(
    modifier: Modifier = Modifier,
    header: String,
    description: String,
    switchState: Boolean,
    switchEnabled: Boolean,
    enabled: Boolean,
    onSwitchChanged: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .padding(start = preferencePaddingStart, end = 4.dp)
                .weight(fill = true, weight = 1.0f)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = header,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = description,
                color = if (enabled) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
        ) {
            Switch(
                checked = switchState,
                enabled = switchEnabled,
                onCheckedChange = { onSwitchChanged(it) },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ComposePreferenceSwitchDay() {
    BssMaterialTheme(darkTheme = false) {
        ComposePreferenceSwitch(
            header = "Battery Temperature Warning",
            description = "Get a notification if the temperature exceeds a limit",
            switchEnabled = true,
            switchState = true,
            onSwitchChanged = {},
            enabled = true,
        )
    }
}

@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ComposePreferenceSwitchNight() {
    BssMaterialTheme(darkTheme = true) {
        Column {
            ComposePreferenceSwitch(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit",
                switchEnabled = true,
                switchState = true,
                onSwitchChanged = {},
                enabled = true,
            )
            ComposePreferenceSwitch(
                header = "Battery Level Warning",
                description = "Get a notification if the battery level reaches a limit",
                switchEnabled = false,
                switchState = true,
                onSwitchChanged = {},
                enabled = false,
            )
        }
    }
}