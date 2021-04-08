package com.example.konwnow.ui.view.write

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.konwnow.R
import com.example.konwnow.ui.adapter.WriteAdapter
import com.google.android.material.tabs.TabLayout


class WriteActivity: AppCompatActivity() {
    private lateinit var btnBack : ImageButton
    private lateinit var btnSubmit : Button
    private lateinit var spnFolder : Spinner
    private lateinit var tabNav : TabLayout
    private lateinit var vpWrite : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)
        setDefault()
        setFolders()
    }

    private fun setFolders() {
        val spinnerArray: MutableList<String> = ArrayList()
        spinnerArray.add("item1")
        spinnerArray.add("gwgwgwgwegwegwegwegw")

        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, spinnerArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFolder.adapter = adapter
        spnFolder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when (position) {
                    0 -> {

                    }
                    1 -> {

                    }
                    else -> {

                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun setDefault(){
        btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnSubmit = findViewById<Button>(R.id.btn_submit)
        spnFolder = findViewById<Spinner>(R.id.spn_folder)
        tabNav = findViewById<TabLayout>(R.id.tab_input_mode)
        vpWrite = findViewById<ViewPager>(R.id.vp_write)

        btnBack.setOnClickListener { finish() }
        btnSubmit.setOnClickListener{ finish() }

        val PageAdapter = WriteAdapter(supportFragmentManager)
        PageAdapter.addFragment(TextWriteFragment(), "직접등록")
        PageAdapter.addFragment(ImageWriteFragment(), "사진")
        vpWrite.adapter = PageAdapter
        tabNav.setupWithViewPager(vpWrite)
    }
}