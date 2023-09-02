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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.R
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.compose.theme.preferencePaddingStart

@Composable
fun ComposePreferenceSwitchWithIcon(
    modifier: Modifier = Modifier,
    header: String,
    description: String,
    switchState: Boolean,
    switchEnabled: Boolean,
    enabled: Boolean,
    onSwitchChanged: (Boolean) -> Unit,
    icon: Int? = null,
) {
    val switchOn = remember { mutableStateOf(switchState) }

    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(top = 18.dp), verticalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(24.dp)
                    .height(24.dp),
                painter = if (icon != null) {
                    painterResource(
                        icon,
                    )
                } else {
                    painterResource(R.drawable.ic_empty_icon)
                },
                contentDescription = null,
                tint = colorResource(id = R.color.std_font),
            )
        }

        Column(
            modifier = Modifier
                .padding(start = preferencePaddingStart, end = 8.dp)
                .weight(fill = true, weight = 0.5f),
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = header,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = description,
                color = if (enabled) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Column(
            modifier = Modifier
                .weight(0.12f)
                .padding(16.dp)
                .align(Alignment.CenterVertically),
        ) {
            Switch(
                modifier = Modifier.padding(end = 4.dp),
                checked = switchOn.value,
                enabled = switchEnabled,
                onCheckedChange = {
                    switchOn.value = !switchOn.value
                    onSwitchChanged(it)
                },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White),
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ComposePreferenceSwitchWithIconDay() {
    BssMaterialTheme(darkTheme = false) {
        ComposePreferenceSwitchWithIcon(
            header = "Battery Temperature Warning",
            description = "Get a notification if the temperature exceeds a limit",
            switchEnabled = true,
            switchState = true,
            onSwitchChanged = {},
            enabled = true,
            icon = R.drawable.ic_notification,
        )
    }
}

@Preview(showBackground = false, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ComposePreferenceSwitchWithIconNight() {
    BssMaterialTheme(darkTheme = true) {
        Column {
            ComposePreferenceSwitchWithIcon(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit",
                switchEnabled = true,
                switchState = true,
                onSwitchChanged = {},
                enabled = true,
                icon = R.drawable.ic_notification,
            )
            ComposePreferenceSwitchWithIcon(
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
