package com.example.konwnow.ui.view.group

import com.example.konwnow.data.remote.dto.WordBook

interface ApplyGroupsInterface {

    fun applyGroupsCliked(selectedList : ArrayList<WordBook.WordBooks>)

    fun deleteWordBookClicked(postion : Int)

    fun editWordBookClicked(postion: Int)
}