package com.example.konwnow.ui.view.mypage

import FadeOutTransformation
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.data.remote.dto.Manual
import com.example.konwnow.ui.adapter.ManualAdapter
import me.relex.circleindicator.CircleIndicator3

class ManualDialog(context: Context) {
    var mContext = context
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCancel: ImageButton
    private lateinit var manualVp: ViewPager2
    private lateinit var manualAdapter: ManualAdapter
    private var manualList = arrayListOf<Manual>()
    private var mIndicator: CircleIndicator3? = null
    private val num_page = 3

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_manual)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        (App.instance).dialogResize(dlg, 0.9f, 0.7f)

        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        setManual()
        setViewPager()
        dlg.show()
    }

    private fun setViewPager(){
        manualVp = dlg.findViewById(R.id.vp_manual) as ViewPager2
        manualAdapter = ManualAdapter()
        manualAdapter.manualUpdateList(manualList)
        //뷰페이저 애니메이션
        manualVp.setPageTransformer(FadeOutTransformation())
        manualVp.adapter = manualAdapter

        //뷰페이저 인디케이터
        mIndicator = dlg.findViewById(R.id.indicator);
        mIndicator!!.setViewPager(manualVp);
        mIndicator!!.createIndicators(num_page, 0)

    }


    private fun setManual() {
        manualList.clear()

        manualList.add(Manual(R.raw.ranking_lottie, mContext.getString(R.string.testString)))
        manualList.add(Manual(R.raw.add_document, "설명2"))
        manualList.add(Manual(R.raw.knownow, "설명3"))
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


