package com.omersungur.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.model.cart.ProductCart

class CartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    Scaffold {
                        Surface(modifier = Modifier.padding(it)) {
                            CartScreen()
                        }
                    }
                }
            }
        }
        return view
    }
}

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        if (isSuccess) {
            carts.forEach {
                it.products?.forEach { product ->
                    CartItemRow(
                        product = product
                    ) { a, b ->

                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    product: ProductCart,
    onQuantityChange: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = product.title ?: "No Data Found", fontSize = 16.sp, fontWeight = FontWeight.Bold)

            Text(text = "$${product.price}", fontSize = 14.sp, color = Color.Gray)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onQuantityChange(product.id ?: -1, product.quantity?.minus(1) ?: -1) }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Decrease quantity")
            }

            Text(text = product.quantity.toString(), fontSize = 16.sp, modifier = Modifier.padding(horizontal = 8.dp))

            IconButton(onClick = { onQuantityChange(product.id ?: -1, product.quantity?.plus(1) ?: -1) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase quantity")
            }
        }
    }
}
