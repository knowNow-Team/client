package com.example.konwnow.ui.view.home

import java.text.FieldPosition

interface HomeInterface {

    fun changeLevelClicked(filter : String, position: Int)

    fun trashClicked(position: Int)

    fun realDelete(position: Int)
}