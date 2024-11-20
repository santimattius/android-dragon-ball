package com.santimattius.basic.skeleton.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
internal fun NetworkImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl).build(),
        loading = {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}