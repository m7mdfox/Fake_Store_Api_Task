package com.example.apitask

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.colorResource
import androidx.core.content.res.ResourcesCompat.getColor


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiTaskTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppRoot()
                }
            }
        }
    }
}




@Composable
fun AppRoot() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (selectedProduct == null) {
        MainScreen(
            onProductClick = { product ->
                selectedProduct = product
            }
        )
    } else {
        ProductDetailsScreen(
            product = selectedProduct!!,
            onBack = { selectedProduct = null }
        )
    }
}


@Composable
fun MainScreen(onProductClick: (Product) -> Unit, viewModel: MainViewModel = viewModel()) {
    val products by viewModel.products.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.loadItems()
    }

    products?.let {
        LazyColumn {
            items(it) { product ->
                ProductCard(product = product) {
                    onProductClick(product)
                }
            }
        }
    } ?: LoadingScreen()
}

@Composable
fun LoadingScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Currently Loading Products.....",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun ProductCard(product: Product, onClick: () -> Unit){
    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .clickable { onClick() }
    ) {
        LazyRow {
            items(product.images){images->
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
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            )
            Text(
                text = "${product.price} $",
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp

            )
        }

    }
}

@Composable
fun ProductDetailsScreen(product: Product, onBack: () -> Unit) {
    Column (
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.buttons)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Back")
        }
        LazyRow {
            items(product.images){images->
                AsyncImage(
                    model = images ,
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(4.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp

            )
            Text(
                text = "${product.price} $",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp

            )
        }

        Text(
            text = "Description:\n${product.description}",
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp,
            color = Color.Black

        )

    }

}

