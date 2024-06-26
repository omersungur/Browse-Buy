package com.omersungur.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.component.bar.CustomProgressBar
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_C58F2D
import com.omersungur.compose_ui.theme.Dimen
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CustomProgressBar()
            }
        }

        if (loadingStateForDetail) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CustomProgressBar()
            }
        }

        if (isSuccessForProducts) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimen.spacing_m1)
                    .verticalScroll(rememberScrollState())
            ) {
                ProductRowColumn(
                    products = products,
                    detailViewModel = viewModel,
                )

                if (isSuccess) {
                    product?.let {
                        AsyncImage(
                            model = it.thumbnail,
                            contentDescription = it.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(10.dp),
                            contentScale = ContentScale.Crop,
                        )

                        Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                        Text(
                            text = it.title ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = Dimen.font_size_l,
                            color = Color.Black,
                        )

                        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                        Text(text = "Brand: ${it.brand}", fontSize = Dimen.font_size_m1, color = Color.Gray)

                        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                        Text(text = "Price: $${it.price}", fontSize = Dimen.font_size_m2, color = Color.Red)

                        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                        Text(text = "Rating: ${it.rating}", fontSize = Dimen.font_size_m1, color = Color.Black)

                        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                        Text(text = it.description ?: "", fontSize = Dimen.font_size_m1, color = Color.DarkGray)

                        Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                        Text(text = stringResource(R.string.reviews), fontWeight = FontWeight.Bold, fontSize = Dimen.font_size_m2)

                        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                        it.reviews?.forEach { review ->
                            ReviewItem(review = review)
                        }

                        Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                        Button(
                            onClick = { /* TODO: Add to cart action */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimen.spacing_m1)
                        ) {
                            Text(text = stringResource(R.string.add_to_cart))
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
            .padding(vertical = Dimen.spacing_xxs),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(review.rating ?: 0) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.rating_star),
                    tint = Color.Yellow,
                )
            }
        }
        Spacer(modifier = Modifier.height(Dimen.spacing_xxs))

        Text(text = review.comment ?: "", fontSize = Dimen.font_size_m1)

        Spacer(modifier = Modifier.height(Dimen.spacing_xxs))

        Text(text = "- ${review.reviewerName}", fontSize = Dimen.font_size_s1, color = Color.Gray)

        HorizontalDivider(thickness = Dimen.spacing_xxxs, color = Color.LightGray)
    }
}

@Composable
fun ProductRowColumn(
    modifier: Modifier = Modifier,
    products: List<ProductX>,
    detailViewModel: DetailViewModel,
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
            .padding(horizontal = Dimen.spacing_m1)
            .clickable {
                onClickForProductId(product.id ?: 1)
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(width = Dimen.spacing_xxxs, color = Color.C_C58F2D),
        shape = RoundedCornerShape(Dimen.spacing_m1),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.spacing_xs),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .padding(Dimen.spacing_xs)
                        .align(Alignment.CenterVertically),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(com.omersungur.compose_ui.R.drawable.loading),
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Text(
                text = product.title.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.spacing_xs),
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                maxLines = 1,
            )

            Text(
                text = product.brand ?: stringResource(R.string.no_brand_found),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.spacing_xs),
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black,
                maxLines = 1,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimen.spacing_xxs),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "$${product.price}",
                    modifier = Modifier.padding(horizontal = Dimen.spacing_xs),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black,
                )

                Row(modifier = Modifier.padding(horizontal = Dimen.spacing_xs)) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = stringResource(R.string.star_icon),
                        tint = Color.C_C58F2D,
                    )

                    Text(text = formattedRating)
                }
            }
        }
    }
}