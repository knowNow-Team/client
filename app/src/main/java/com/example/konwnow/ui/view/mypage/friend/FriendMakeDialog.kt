package com.example.konwnow.ui.view.mypage.friend

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.konwnow.App
import com.example.konwnow.R
import com.example.konwnow.ui.view.group.MakeGroupInterface

class FriendMakeDialog(context: Context, makeFriendInterface: MakeFriendInterface) : Dialog(context), View.OnClickListener{
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnCancel: ImageButton
    private lateinit var btnMake : Button
    private lateinit var etCode : EditText

    private var makeFriendInterface : MakeFriendInterface? = null

    init{
        this.makeFriendInterface = makeFriendInterface
    }

    fun start() {
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)   //타이틀바 제거
        dlg.setContentView(R.layout.dialog_make_friend)     //다이얼로그에 사용할 xml 파일을 불러옴
        dlg.setCancelable(false)    //다이얼로그의 바깥 화면을 눌렀을 때 다이얼로그가 닫히지 않도록 함

        btnCancel = dlg.findViewById(R.id.btn_cancle)
        btnCancel.setOnClickListener(this)
        dlg.show()

        (App.instance).dialogResize(dlg, 0.9f, 0.5f)

        etCode = dlg.findViewById(R.id.et_friend_code)
        btnMake = dlg.findViewById(R.id.btn_make_friend)
        btnMake.setOnClickListener(this)
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


    override fun onClick(v: View?) {
        when(v){
            btnMake -> {
                this.makeFriendInterface?.makeFriendClicked(etCode.text.toString())
                dlg.dismiss()
            }

            btnCancel -> {
                dlg.dismiss()
            }
        }
    }
}


