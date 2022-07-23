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

package com.bytesculptor.applib.compose.helpers

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bytesculptor.applib.R

@Composable
fun ComposeDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(1.dp),
        color = colorResource(id = R.color.darkFont)
    )
}

@Composable
fun ComposeBottomSheetNotch(modifier: Modifier) {
    Divider(
        modifier = modifier
            .padding(vertical = 12.dp)
            .width(32.dp),
        thickness = 4.dp
    )
}