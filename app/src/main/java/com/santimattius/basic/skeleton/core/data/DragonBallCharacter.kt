package com.santimattius.basic.skeleton.core.data

import com.google.gson.annotations.SerializedName

data class DragonBallCharacter(
    @SerializedName("id") override val id: Long,
    @SerializedName("name") override val name: String,
    @SerializedName("description") override val description: String,
    @SerializedName("image") override val imageUrl: String,
) : Character

data class DragonBallCharacterResponse(
    @SerializedName("items") val items: List<DragonBallCharacter>
)