package com.example.konwnow.ui.view.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.konwnow.R

class MypageFragment: Fragment() {

    private lateinit var v : View

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_mypage, container, false)

        val title = v.findViewById<TextView>(R.id.tv_title)
        val btnBack = v.findViewById<ImageButton>(R.id.ib_back)
        btnBack.visibility = View.GONE
        title.text = "마이페이지"

        return v
    }
}