package com.example.konwnow.ui.view.test

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.ui.adapter.TestWordsAdapter

class TestLogDialog(context: Context) {
    var mContext = context
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCancel: ImageButton
    private lateinit var tvEng: TextView
    private lateinit var rvWords: RecyclerView
    private lateinit var wordsAdapter: TestWordsAdapter
    private var wordsList = arrayListOf<Quiz>()


    fun start(date: String) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_test_log)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        (App.instance).dialogResize(dlg, 0.9f, 0.7f)

        tvEng = dlg.findViewById(R.id.tv_dlg_date)
        tvEng.text = date

        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        setWord()
        dlg.show()
    }

    fun setWord(){
        requestWords()
        rvWords = dlg.findViewById(R.id.rv_word_log)
        rvWords.layoutManager = LinearLayoutManager(mContext)
        wordsAdapter.wordsUpdateList(wordsList)
        rvWords.adapter = wordsAdapter
    }

    private fun requestWords() {
        wordsList.clear()

        wordsAdapter = TestWordsAdapter()
//        wordsList.add(Quiz("Complex", "복잡한","complex",true))
//        wordsList.add(Quiz("movie", "영화관","movie",true))
//        wordsList.add(Quiz("Fragment", "조각","Fragment",true))
//        wordsList.add(Quiz("Complex", "복잡한","cococo",false))
//        wordsList.add(Quiz("movie", "영화관","momomo",false))
//        wordsList.add(Quiz("Fragment", "조각","frfr",false))
//        wordsList.add(Quiz("Complex", "복잡한","plpl",false))
//        wordsList.add(Quiz("movie", "영화관","movie",true))
//        wordsList.add(Quiz("Fragment", "조각","fafa",false))

    }


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


