package com.omersungur.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.model.product.ProductX
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        activity?.setTitle("Search");
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    SearchScreen()
                }
            }
        }
        return view
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    with(uiState) {
        if (loadingState) {
            CircularProgressIndicator()
        }
        if (isSuccess) {
            Column(modifier = Modifier.fillMaxSize()) {
                var text by remember { mutableStateOf("") }


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
                            viewModel.filterProduct(it)
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Sharp.Search,
                            tint = MaterialTheme.colorScheme.outline,
                            contentDescription = "Back button"
                        )
                    },
                    placeholder = {
                        Text(text = "Search")
                    },
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                ProductLazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    items = uiState.products,
                )
            }
        }
    }
}

@Composable
fun ProductLazyColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(8.dp),
    items: List<ProductX>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement
    ) {
        items(items) {
            ProductColumn(product = it)
        }
    }
}

@Composable
fun ProductColumn(
    modifier: Modifier = Modifier,
    product: ProductX
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(product.thumbnail)
        .memoryCacheKey(product.thumbnail)
        .diskCacheKey(product.thumbnail)
        .placeholder(com.omersungur.compose_ui.R.drawable.ic_google)
        .error(com.google.android.material.R.drawable.mtrl_ic_error)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()

    Card(modifier = modifier.fillMaxWidth()) {
        Row {
            AsyncImage(
                model = imageRequest,
                contentDescription = product.title,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = product.title ?: "No Data Found!",
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis,
                )

                RatingBar(rating = product.rating ?: 0.0)
            }
        }
    }
}
@Composable
private fun RatingBar(
    rating: Double,
) {
    if (rating < 2.0) {
        Icon(
            imageVector = Icons.Outlined.StarRate,
            contentDescription = "Star",
            tint = Color.Red
        )
    }
    else if (rating < 4.0) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.StarHalf,
            contentDescription = "Star",
            tint = Color.Green
        )
    }
    else {
        Icon(
            imageVector = Icons.Outlined.Star,
            contentDescription = "Star",
            tint = Color.Yellow
        )
    }
}

@Preview
@Composable
private fun RatingBarPreview() {
    RatingBar(rating = 4.0)
}