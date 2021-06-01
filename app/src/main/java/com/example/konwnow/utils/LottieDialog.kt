package com.example.konwnow.utils

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.example.konwnow.App
import com.example.konwnow.R

class LottieDialog(var context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var lottieView: LottieAnimationView
    private lateinit var okButton: Button


    @SuppressLint("ClickableViewAccessibility")
    fun start(rawRes:Int) {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_lottie)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        (App.instance).dialogResize(dlg, 0.95f, 0.63f)

        lottieView = dlg.findViewById(R.id.lottie)
        okButton = dlg.findViewById(R.id.btn_ok)
        lottieView.setAnimation(rawRes)
        lottieView.repeatCount = ValueAnimator.INFINITE

        dlg.show()
    }

    fun loadingComplete(){
        lottieView.repeatCount = 0
        okButton.isClickable = true
        okButton.text = "확인"
        okButton.background = context.resources.getDrawable(R.drawable.btn_submit)
        okButton.setOnClickListener {
            dismiss()
        }
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

    fun dismiss() {
        dlg.dismiss()
    }
}


