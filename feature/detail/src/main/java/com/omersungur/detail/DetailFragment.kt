package com.omersungur.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.model.product.ProductX
import com.omersungur.domain.model.product.Review
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    Scaffold {
                        Surface(modifier = Modifier.padding(it)) {
                            DetailScreen()
                        }
                    }
                }
            }
        }
        return view
    }
}

@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        if (loadingStateForProducts) {
            CircularProgressIndicator()
        }

        if (loadingStateForDetail) {
            CircularProgressIndicator()
        }

        if (isSuccessForProducts) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                ProductRowColumn(products = products, detailViewModel = viewModel)

                if (isSuccess) {
                    product?.let {
                        AsyncImage(
                            model = it.thumbnail,
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(10.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = it.title ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Brand: ${it.brand}", fontSize = 16.sp, color = Color.Gray)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Price: $${it.price}", fontSize = 20.sp, color = Color.Red)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Rating: ${it.rating}", fontSize = 16.sp, color = Color.Black)

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = it.description ?: "", fontSize = 16.sp, color = Color.DarkGray)

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Reviews", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                        Spacer(modifier = Modifier.height(8.dp))

                        it.reviews?.forEach { review ->
                            ReviewItem(review = review)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { /* TODO: Add to cart action */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                        ) {
                            Text(text = "Add to Cart")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(review.rating ?: 0) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating Star",
                    tint = Color.Yellow
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = review.comment ?: "", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "- ${review.reviewerName}", fontSize = 12.sp, color = Color.Gray)

        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
    }
}

@Composable
fun ProductRowColumn(
    modifier: Modifier = Modifier,
    products: List<ProductX>,
    detailViewModel: DetailViewModel
) {
    LazyRow(
        modifier = modifier,
    ) {
        items(products) { product ->
            ProductCard(product = product) {
                detailViewModel.getProductById(it)
            }
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductX,
    onClickForProductId: (Int) -> Unit,
) {
    val formattedRating = String.format(locale = Locale.getDefault(), "%.1f", product.rating)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onClickForProductId(product.id ?: 1)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(width = 2.dp, color = Color(0xFFFFA500)),
        shape = RoundedCornerShape(16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(com.omersungur.compose_ui.R.drawable.ic_google),
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = product.title.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                maxLines = 1,
            )

            Text(
                text = product.brand ?: "No Brand Found!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                maxLines = 1,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "$${product.price}",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                )

                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Star Icon",
                        tint = Color(0xFFFFA500),
                    )

                    Text(text = formattedRating)
                }
            }
        }
    }
}