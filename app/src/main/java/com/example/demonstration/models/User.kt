package com.example.demonstration.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: Int?= 0,
    @SerializedName("login")
    val login: String ?= "",
    @SerializedName("url")
    val url: String ?= "",
    @SerializedName("type")
    val type: String ?= "",
    @SerializedName("avatar_url")
    val avatarUrl: String ?= ""
)
