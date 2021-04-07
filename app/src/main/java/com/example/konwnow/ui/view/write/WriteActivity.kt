package com.example.konwnow.ui.view.write

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
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
        spinnerArray.add("item2")

        val adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, spinnerArray
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFolder.adapter = adapter

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
        PageAdapter.addFragment(galleryWriteFragment(), "갤러리")
        PageAdapter.addFragment(cameraWriteFragment(), "카메라")
        vpWrite.adapter = PageAdapter
        tabNav.setupWithViewPager(vpWrite)
    }
}