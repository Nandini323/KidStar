package com.metroid.logindemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

import androidx.lifecycle.viewmodel.compose.viewModel

import com.metroid.logindemo.Datastore.Frontlistmodel
import com.metroid.logindemo.ui.theme.LoginDemoTheme
import com.metroid.logindemo.viewModel.MainViewModel

//MainActivity
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
