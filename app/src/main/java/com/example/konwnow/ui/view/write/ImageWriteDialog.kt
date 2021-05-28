package com.example.konwnow.ui.view.write

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.FileProvider
import com.example.konwnow.R
import com.example.konwnow.utils.CAMERA
import com.example.konwnow.utils.Constants.TAG
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImageWriteDialog : BottomSheetDialogFragment() {

    private lateinit var listener : ImageWriteDialogListener
    private lateinit var selectedImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                Log.d(TAG, "권한이 없습니다")
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
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        intent.type = "image/*"
        startActivityForResult(intent, CAMERA.PICK_FROM_ALBUM)
    }

    private fun launchCamera(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if(context!!.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                try{
                    Log.d("실행", "카메라")
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("파일", "에러")
                        listener.onSuccess(Uri.parse(""))
                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            context!!,
                            context!!.packageName + ".fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, CAMERA.REQUEST_IMAGE_CAPTURE)
                    }
                }catch (ex1: Exception){
                    Log.d("에러", ex1.toString())
                    listener.onSuccess(Uri.parse(""))
                }
            }else{
                Log.d(TAG, "실행 불가")
            }

        }
    }


    // 카메라로 촬영한 이미지를 파일로 저장
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File? = context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val storageDir: File? = context!!.filesDir
        var image = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            selectedImageUri = Uri.parse(absolutePath)
        }
        Log.d(TAG, "createImageFile : " + image.absolutePath)
        return image
    }


    private fun getRealPathFromURI(contentUri: Uri): Uri? {
        var column_index = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context!!.contentResolver.query(contentUri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return Uri.parse(cursor.getString(column_index))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA.PICK_FROM_ALBUM && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.data?.let { getRealPathFromURI(it) }!!
            Log.d("이거", selectedImageUri.toString())

            Log.d("데이터", data.data.toString())
        }

        if (requestCode == CAMERA.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null && data!!.getData() != null) {

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
        fun onSuccess(content: Uri)
    }
}