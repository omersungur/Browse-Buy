package com.omersungur.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.omersungur.compose_ui.component.bar.CustomProgressBar
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.Dimen
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CustomProgressBar()
            }
        }
        if (isSuccess) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimen.spacing_m1),
            ) {
                var text by remember { mutableStateOf("") }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimen.spacing_xs)
                        .shadow(1.dp, RoundedCornerShape(1.dp)),
                    value = text,
                    onValueChange = {
                        text = it

                        debounceJob?.cancel()
                        debounceJob = CoroutineScope(Dispatchers.Main).launch {
                            delay(500)
                            viewModel.filterProduct(it)
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Sharp.Search,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            contentDescription = "Search Icon"
                        )
                    },
                    placeholder = {
                        Text(text = "Search", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    },
                    shape = RoundedCornerShape(Dimen.spacing_xs),
                )

                Spacer(modifier = Modifier.height(Dimen.spacing_m1))

                ProductLazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    items = uiState.products,
                )
            }
        }
    }
}

@Composable
fun ProductLazyColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(Dimen.spacing_xs),
    items: List<ProductX>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
    ) {
        items(items) {
            ProductColumn(product = it)
        }
    }
}

@Composable
fun ProductColumn(
    modifier: Modifier = Modifier,
    product: ProductX,
) {
    val context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(product.thumbnail)
        .memoryCacheKey(product.thumbnail)
        .diskCacheKey(product.thumbnail)
        .placeholder(com.omersungur.compose_ui.R.drawable.loading)
        .error(com.google.android.material.R.drawable.mtrl_ic_error)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.spacing_xxs)
            .shadow(Dimen.spacing_xxs, RoundedCornerShape(Dimen.spacing_xs)),
        shape = RoundedCornerShape(Dimen.spacing_xs),
    ) {
        Row(
            modifier = Modifier.padding(Dimen.spacing_m1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(Dimen.spacing_xs))
            )

            Spacer(modifier = Modifier.width(Dimen.spacing_m1))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title ?: "No Data Found!",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                RatingBar(rating = product.rating ?: 0.0)

                Spacer(modifier = Modifier.height(Dimen.spacing_xxs))

                Text(
                    text = "Price: \$${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun RatingBar(
    rating: Double,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(5) { index ->
            val tint = when {
                index < rating -> Color.Blue
                index < rating + 0.5 -> Color.LightGray
                else -> Color.LightGray
            }
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.star_icon),
                tint = tint
            )
        }
    }
}