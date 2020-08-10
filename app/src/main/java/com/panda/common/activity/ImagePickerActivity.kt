package com.panda.common.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.panda.common.R
import com.panda.common.databinding.ActivityImagePickerBinding
import com.panda.commonlibrary.activity.BaseVBActivity
import com.panda.commonlibrary.extension.load
import com.panda.commonlibrary.extension.openCamera
import com.panda.commonlibrary.extension.selectPicture

class ImagePickerActivity : BaseVBActivity<ActivityImagePickerBinding>() {
    override fun initVB(): ActivityImagePickerBinding {
        return ActivityImagePickerBinding.inflate(layoutInflater)
    }

    override fun initData() {
        vb.ivCamera.setOnClickListener {
            openCamera(
                this,
                onImages = {
                    vb.ivCamera.load(
                        this,
                        it[0].androidQToPath
                    )
                }
            )
        }
        vb.ivPicker.setOnClickListener {
            selectPicture(this
                , onImages = {
                    vb.ivPicker.load(
                        this,
                        it[0].androidQToPath
                    )
                })
        }
    }
}