/*
 * Copyright (c) 2021 Byte Sculptor Software - All Rights Reserved
 *
 * All information contained herein is and remains the property of Byte Sculptor Software.
 * Unauthorized copying of this file, via any medium, is strictly prohibited unless prior
 * written permission is obtained from Byte Sculptor Software.
 *
 * Romeo Rondinelli - bytesculptor@gmail.com
 *
 */

package com.bytesculptor.applib.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bytesculptor.applib.R
import com.bytesculptor.applib.utilities.ExternalLinksHelper

class DisclaimerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_disclaimer, container, false)
        val feedback = view.findViewById<TextView>(R.id.tvFeedbackMailLink)
        feedback?.setOnClickListener { _: View? ->
            ExternalLinksHelper.sendFeedbackMail(
                requireContext(),
                "App",
                getString(R.string.no_email_app_found)
            )
        }
        return view.rootView
    }
}