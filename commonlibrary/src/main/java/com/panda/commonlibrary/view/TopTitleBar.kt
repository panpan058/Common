package com.panda.commonlibrary.view;

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.panda.commonlibrary.R

/**
 * ZyOng
 * 2020/8/5
 * 说明：
 */
class TopTitleBar(val mContext: Context, attrs: AttributeSet? = null) : Toolbar(mContext, attrs) {
    var isShowBac: Boolean? = null
    var backIcon: Int? = null;
    var mBgColor: Int? = null
    var mRightColor: Int? = null
    var rightTv: TextView? = null
    private var bacContainer //返回按键区域的容器
            : LinearLayout? = null
    private var container: View? = null //title的容器
    private var bacArrow: ImageView? = null//返回箭头
    private var topBarTitle: TextView? = null
    private var rightContainer: LinearLayout? = null//右侧控制区域的容器
    var title: String? = null
    private var rightText: String? = null

    init {
        init()
        val typedArray: TypedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TopTitleBar)
        isShowBac = typedArray.getBoolean(R.styleable.TopTitleBar_isShowBack, false)
        backIcon = typedArray.getResourceId(R.styleable.TopTitleBar_backIcon, R.mipmap.ic_back)
        title = typedArray.getString(R.styleable.TopTitleBar_topTitleBarTitle)
        rightText = typedArray.getString(R.styleable.TopTitleBar_rightTitle)
        mBgColor = typedArray.getColor(
            R.styleable.TopTitleBar_bColor,
            context.resources.getColor(R.color.colorPrimaryDark)
        )
        mRightColor = typedArray.getColor(
            R.styleable.TopTitleBar_rightTextColor,
            context.resources.getColor(R.color.white)
        )
        val color = typedArray.getColor(
            R.styleable.TopTitleBar_titleTextColor,
            mContext.resources.getColor(R.color.white)
        )
        topBarTitle!!.setTextColor(color)

        initView();

        typedArray.recycle()
    }

    private fun initView() {
        bacArrow!!.setImageResource(backIcon!!)
        setBackViewShow()
        setTitleContent(title ?: "")
        rightTv!!.setTextColor(mRightColor!!)
        if (!TextUtils.isEmpty(rightText)) {
            setRightText(rightText!!)
        }
    }


    /**
     * 初始化
     */
    private fun init() {
        setContentInsetsAbsolute(0, 0)
        container =
            LayoutInflater.from(context).inflate(R.layout.top_title_bar, this, false)

        container!!.apply {
            bacContainer = findViewById<View>(R.id.titlebar_back_container_ll) as LinearLayout
            bacArrow = findViewById<View>(R.id.titlebar_back_arrow_iv) as ImageView
            rightContainer = findViewById<View>(R.id.titlebar_right_container_ll) as LinearLayout
            topBarTitle = findViewById<View>(R.id.titlevar_title_tv) as TextView
            rightTv = findViewById<View>(R.id.tilebar_right_tv) as TextView
        }

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        container!!.layoutParams = layoutParams
        this.addView(container)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = dp2px(0F)
        }
    }

    fun setBackViewShow() {
        if (isShowBac!!) {
            bacContainer!!.visibility = View.VISIBLE
            bacListener()

        } else {
            bacContainer!!.visibility = View.GONE
        }
    }

    /*
     * 设置标题
     *
     * */
    fun setTitleContent(string: String) {
        title = string
        topBarTitle!!.text = title
        topBarTitle!!.visibility = VISIBLE
    }

    fun setTitleContent(@StringRes string: Int) {
        title = mContext.getString(string)
        topBarTitle!!.text = title
        topBarTitle!!.visibility = VISIBLE
    }

    /**
     * 设置显示在右侧的内容
     *
     * @param str
     */
    fun setRightText(str: String?) {
        if (rightTv != null) {
            rightContainer!!.setVisibility(VISIBLE)
            rightTv!!.setText(str)
        } else {
            rightContainer!!.setVisibility(View.GONE)
        }
    }


    fun dp2px(dpVal: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dpVal,
            mContext.resources.displayMetrics
        );
    }

    fun setRightClickListener(rightClickListener: OnClickListener) {
        if (rightClickListener != null) {
            rightContainer!!.setOnClickListener(rightClickListener)
        }
    }

    /**
     * 设置是否显示返回按钮
     *
     * @param isShowBac true显示 false不显示
     */
    fun setIsShowBac(isShowBac: Boolean) {
        this.isShowBac = isShowBac
        setBackViewShow()
        bacListener()
    }


    private fun bacListener() {
        bacContainer!!.setOnClickListener {
            if (context is AppCompatActivity) {
                (context as AppCompatActivity).finish()
            } else if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }
}