package com.omersungur.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
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
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
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
            .padding(top = 8.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Discover",
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                fontSize = 20.sp,
            )

            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Shopping Cart",
                tint = Color.Black,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
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
                    contentDescription = "Search Icon",
                )
            },
            placeholder = {
                Text(text = "Search")
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            painter = painterResource(id = R.drawable.banner1),
            contentScale = ContentScale.Crop,
            contentDescription = "Banner Image",
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Categories",
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                color = Color.Black,
            )

            Text(
                text = "See All",
                fontWeight = FontWeight.Bold,
                fontFamily = robotoFamily,
                color = Color.Blue,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        with(uiState) {
            if (loadingState) {
                CircularProgressIndicator()
            }

            if (isSuccess) {
                CategoryLazyRow(categories = categories)

                Spacer(modifier = Modifier.height(8.dp))

                ProductLazyColumn(
                    products = products,
                    viewModel = viewModel,
                    onAddToCartClicked = {

                    },
                )
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
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF008b8b))
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
        modifier = modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
    onAddToCartClicked: () -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
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
                                rating = product.rating ?: 0.0
                            )
                        )
                    } else {
                        viewModel.insertFavoriteProduct(
                            FavoriteProduct(
                                id = product.id ?: -1,
                                name = product.title.orEmpty(),
                                imageUrl = product.thumbnail.orEmpty(),
                                brand = product.brand.orEmpty(),
                                rating = product.rating ?: 0.0
                            )
                        )
                    }

                },
                onAddToCartClicked = onAddToCartClicked,
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
            .padding(horizontal = 16.dp),
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

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

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
                fontFamily = robotoFamily,
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
                fontFamily = robotoFamily,
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
                    fontFamily = robotoFamily,
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

            Button(
                onClick = onAddToCartClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .align(CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500)
                ),
            ) {
                Text(text = "Add to Cart")
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
