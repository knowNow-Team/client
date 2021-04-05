package com.example.konwnow.ui.view.home

import android.icu.lang.UCharacter.IndicPositionalCategory.RIGHT
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeHelperCallBack : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, RIGHT) // 1. drag, 2.swipe
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    )= false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
}