package com.omersungur.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
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
import com.omersungur.compose_ui.component.bar.CustomProgressBar
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
import com.omersungur.compose_ui.theme.C_C58F2D
import com.omersungur.compose_ui.theme.Dimen
import com.omersungur.domain.model.product.ProductX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                BrowseAndBuyAppTheme {
                    Scaffold {
                        Surface(modifier = Modifier.padding(it)) {
                            CategoryScreen()
                        }
                    }
                }
            }
        }
        return view
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    with(uiState) {
        if (loadingState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CustomProgressBar()
            }
        }

        Column(modifier = modifier.fillMaxSize()) {
            if (isSuccess) {
                FlowRow(
                    modifier = Modifier.padding(Dimen.spacing_xs),
                ) {
                    categories.forEach {
                        SuggestionChip(
                            modifier = Modifier.padding(horizontal = Dimen.spacing_xxs),
                            onClick = {
                                viewModel.getProductsByCategory(it.slug ?: "beauty")
                            },
                            label = { Text(it.name ?: "No Data Found") },
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = Dimen.spacing_xs))

            if (categorySuccess) {
                LazyColumn {
                    items(categoryProducts) {
                        ProductsByCategoryRow(product = it)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsByCategoryRow(
    modifier: Modifier = Modifier,
    product: ProductX,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimen.spacing_m1),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .padding(Dimen.spacing_xs),
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .placeholder(com.omersungur.compose_ui.R.drawable.loading)
                .build(),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimen.spacing_m1),
        ) {
            Text(
                text = product.title ?: stringResource(R.string.no_data_found),
                fontSize = Dimen.font_size_18,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )

            Spacer(modifier = Modifier.height(Dimen.spacing_xs))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(Dimen.spacing_m2),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = stringResource(R.string.star_icon),
                    tint = Color.C_C58F2D,
                )

                Spacer(modifier = Modifier.width(Dimen.spacing_xxs))

                Text(
                    text = product.rating.toString(),
                    fontSize = Dimen.font_size_m1,
                    color = Color.Gray,
                )
            }
        }

        Text(
            text = "$${product.price}",
            modifier = Modifier.padding(horizontal = Dimen.spacing_xs),
            fontSize = Dimen.font_size_m1,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
        )
    }
}