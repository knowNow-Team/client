package com.example.konwnow.ui.view.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.konwnow.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


class ImageWriteDialog : BottomSheetDialogFragment() {
    private val PICK_FROM_ALBUM = 1
    private lateinit var listener : ImageWriteDialogListener
    private lateinit var selectedImageUri: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        tedPermission()
        selectedImageUri = Uri.parse("")
        return inflater.inflate(R.layout.dialog_image_write, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<LinearLayout>(R.id.ll_image_camera)?.setOnClickListener {
            Log.d("선택:", "카메라")
            dismiss()
        }

        view?.findViewById<LinearLayout>(R.id.ll_image_gallery)?.setOnClickListener {
            goToAlbum()
        }
    }

    private fun tedPermission() {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // 권한 요청 성공
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String?>?) {
                dismiss()
            }
        }
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()
    }

    private fun goToAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData()!!
        }
        listener.onSuccess(selectedImageUri)
        dismiss()
    }

    fun setSuccessListener(listener: (Uri) -> Unit) {
        this.listener = object: ImageWriteDialogListener {
            override fun onSuccess(content: Uri) {
                listener(content)
            }
        }
    }

    interface ImageWriteDialogListener {
        fun onSuccess(content : Uri)
    }
}