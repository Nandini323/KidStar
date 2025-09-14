package com.metroid.logindemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.metroid.logindemo.ui.theme.LoginDemoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity:ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginDemoTheme {
                SplashScreen()

                lifecycleScope.launch {
                    delay(3000)
                    val intent = Intent(this@SplashScreenActivity,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}