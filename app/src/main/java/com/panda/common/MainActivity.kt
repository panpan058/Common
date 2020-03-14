package com.panda.common

import android.os.Bundle
import com.panda.commonlibrary.StatusBarUtils
import com.panda.commonlibrary.goActivity
import com.panda.commonlibrary.showCustomDialog
import com.panda.commonlibrary.t
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnClicks()
        StatusBarUtils.steep(this, R.id.tBar)
    }

    private fun setOnClicks() {
        btn_permission.setOnClickListener {
            goActivity(PermissionActivity::class.java)
        }
        btn_qqFace.setOnClickListener {
            goActivity(QQFaceActivity::class.java)
        }
        btn_showDialog.setOnClickListener {
            showCustomDialog(
                this,
                "标题", "内容",
                ensure = {
                    t("确定了")
                }, cancel = {
                    t("取消了")
                }, ensureString = "确定", cancelString = "取消"
            )
        }
    }

//    private fun goActivity(clazz: java.lang.Class<out AppCompatActivity>) {
//        startActivity(Intent(this,clazz))
//    }
}
