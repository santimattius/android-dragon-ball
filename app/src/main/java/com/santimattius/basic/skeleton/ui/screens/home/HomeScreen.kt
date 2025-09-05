package com.santimattius.basic.skeleton.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.annotation.ExperimentalCoilApi
import coil3.asImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.santimattius.basic.skeleton.R
import com.santimattius.basic.skeleton.core.data.Character
import com.santimattius.basic.skeleton.core.data.DragonBallCharacter
import com.santimattius.basic.skeleton.ui.component.AppBar
import com.santimattius.basic.skeleton.ui.component.BasicSkeletonContainer
import com.santimattius.basic.skeleton.ui.component.NetworkImage
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onClick: (Character) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onClick = onClick
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onClick: (Character) -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(id = R.string.app_name),
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator()
                }

                state.hasError -> {
                    Text(text = "Error")
                }

                state.characters.isEmpty() -> {
                    Text(text = "Empty")
                }

                else -> {
                    GridOfCharacters(
                        characters = state.characters,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
private fun GridOfCharacters(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onClick: (Character) -> Unit = {},
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        modifier = modifier
    ) {

        items(characters, key = { it.id }) { character ->
            CharacterItem(
                character = character,
                modifier = Modifier.clickable { onClick(character) },
                onClick = onClick,
            )
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { onClick(character) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        NetworkImage(
            imageUrl = character.imageUrl,
            contentDescription = character.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .aspectRatio(ratio = 0.67f),
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current

    val previewHandler = AsyncImagePreviewHandler {
        ContextCompat.getDrawable(
            context,
            R.drawable.goku_normal
        )?.asImage()!!
    }

    CompositionLocalProvider(
        LocalAsyncImagePreviewHandler provides previewHandler
    ) {
        BasicSkeletonContainer {
            HomeScreen(
                state = HomeUiState(
                    isLoading = false,
                    characters = fakeCharacters
                ),
            )
        }
    }
}

private val fakeCharacters = (1..10).map {
    DragonBallCharacter(
        id = it.toLong(),
        name = "Goku $1",
        imageUrl = "https://dragonball-api.com/characters/goku_normal.webp",
        description = "Goku description"
    )
}