package com.omersungur.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.omersungur.compose_ui.theme.BrowseAndBuyAppTheme
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
            CircularProgressIndicator()
        }

        Column(modifier = modifier.fillMaxSize()) {
            if (isSuccess) {
                FlowRow(
                    modifier = Modifier.padding(8.dp),
                ) {
                    categories.forEach {
                        SuggestionChip(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            onClick = {
                                viewModel.getProductsByCategory(it.slug ?: "beauty")
                            },
                            label = { Text(it.name ?: "No Data Found") },
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

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
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = product.title ?: "No Data Found",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Star Icon",
                    tint = Color(0xFFFFA500),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = product.rating.toString(),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }

        Text(
            text = "$${product.price}",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}