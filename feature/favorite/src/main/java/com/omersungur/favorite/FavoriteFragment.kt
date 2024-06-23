package com.omersungur.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.model.favorite.FavoriteProduct

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    Scaffold {
                        Surface(modifier = Modifier.padding(it)) {
                            FavoriteScreen(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
            }
        }
        return view
    }
}

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {

        if (isSuccess) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(favoriteProducts) {
                    FavoriteProductLazyColumn(favoriteProduct = it)
                }
            }
        }
    }
}

@Composable
fun FavoriteProductLazyColumn(
    modifier: Modifier = Modifier,
    favoriteProduct: FavoriteProduct,
) {

    Card(modifier = modifier.fillMaxSize()) {
        Row {
            AsyncImage(
                modifier = Modifier.padding(8.dp),
                alignment = Alignment.Center,
                model = ImageRequest.Builder(LocalContext.current)
                    .data(favoriteProduct.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(com.omersungur.compose_ui.R.drawable.ic_google),
                contentDescription = favoriteProduct.name,
                contentScale = ContentScale.Fit,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.Center) {

                Text(text = "Name: ${favoriteProduct.name}")

                Text(text = "Brand: ${favoriteProduct.brand}")

                Row {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Star Icon",
                        tint = Color(0xFFFFA500),
                    )

                    Text(text = favoriteProduct.rating.toString())

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red,
                    )
                }
            }
        }
    }
}