package com.backtorfront.app.play

import android.content.Context
import android.content.Intent
import android.net.Uri

object StreamLauncher {
    /** Opens the streaming app, or its Play Store page if not installed. */
    fun launch(context: Context, app: StreamApp) {
        val pm = context.packageManager
        val intent = pm.getLaunchIntentForPackage(app.packageName)
            ?: Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${app.packageName}"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${app.packageName}")
                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }
}
