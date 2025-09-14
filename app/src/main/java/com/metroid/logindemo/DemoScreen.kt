package com.metroid.logindemo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun subDemo(){

    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Text(text = "Demo testing", modifier = Modifier.fillMaxWidth())
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter your text") },
            modifier = Modifier.fillMaxWidth()
        )
       Button(onClick = {Toast.makeText(context,"entered",Toast.LENGTH_LONG).show()}) {
           Text(text = "Submit")
       }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun  demoscoff(){
    Scaffold (
        topBar = { TopAppBar(title = { Text("demo") }) },
        content ={

            Row(modifier = Modifier.fillMaxSize()) {
                Text(text = "demo")
                Text(text = "demo")
                Text(text = "demo")
            }


            Box(modifier = Modifier.fillMaxSize()){
                Text(text = "first")
                Image(painter = painterResource(id = R.drawable.cartoon3), contentDescription = "", modifier = Modifier.align(
                    Alignment.Center).fillMaxWidth().size(100.dp))

            }
        }
    )
}