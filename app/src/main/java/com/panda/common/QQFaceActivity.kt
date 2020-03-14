package com.panda.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_q_q_face.*

class QQFaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_q_face)
        QMUIStatusBarHelper.translucent(this)
        qfv.text = "这是一行很长很长[微笑][微笑][微笑][微笑]的文本，但是[微笑][微笑][微笑][微笑]只能单行显示"
    }
}
