/*
 * Copyright 2020 Byte Sculptor Software
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


package com.bytesculptor.applib.utilities;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.bytesculptor.applib.utilities.ByteSculptorConstants.DEVELOPER_STORE_ID;
import static com.bytesculptor.applib.utilities.ByteSculptorConstants.FEEDBACK_EMAIL;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


public class ExternalLinksHelper {


    /**
     * Intent to start the default email app. Prompts a choice if more than one is installed.
     *
     * @param subject email subject
     */
    public static void sendFeedbackMail(Context context, String subject, String toastMessageIfFails) {
        String uriText = "mailto:" + FEEDBACK_EMAIL + "?subject=" + subject;
        Uri mailUri = Uri.parse(uriText);
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, mailUri);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(Intent.createChooser(intent, "sendEmail"));
        } catch (Exception e) {
            if (!toastMessageIfFails.trim().isEmpty()) {
                Toast.makeText(context, toastMessageIfFails, Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Opens an URL with the default browser. Fires a toast if it fails and the toast message is not empty.
     *
     * @param context
     * @param url
     * @param toastMessageIfFails
     */
    public static void openLinkInBrowser(Context context, String url, String toastMessageIfFails) {
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            if (!toastMessageIfFails.trim().isEmpty()) {
                Toast.makeText(context, toastMessageIfFails, Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Opens the Google Play Store app with the app named in packageName. If it fails or Play Store
     * is not installed it tries to open with the browser.
     *
     * @param context
     * @param packageName
     */
    public static void goToAppStore(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToAppStore = new Intent(Intent.ACTION_VIEW, uri);
        goToAppStore.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK | FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(goToAppStore);
        } catch (ActivityNotFoundException e) {
            openLinkInBrowser(
                    context,
                    "https://play.google.com/store/apps/details?id=" + packageName,
                    context.getString(com.bytesculptor.applib.R.string.errorOpeningExternalLink)
            );
        }
    }


    /**
     * Opens the Google Play Store with all Byte Sculptor apps. If it fails or Play Store
     * is not installed it tries to open with the browser.
     *
     * @param context
     */
    public static void showMoreByteSculptorApps(Context context) {
        Uri uri = Uri.parse("market://apps/dev?id=" + DEVELOPER_STORE_ID);
        Intent goToAppStore = new Intent(Intent.ACTION_VIEW, uri);
        goToAppStore.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToAppStore);
        } catch (ActivityNotFoundException e) {
            Log.d("BSS", "fail");
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/dev?id=" + DEVELOPER_STORE_ID)));
        }
    }


    /**
     * Opens a "share" to send the Google Play Store link
     *
     * @param context
     * @param packageName
     * @param appName
     */
    public static void shareAppGooglePlayStore(Context context, String packageName, String appName) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            String shareMessage = "https://play.google.com/store/apps/details?id=" + packageName + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, ""));
        } catch (Exception e) {
            //e.toString();
        }
    }

    /**
     * Opens a "share" to send the app store link for the browser (used in other than
     * Google Play Store flavour builds)
     *
     * @param context
     * @param url
     * @param appName
     */
    public static void shareAppLink(Context context, String url, String appName) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
            String shareMessage = url + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, ""));
        } catch (Exception e) {
            //e.toString();
        }
    }

}