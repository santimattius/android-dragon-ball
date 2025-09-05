package com.santimattius.basic.skeleton.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.basic.skeleton.R
import com.santimattius.basic.skeleton.core.data.Character
import com.santimattius.basic.skeleton.ui.component.AppBar
import com.santimattius.basic.skeleton.ui.component.AppBarIconModel
import com.santimattius.basic.skeleton.ui.component.NetworkImage


@Composable
fun DetailScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBack: () -> Unit = {}
) {

    DetailScreen(
        modifier = modifier,
        viewModel = viewModel,
        onBack = onBack
    )
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    onBack: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = stringResource(id = R.string.app_name),
                navIcon = AppBarIconModel(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    action = onBack
                )
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.value.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.value.hasError -> {
                    Text("Error while loading")
                }

                state.value.character != null -> {
                    DetailContentView(
                        model = state.value.character!!,
                        onFavoriteClick = {
                            viewModel.toggleFavorite()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailContentView(
    model: Character,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {},
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            NetworkImage(
                imageUrl = model.imageUrl,
                contentDescription = model.name,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.6f)
                    .background(Color.White)
                    .aspectRatio(ratio = 0.67f),
            )
        }
        Row(
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 8.dp
            )
        ) {
            Text(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                text = model.name,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            /*SmallFloatingActionButton(
                onClick = onFavoriteClick
            ) {
                if (isFavorite) {
                    Icon(Icons.Default.Favorite, contentDescription = "")
                } else {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                }
            }*/
        }
        Text(
            text = model.description,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                )
        )
    }

}