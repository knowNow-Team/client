package com.example.konwnow.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Folder(val name: String, var wordsCount: Int) : Parcelable
