package com.omersungur.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.domain.model.product.ProductX

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
    product: ProductX,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Row {
            AsyncImage(
                modifier = Modifier.padding(8.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(com.omersungur.compose_ui.R.drawable.ic_google),
                contentDescription = product.title,
                contentScale = ContentScale.Fit,
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = "Name: ${product.title}")

                Text(text = "Brand: ${product.brand}")

                Row {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Star Icon",
                        tint = Color(0xFFFFA500),
                    )

                    Text(text = product.rating.toString())
                }
            }

            Icon(
                imageVector = Icons.Outlined.Favorite,
                contentDescription = "Favorite Icon",
                tint = Color.Red,
            )
        }
    }
}