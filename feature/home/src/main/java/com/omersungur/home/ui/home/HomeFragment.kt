package com.omersungur.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.component.bar.CustomProgressBar
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_008B8B
import com.omersungur.compose_ui.theme.C_C58F2D
import com.omersungur.compose_ui.theme.Dimen
import com.omersungur.domain.model.cart.CartProduct
import com.omersungur.domain.model.category.Category
import com.omersungur.domain.model.favorite.FavoriteProduct
import com.omersungur.domain.model.product.ProductX
import com.omersungur.home.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    Scaffold {
                        Surface(modifier = Modifier.padding(it)) {
                            HomeScreen()
                        }
                    }
                }
            }
        }
        return view
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var text by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = Dimen.spacing_xs),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_m1),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.discover),
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                fontSize = 20.sp,
            )

            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = stringResource(R.string.shopping_cart),
                tint = Color.Black,
            )
        }

        Spacer(modifier = Modifier.height(Dimen.spacing_m1))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_m1),
            value = text,
            onValueChange = {
                text = it

                debounceJob?.cancel()
                debounceJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                    viewModel.getProducts(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Sharp.Search,
                    contentDescription = stringResource(R.string.search_icon),
                )
            },
            placeholder = {
                Text(text = stringResource(R.string.search_text))
            },
        )

        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_m1),
            painter = painterResource(id = R.drawable.banner1),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.banner_image),
        )

        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.spacing_m1),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.categories_text),
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                color = Color.Black,
            )

            Text(
                text = stringResource(R.string.see_all),
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                color = Color.Black,
            )
        }

        Spacer(modifier = Modifier.height(Dimen.spacing_xs))

        with(uiState) {
            if (loadingState) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Center,
                ) {
                    CustomProgressBar()
                }
            }

            if (isSuccessForDetail) {
                CategoryLazyRow(categories = categories)

                Spacer(modifier = Modifier.height(Dimen.spacing_xs))

                ProductLazyColumn(
                    products = products,
                    viewModel = viewModel,

                )
            }

            if (isRequestSuccess) {
                Toast.makeText(LocalContext.current, stringResource(R.string.success), Toast.LENGTH_SHORT).show()
                viewModel.updateRequestSuccessStatesWithDefaultValues()
            }
        }
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    categoryName: String,
    onCategoryClick: (String) -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = { onCategoryClick(categoryName) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.C_008B8B),
    ) {
        Text(text = categoryName)
    }
}

@Composable
fun CategoryLazyRow(
    modifier: Modifier = Modifier,
    categories: List<Category>
) {
    LazyRow(
        modifier = modifier.padding(start = Dimen.spacing_m1),
        horizontalArrangement = Arrangement.spacedBy(Dimen.spacing_xs),
    ) {
        items(categories) {
            CategoryRow(categoryName = it.name.orEmpty()) { name ->
                println(name)
            }
        }
    }
}

@Composable
fun ProductLazyColumn(
    modifier: Modifier = Modifier,
    products: List<ProductX>,
    viewModel: HomeViewModel,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(Dimen.spacing_xxs),
    ) {
        items(products) { product ->
            val isFavorite = viewModel.isProductFavorite(product.id ?: -1)

            ProductCard(
                product = product,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    if (isFavorite) {
                        viewModel.deleteFavoriteProduct(
                            FavoriteProduct(
                                id = product.id ?: -1,
                                name = product.title.orEmpty(),
                                imageUrl = product.thumbnail.orEmpty(),
                                brand = product.brand.orEmpty(),
                                rating = product.rating ?: 0.0,
                            )
                        )
                    } else {
                        viewModel.insertFavoriteProduct(
                            FavoriteProduct(
                                id = product.id ?: -1,
                                name = product.title.orEmpty(),
                                imageUrl = product.thumbnail.orEmpty(),
                                brand = product.brand.orEmpty(),
                                rating = product.rating ?: 0.0,
                            )
                        )
                    }

                },
                onAddToCartClicked = {
                    val cartProduct = CartProduct(product.id ?: 1, 1)
                    viewModel.addProductsToCart(cartProduct.id, listOf(cartProduct))
                },
            )
        }
    }
}

@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    product: ProductX,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onAddToCartClicked: () -> Unit,
) {
    val formattedRating = String.format(locale = Locale.getDefault(), "%.1f", product.rating)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.spacing_m1),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(width = Dimen.spacing_xxxs, color = Color.C_008B8B),
        shape = RoundedCornerShape(Dimen.spacing_m1),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.spacing_xs),
            ) {

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(R.string.favorite_icon),
                        tint = Color.Red,
                    )
                }

                AsyncImage(
                    modifier = Modifier
                        .padding(Dimen.spacing_xs)
                        .align(Alignment.CenterVertically),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.thumbnail)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_cart),
                    contentDescription = product.title,
                    contentScale = ContentScale.Fit,
                )
            }

            Text(
                text = product.title.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimen.spacing_xs),
                overflow = TextOverflow.Ellipsis,
                fontFamily = robotoFamily,
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
                fontFamily = robotoFamily,
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
                    fontFamily = robotoFamily,
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

            Button(
                onClick = onAddToCartClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = Dimen.spacing_xs, end = Dimen.spacing_xs, bottom = Dimen.spacing_xs)
                    .align(CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color.C_008B8B),
            ) {
                Text(text = stringResource(R.string.add_to_cart))
            }
        }
    }
}

val robotoFamily = FontFamily(
    Font(R.font.roboto_bold, FontWeight.Light),
    Font(R.font.roboto_light, FontWeight.Normal),
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
)
