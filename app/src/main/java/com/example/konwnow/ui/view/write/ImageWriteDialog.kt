package com.example.konwnow.ui.view.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import com.example.konwnow.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ImageWriteDialog : BottomSheetDialogFragment() {
    private val PICK_FROM_ALBUM = 1
    private val REQUEST_IMAGE_CAPTURE = 2
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
            launchCamera()
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

    private fun launchCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            try{
                Log.d("실행", "카메라")
                val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("파일","에러")
                        null
                    }
            // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        context!!,
                        "com.example.konwnow.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }catch(ex1: Exception){
                Log.d("에러", ex1.toString())
            }
        }
    }


    // 카메라로 촬영한 이미지를 파일로 저장
    @Throws(IOException::class)
    private fun createImageFile(): File {
        Log.d("실행","여긴가")
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            selectedImageUri = Uri.parse(absolutePath)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData()!!
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data!!.getData() != null) {
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