package com.example.konwnow.ui.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.konwnow.R
import com.example.konwnow.ui.view.group.GroupActivity

class HomeFragment : Fragment() {

    private lateinit var v: View
    private lateinit var switch: Switch
    private lateinit var groupButton : TextView
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)


        setSwitch()
        setButton()

        return v
    }

    private fun setButton() {
        groupButton = v.findViewById(R.id.tv_group_text)
        groupButton.setOnClickListener {
            activity?.let{
                val intent = Intent(context, GroupActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setSwitch() {
        switch = v.findViewById(R.id.switch_hide)
        switch.isChecked = true
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {

            } else {

            }
        }
    }
}