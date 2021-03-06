package com.example.konwnow.ui.view.mypage

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.konwnow.R


class CommentActivity : AppCompatActivity() {

    private lateinit var spnFolder : Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        setToolbar()
        setFolders()
        setEdit()
    }

    private fun setEdit() {
        val comment = findViewById<EditText>(R.id.et_comment_box)
        val submit = findViewById<Button>(R.id.button2)
        submit.setOnClickListener {
            if(comment.text.toString() != null){
                val dlg: AlertDialog.Builder = AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
                dlg.setTitle("의견 보내기")
                dlg.setMessage("성공적으로 전달하였습니다.")
                dlg.setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
                dlg.show()
            }else{
                val dlg: AlertDialog.Builder = AlertDialog.Builder(this,android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
                dlg.setTitle("의견 보내기")
                dlg.setMessage("내용이 없습니다.")
                dlg.setNeutralButton("확인", DialogInterface.OnClickListener { dialog, which ->
                })
                dlg.show()
            }


        }

    }

    private fun setFolders() {
        val spinnerArray: MutableList<String> = ArrayList()
        spnFolder = findViewById(R.id.spn_folder)
        spinnerArray.add("버그")
        spinnerArray.add("불편한 점")
        spinnerArray.add("기능 추천")
        spinnerArray.add("기타")
        spinnerArray.add("카테고리 선택")

        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray) {
                override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                    val v = super.getView(position, convertView, parent)
                    return v
                }

                override fun getCount(): Int {
                    return super.getCount() - 1
                }

            }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnFolder.adapter = adapter
        spnFolder.setSelection(adapter.count)
        spnFolder.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(position == 0){

                }
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
            }
        }

    }

    private fun setToolbar() {
        val title  = findViewById<TextView>(R.id.tv_title)
        title.text = "의견보내기"

        val btnBack = findViewById<ImageButton>(R.id.ib_back)
        btnBack.setOnClickListener {
            setResult(1)
            finish()
        }

    }
}