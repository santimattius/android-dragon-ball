package com.santimattius.basic.skeleton.core.data

import androidx.compose.runtime.Stable

@Stable
interface Character {
    val id: Long
    val name: String
    val description: String
    val imageUrl: String
}