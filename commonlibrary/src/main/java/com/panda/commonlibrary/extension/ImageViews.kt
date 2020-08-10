package com.panda.commonlibrary.extension

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.panda.commonlibrary.R
import java.io.File

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/10 15:20
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
fun ImageView.load(context: Context, url: String) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(this)
}

fun ImageView.load(context: Context, url: File) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(this)
}

fun ImageView.load(context: Context, @DrawableRes url: Int) {
    Glide.with(context)
        .asBitmap()
        .load(url)
        .into(this)
}

fun ImageView.loadCircle(context: Context, url: String) {
    Glide.with(context)
        .asBitmap()
        .transform(CircleCrop())
        .load(url)
        .into(this)
}

fun ImageView.loadRound(context: Context, url: String, radius: Int) {
    Glide.with(context)
        .asBitmap()
        .transform(RoundedCorners(radius))
        .load(url)
        .into(this)
}

fun ImageView.loadRound(context: Context, url: String, tl: Float, tr: Float, br: Float, bl: Float) {
    Glide.with(context)
        .asBitmap()
        .transform(GranularRoundedCorners(tl,tr,br,bl))
        .load(url)
        .into(this)
}