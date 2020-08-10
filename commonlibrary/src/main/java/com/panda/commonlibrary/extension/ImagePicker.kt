package com.panda.commonlibrary.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/08/10 16:03
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
fun selectPicture(activity: AppCompatActivity, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(activity)
        .openGallery(PictureMimeType.ofImage())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}

fun selectPicture(fragment: Fragment, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(fragment)
        .openGallery(PictureMimeType.ofImage())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}

fun selectVideo(fragment: Fragment, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(fragment)
        .openGallery(PictureMimeType.ofVideo())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}

fun selectVideo(activity: AppCompatActivity, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(activity)
        .openGallery(PictureMimeType.ofVideo())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}

fun openCamera(fragment: Fragment, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(fragment)
        .openCamera(PictureMimeType.ofImage())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}

fun openCamera(activity: AppCompatActivity, onImages: (MutableList<LocalMedia>) -> Unit) {
    PictureSelector
        .create(activity)
        .openCamera(PictureMimeType.ofImage())
        .imageEngine(GlideEngine())
        .forResult(object : OnResultCallbackListener<LocalMedia> {
            override fun onResult(result: MutableList<LocalMedia>?) {
                result?.let { onImages(it) }
            }

            override fun onCancel() {
            }

        })
}