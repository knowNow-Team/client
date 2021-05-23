package com.example.konwnow.ui.view.test

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Quiz
import com.example.konwnow.data.remote.dto.TestLog
import com.example.konwnow.ui.adapter.TestWordsAdapter
import com.example.konwnow.ui.view.MainActivity
import com.example.konwnow.viewmodel.TestLogViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class TestLogDialog(private var mContext: Context, var lifecycleOwner: LifecycleOwner) {
    companion object{
        private var wordsList = arrayListOf<Quiz.QuizDetail>()
        fun getList(): ArrayList<Quiz.QuizDetail>{
            return wordsList
        }
        fun clearList(){
            wordsList.clear()
        }
    }
    private val dlg = Dialog(mContext)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCancel: ImageButton
    private lateinit var tvTestDate: TextView
    private lateinit var rvWords: RecyclerView
    private lateinit var wordsAdapter: TestWordsAdapter
    private lateinit var viewModel: TestLogViewModel


    fun start(testId: String) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_test_log)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함
        tvTestDate = dlg.findViewById(R.id.tv_dlg_date)

        (App.instance).dialogResize(dlg, 0.9f, 0.7f)
        setAdapter()
        requestWords(testId)

        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }

    private fun setAdapter(){
        rvWords = dlg.findViewById(R.id.rv_word_log)
        rvWords.layoutManager = LinearLayoutManager(mContext)
        wordsAdapter = TestWordsAdapter()
        rvWords.adapter = wordsAdapter
    }

    private fun requestWords(testId: String) {

        viewModel = TestLogActivity.getTestViewModel()
        viewModel.getTestDetailObserver().observe(lifecycleOwner, Observer<TestLog.TestDetails> {
            if (it != null) {
                try {
                    tvTestDate.text = parseDate(it.createdAt)
                }catch (e: ParseException){
                    Log.e("error",e.toString())
                }
                wordsList.clear()
                wordsList.addAll(it.words) //단어
                wordsAdapter.wordsUpdateList(wordsList)
                Log.d("관찰", wordsList.size.toString())
            } else {
                Log.d("view", "view에서 viewModel 관찰 실패")
            }
        })

        viewModel.getTestById(MainActivity.getUserData()!!.loginToken, testId)
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

    private fun parseDate(createdAt: String): String {
        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.KOREA) // 받은 데이터 형식
        oldFormat.timeZone = TimeZone.getTimeZone("KST")
        val newFormat = SimpleDateFormat("yyyy년 MM월 dd일 HH:mm:ss", Locale.KOREA) // 바꿀 데이터 형식

        val oldDate: Date = oldFormat.parse(createdAt)
        return newFormat.format(oldDate)
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


