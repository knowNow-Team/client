package com.example.konwnow.ui.view.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.WordBook
import com.example.konwnow.ui.adapter.WordClassAdapter


class WordDialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCancel: ImageButton
    private lateinit var tvEng: TextView
    private lateinit var tvKor : TextView
    private lateinit var tvPhonics : TextView

    private lateinit var rvWordClass : RecyclerView

    @SuppressLint("WrongConstant")
    fun start(content: WordBook.GetAllWordResponseData) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_detail_word)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        (App.instance).dialogResize(dlg, 0.9f, 0.5f)

        tvEng = dlg.findViewById(R.id.tv_dlg_eng)
        tvKor =dlg.findViewById(R.id.tv_meaning)
        tvPhonics = dlg.findViewById(R.id.tv_phonics)

        rvWordClass = dlg.findViewById(R.id.rv_word_class)
        val wordClassAdapter = WordClassAdapter()
        val layoutManager = LinearLayoutManager(App.instance, LinearLayout.HORIZONTAL, false)
        rvWordClass.layoutManager = layoutManager
        rvWordClass.adapter = wordClassAdapter
        wordClassAdapter.notifyDataSetChanged()

        for(data in content.wordsDoc){
            tvEng.text = data.word
            val korText = StringBuilder()
            var i =0
            for(mean in data.meanings){
                korText.append("${i + 1}. $mean \n")
                i++
            }
            tvKor.text = korText
            tvPhonics.text = data.phonics

            for(wordclass in data.wordClasses){
                wordClassAdapter.wordUpdateList(wordclass)
            }
        }

        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }

    // 화면 비율에 맞게 다이얼로그 크기 조정
    fun Context.dialogResize(dialog: Dialog, width: Float, height: Float){
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        if (Build.VERSION.SDK_INT < 30){
            val display = windowManager.defaultDisplay
            val size = Point()

            display.getSize(size)

            val window = dialog.window

            val x = (size.x * width).toInt()
            val y = (size.y * height).toInt()

            window?.setLayout(x, y)

        }else{
            val rect = windowManager.currentWindowMetrics.bounds

            val window = dialog.window
            val x = (rect.width() * width).toInt()
            val y = (rect.height() * height).toInt()

            window?.setLayout(x, y)
        }

    }
}


