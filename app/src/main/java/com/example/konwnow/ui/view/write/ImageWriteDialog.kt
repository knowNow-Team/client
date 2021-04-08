package com.example.konwnow.ui.view.write

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.konwnow.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageWriteDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_image_write, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<LinearLayout>(R.id.ll_image_camera)?.setOnClickListener {
            Log.d("선택:","카메라")
        }

        view?.findViewById<LinearLayout>(R.id.ll_image_gallery)?.setOnClickListener {
            Log.d("선택:","갤러리")
        }
    }
}