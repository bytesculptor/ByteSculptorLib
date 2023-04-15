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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.R
import com.bytesculptor.applib.compose.theme.BssMaterialTheme
import com.bytesculptor.applib.compose.theme.preferencePaddingStart

@Composable
fun ComposePreferenceWithIcon(
    modifier: Modifier = Modifier,
    header: String,
    description: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    icon: Int? = null,
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick, enabled = enabled)
    ) {

        Column(modifier = Modifier.padding(top = 18.dp), verticalArrangement = Arrangement.Center) {
            Icon(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .width(24.dp)
                    .height(24.dp),
                painter = if (icon != null) painterResource(icon) else painterResource(R.drawable.ic_empty_icon),
                contentDescription = null,
                tint = colorResource(id = R.color.std_font),
            )
        }
        Column(
            modifier = Modifier
                .padding(start = preferencePaddingStart, end = 16.dp)
                .weight(fill = true, weight = 0.5f)
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = header,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (enabled) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                text = description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = if (enabled) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ComposePreferenceWithIconDay() {
    BssMaterialTheme {
        Column {
            ComposePreferenceWithIcon(
                header = "Battery Temperature Warning",
                description = "Get a notification if the battery temperature exceeds a limit",
                onClick = {},
                enabled = true,
                icon = R.drawable.ic_notification,
            )
            ComposePreferenceWithIcon(
                header = "Battery Temperature Warning",
                description = "Get a notification if the battery temperature exceeds a limit",
                onClick = {},
                enabled = false,
                icon = null,
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ComposePreferenceWithIconNight() {
    BssMaterialTheme(darkTheme = true) {
        Column {
            ComposePreferenceWithIcon(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit",
                onClick = {},
                enabled = true,
                icon = R.drawable.ic_notification,
            )
            ComposePreferenceWithIcon(
                header = "Battery Temperature Warning",
                description = "Get a notification if the temperature exceeds a limit",
                onClick = {},
                enabled = false,
                icon = null,
            )
        }
    }
}