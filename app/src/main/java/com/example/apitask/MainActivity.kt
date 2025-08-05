package com.example.apitask

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apitask.ui.theme.ApiTaskTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {

}
@Composable
fun MainScreen(modifier: Modifier){
    val viewModel: MainViewModel = viewModel()
    val products by viewModel.products.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    when {
        products != null -> {
            LazyColumn {
                items(products!!){product ->
                    ProductCard(price = product.price, title = product.title,
                        images = product.images, description = product.description)

                }
            }
        }
        else -> {

        }
    }
}
@Composable
fun ProductCard(price : Int, title: String, images: List<String>,description: String){
    Column (modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        LazyRow {
            items(images){images->
                AsyncImage(
                    model = images ,
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            )
            Text(
                text = price.toString() + " $",
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            )
        }

    }
}
