package com.tejeet.saveodemoapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tejeet.nobrokerdemoapi.constants.ConstantsData
import com.tejeet.saveodemoapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch {
            delay(ConstantsData.SPLASH_SCREEN_TIMEOUT);  // Splash Screen Wait time Defined in Constant File
            gotoDashboard();
        }

    }

    private fun gotoDashboard() {
        intent = Intent(this, MainActivity::class.java);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}