package com.example.konwnow.data.model.dto

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Words(var eng: String, var kor: String,var levelStatus: Int) : Parcelable