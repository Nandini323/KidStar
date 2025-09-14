package com.metroid.logindemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import com.metroid.logindemo.Datastore.Frontlistmodel
import com.metroid.logindemo.ui.theme.LoginDemoTheme
import com.metroid.logindemo.viewModel.MainViewModel
import com.metroid.logindemo.worker.WorkerClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LoginDemoTheme {
            val viewModel: MainViewModel = viewModel()
            val navTarget by viewModel.navigateTo.collectAsState()
            val context = this

            LaunchedEffect(navTarget) {
                navTarget?.let { id->
                    val intent = when (id){
                        1-> Intent(context,VedioPlayerScreen::class.java)
                        2-> Intent(context,VedioPlayerScreen::class.java)
                        3-> Intent(context,VedioPlayerScreen::class.java)
                        4-> Intent(context,VedioPlayerScreen::class.java)
                        5-> Intent(context,VedioPlayerScreen::class.java)
                        else -> null
                    }
                    intent?.let { startActivity(it) }
                    viewModel.resetNavigation()

                }
            }
                val items = listOf(
                    Frontlistmodel(1,"Movies","",R.drawable.cartoon1,Color.Red),
                    Frontlistmodel(2,"Horror","",R.drawable.cartoon2,Color.Yellow),
                    Frontlistmodel(3,"Funny","",R.drawable.cartoon3,Color.Cyan),
                    Frontlistmodel(4,"Serials","",R.drawable.cartoon8,Color.Green),
                    Frontlistmodel(5,"webSeries","",R.drawable.cartoon9,Color.DarkGray),
                    Frontlistmodel(6,"Cartoons","",R.drawable.cartoon6,Color.Magenta)


                )
                MainActivityScreen(item = items,viewModel = viewModel)

            }
        }
    }
}
