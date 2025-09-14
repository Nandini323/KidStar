package com.metroid.logindemo

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.window.SplashScreen
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.metroid.logindemo.Datastore.Frontlistmodel
import com.metroid.logindemo.ui.theme.white
import com.metroid.logindemo.viewModel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SplashScreen(){
    Scaffold(

        content = { paddingValues ->
            // Main content with padding applied correctly
            Box(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()) {
                Image(painter = painterResource(id = R.drawable.panda_bg), contentDescription = "Splash Screen Image", modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)

@Composable
fun MainActivityScreen(item : List<Frontlistmodel>,viewModel: MainViewModel){
    val pagerState = rememberPagerState(initialPage = 0)

    val selectedTab = remember { mutableStateOf("Home") }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(1000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }



    Scaffold (
        topBar = {
            TopAppBar(
               title = { Text("Lets Enjoy", style = TextStyle(fontStyle = FontStyle.Italic, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Red))},
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            )
        },
        content = { paddingValues ->
            when(selectedTab.value){
                "Home" ->{
                    HomeContent(pagerState,item,viewModel,paddingValues)
                }
                "Search" ->{
                    SearchContent(pagerState,item,viewModel,paddingValues)
                }
                "Profile" ->{
                    ProfileScreen(paddingValues)
                }
                "Settings"->{

                }
            }


        }, bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        BottomBarItem("Home", R.drawable.ic_home) {
                            selectedTab.value = "Home"
                        //  startActivity(LocalContext.current, Intent(LocalContext.current, HomeActivity::class.java), null)
                        }
                        BottomBarItem("Search", R.drawable.ic_search) {
                            selectedTab.value = "Search"
                        //  startActivity(LocalContext.current, Intent(LocalContext.current, SearchActivity::class.java), null)
                        }
                        BottomBarItem("Profile", R.drawable.ic_profile) {
                            selectedTab.value = "Profile"
                        //  startActivity(LocalContext.current, Intent(LocalContext.current, ProfileActivity::class.java), null)
                        }
                        BottomBarItem("Settings", R.drawable.ic_settings) {
                            selectedTab.value = "Settings"
                           // startActivity(LocalContext.current, Intent(LocalContext.current, SettingsActivity::class.java), null)
                        }
                    }
                }
            )
        }

    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContent(pagerState: PagerState, item: List<Frontlistmodel>, viewModel: MainViewModel, paddingValues: PaddingValues) {
    val imageSlider = listOf(
        painterResource(id = R.drawable.cartoon1),
        painterResource(id = R.drawable.cartoon6),
        painterResource(id = R.drawable.cartoon2)
    )
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) { page ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }.clickable {
                        Toast.makeText(context,"Cliked on card!!!",Toast.LENGTH_LONG).show()

                    }
            ) {
                Image(
                    painter = imageSlider[page],
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Column(modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item) { index ->
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(0.5.dp,Color.Black,CircleShape)
                            .clickable {
                                viewModel.onItemClicked(index.id)
                              //  Toast.makeText(context,"Cliked",Toast.LENGTH_LONG).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {

                        Image(painter = painterResource(id = index.imageRes), contentDescription = "", modifier = Modifier.align(Alignment.Center).background(index.backgroundcolor))

                    }
                }
            }


            LazyVerticalGrid(columns = GridCells.Fixed(2)
                ,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(item){ index->
                    Card(modifier = Modifier.fillMaxWidth().border(0.5.dp,Color.Black)
                        .clickable {
                            viewModel.onItemClicked(index.id)
                          //  Toast.makeText(context,"Grid Cliked",Toast.LENGTH_LONG).show()
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize()){
                            Image(painter = painterResource(id = index.imageRes), contentDescription = "", modifier = Modifier.align(Alignment.Center).size(100.dp))
                        }
                    }
                }
            }

        }

    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun SearchContent(pagerState: PagerState, item: List<Frontlistmodel>, viewModel: MainViewModel, paddingValues: PaddingValues){
    val items = listOf("Cartoons", "Movies", "Webseries", "serials", "other")
    var searchQuery by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search...") },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp) // space between cards
        ) {
            items(items) { text ->
                Card(
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        LazyVerticalGrid(columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(item){ index->
                Card(modifier = Modifier.fillMaxWidth().border(0.5.dp,Color.Black)
                    .clickable {
                        viewModel.onItemClicked(index.id)
                        //  Toast.makeText(context,"Grid Cliked",Toast.LENGTH_LONG).show()
                    }
                ) {
                    Column( horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = index.imageRes),
                                contentDescription = "",
                                modifier = Modifier.align(Alignment.Center).size(100.dp)
                            )
                        }
                        Text(
                            text = index.Headline,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )

                        // Row for Favorite and Share icons at bottom-end
                        Row(
                            modifier = Modifier
                                .padding(8.dp).align(Alignment.End).padding(bottom = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite), // Replace with your favorite icon
                                contentDescription = "Favorite",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp).clickable {
                                    Toast.makeText(context,"Favourite added",Toast.LENGTH_LONG).show()
                                }

                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share), // Replace with your share icon
                                contentDescription = "Share",
                                modifier = Modifier.size(20.dp).clickable {
                                    Toast.makeText(context,"share added",Toast.LENGTH_LONG).show()
                                },
                                tint = Color.Gray
                            )
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun ProfileScreen(PaddingValues:PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.edit_note_24),
            contentDescription = "",
            modifier = Modifier.size(75.dp).padding(8.dp).align(Alignment.End).padding(bottom = 10.dp, end = 10.dp)
        )
        // Profile Picture
        Card(
            modifier = Modifier
                .size(120.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile_pic), // replace with your image
                contentDescription = "Profile Picture",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = "Lokesh Yadav",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Email
        Text(
            text = "lokesh@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Extra Information Section
        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Phone")
                    Text(text = "+91 9876543210")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Location")
                    Text(text = "Kalyan, India")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Edit Button
        Button(onClick = { /* Handle Edit */ }) {
            Text("Edit Profile")
        }
    }
}


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
    fun VideoPlayer(@RawRes videoResId: Int,item : List<Frontlistmodel>,viewModel: MainViewModel) {

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        val context = LocalContext.current
        val uri = RawResourceDataSource.buildRawResourceUri(videoResId)

        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(uri)
                setMediaItem(mediaItem)
                prepare()
                playWhenReady = true
            }
        }

        DisposableEffect(
            AndroidView(factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            })
        ) {
            onDispose {
                exoPlayer.release()
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Doremon Episode",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White
        )
        Text(
            text = "Doraemon in Hindi - Nobita Gadget Se Dur Bhagega",
            color = Color.White
        )

        LazyColumn (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(5.dp)
        ) {
            items(item) { index ->
                Card(modifier = Modifier.fillMaxWidth().border(0.5.dp,Color.Black)

                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Column(modifier = Modifier.fillMaxSize().align(Alignment.Center)) {
                        Row (modifier = Modifier.fillMaxSize()
                            .clickable {
                                viewModel.onItemClicked(index.id)
                                Toast.makeText(context,"Grid Cliked",Toast.LENGTH_LONG).show()
                            }){
                            Image(painter = painterResource(id = index.imageRes), contentDescription = "", modifier = Modifier
                                .size(100.dp).padding(10.dp)
                                .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop)
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = index.Headline,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White ,
                                    modifier = Modifier.padding(start = 16.dp).padding(top = 16.dp)
                                )
                                Text(
                                    text = index.description,
                                    color = Color.White,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                        // Row for Favorite and Share icons at bottom-end
                        Row(
                            modifier = Modifier
                                .padding(8.dp).align(Alignment.End).padding(bottom = 10.dp, end = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite), // Replace with your favorite icon
                                contentDescription = "Favorite",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp).clickable {
                                    Toast.makeText(context,"Favourite added",Toast.LENGTH_LONG).show()
                                }

                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_share), // Replace with your share icon
                                contentDescription = "Share",
                                modifier = Modifier.size(20.dp).clickable {
                                    Toast.makeText(context,"share added",Toast.LENGTH_LONG).show()
                                },
                                tint = Color.Gray
                            )
                        }
                        }
                    }
                }
            }
        }
    }
    }


@Composable
fun BottomBarItem(label: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = label)
        Text(text = label, fontSize = 12.sp)
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun getLoginScreen(){

    ConstraintLayout(modifier = Modifier.fillMaxSize().background(color = white)) {
        val (companyName,submit,mainimg) = createRefs()
        val context = LocalContext.current
        Text(text = "i am inn..", modifier = Modifier.constrainAs(companyName){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        })

                Box(modifier = Modifier
                    .padding(1.dp)
                    .constrainAs(mainimg){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(companyName.bottom)
                    }) {
                    Image(painter = painterResource(id = R.drawable.panda), contentDescription = "Splash Screen Image", modifier = Modifier.align(Alignment.Center))
                }

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray)

        ) {
            // Center Text
            Text(
                text = "Product Name",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )

            // Bottom-end Image (Add to Cart)
            Image(
                painter = painterResource(id = R.drawable.panda), // your cart icon
                contentDescription = "Add to Cart",
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp) // Optional: little padding from edges
            )
        }

        Button(onClick = {Toast.makeText(context,"submitt",Toast.LENGTH_LONG).show()

        }, modifier = Modifier.padding(1.dp).constrainAs(submit){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(mainimg.bottom)
        }, colors = ButtonDefaults.buttonColors(
            contentColor = Color.Red,
            containerColor = Color.Yellow
        )) {
            Text(text = "Submit")
        }
    }
}


@Composable
fun MyAlertDialog(showDialog: Boolean, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Confirmation") },
            text = { Text("Are you sure you want to proceed?") },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview
@Composable
fun demo(){
        Text(text = "hello", modifier = Modifier.background(Color.White))
}