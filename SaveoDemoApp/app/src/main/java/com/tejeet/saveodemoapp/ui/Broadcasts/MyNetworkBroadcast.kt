package com.tejeet.saveodemoapp.ui.Broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import com.tejeet.saveodemoapp.ui.listners.MovieClickListner
import com.tejeet.saveodemoapp.ui.listners.NetworkListner

class MyNetworkBroadcast(val context: Context, val networkListner: NetworkListner) : BroadcastReceiver() {

    private val TAG = "tag"

    override fun onReceive(p0: Context?, p1: Intent?) {

        try {
            if (isOnline(context)) {
                Log.d(TAG, "Online Connect Intenet ")
                networkListner.onNetworkChnageUpdates("YES")

            } else {

                Log.d(TAG, "Conectivity Failure !!! ")
                networkListner.onNetworkChnageUpdates("NO")
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }
}