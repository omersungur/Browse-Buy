package com.omersungur.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.Dimen
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
fun CartScreen(viewModel: CartViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        if (isSuccess) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimen.spacing_m1),
            ) {
                Text(
                    text = stringResource(R.string.cart_summary),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = Dimen.font_size_l,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                carts.forEach { cart ->
                    Text(
                        text = "Total Products: ${cart.totalProducts}",
                        fontSize = Dimen.font_size_m1,
                    )

                    Text(
                        text = "Total Quantity: ${cart.totalQuantity}",
                        fontSize = Dimen.font_size_m1,
                    )

                    Text(
                        text = "Total: \$${String.format("%.2f", cart.total)}",
                        fontSize = Dimen.font_size_m1,
                    )

                    Text(
                        text = "Discounted Total: \$${String.format("%.2f", cart.discountedTotal)}",
                        fontSize = Dimen.font_size_s1,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                LazyColumn {
                    items(carts) { cart ->
                        cart.products?.forEach {
                            ProductRow(product = it)

                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = Dimen.spacing_xs),
                                thickness = 1.dp,
                                color = Color.Gray,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductRow(product: ProductCart) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimen.spacing_xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(64.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(Dimen.spacing_m1))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = product.title ?: "No Data Found!", fontSize = Dimen.font_size_m1, fontWeight = FontWeight.Bold)

            Text(text = "Price: \$${String.format("%.2f", product.price)}")

            Text(text = "Quantity: ${product.quantity}")

            Text(text = "Total: \$${String.format("%.2f", product.total)}")

            Text(text = "Discounted Total: \$${String.format("%.2f", product.discountedTotal)}", color = Color.Green)
        }
    }
}