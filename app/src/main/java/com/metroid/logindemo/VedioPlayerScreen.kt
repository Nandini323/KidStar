package com.metroid.logindemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.metroid.logindemo.Datastore.Frontlistmodel
import com.metroid.logindemo.ui.theme.LoginDemoTheme
import com.metroid.logindemo.viewModel.MainViewModel
import com.metroid.logindemo.worker.WorkerClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VedioPlayerScreen:ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val items = withContext(Dispatchers.IO) {
                listOf(
                    Frontlistmodel(
                        1,
                        "Doremon Episode No. 1",
                        "When Doremon finally finds iceland...",
                        R.drawable.do1,
                        Color.Red
                    ),
                    Frontlistmodel(
                        2,
                        "Doremon Episode No. 2",
                        "Nobita always fail.. now what is the reaction we will see",
                        R.drawable.do2,
                        Color.Yellow
                    ),
                    Frontlistmodel(
                        3,
                        "Doremon Episode No. 3",
                        "Shizuka always support nobita.",
                        R.drawable.do3,
                        Color.Cyan
                    ),
                    Frontlistmodel(
                        4,
                        "Doremon Episode No. 4",
                        "Doremon finds a new friend dekesuki",
                        R.drawable.do4,
                        Color.Green
                    ),
                    Frontlistmodel(
                        5,
                        "Doremon Episode No. 5",
                        "Doremon gives its gadget to nobita what will happen",
                        R.drawable.do5,
                        Color.DarkGray
                    ),
                    Frontlistmodel(
                        6,
                        "Doremon Episode No. 6",
                        "Giyan finds the new mistarious stone.",
                        R.drawable.do6,
                        Color.Magenta
                    )
                )
            }

            setContent {

                LoginDemoTheme {


                    val viewModel: MainViewModel = viewModel()
                    val navTarget by viewModel.navigateTo.collectAsState()
                    val context = this@VedioPlayerScreen
                    LaunchedEffect(navTarget) {
                        navTarget?.let { id ->
                            val intent = when (id) {
                                1 -> Intent(context, VedioPlayerScreen::class.java)
                                2 -> Intent(context, VedioPlayerScreen::class.java)
                                3 -> Intent(context, VedioPlayerScreen::class.java)
                                4 -> Intent(context, VedioPlayerScreen::class.java)
                                5 -> Intent(context, VedioPlayerScreen::class.java)
                                else -> null
                            }
                            intent?.let { startActivity(it) }
                            viewModel.resetNavigation()

                        }
                    }

                    VideoPlayer(videoResId = R.raw.doremon, item = items, viewModel = viewModel)
                    //create work Request
                    val request: WorkRequest = OneTimeWorkRequestBuilder<WorkerClass>().build()
                    //Submit work request
                    WorkManager.getInstance(context).enqueue(request)
                }

            }
        }
    }
}