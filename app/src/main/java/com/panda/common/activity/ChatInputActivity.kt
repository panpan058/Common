package com.panda.common.activity

import cn.jiguang.imui.chatinput.listener.OnMenuClickListener
import cn.jiguang.imui.chatinput.model.FileItem
import com.panda.common.databinding.ActivityChatInputBinding
import com.panda.common.viewmodel.ChatInputViewModel
import com.panda.commonlibrary.activity.BaseActivityVM
import com.panda.commonlibrary.extension.e

class ChatInputActivity : BaseActivityVM<ChatInputViewModel, ActivityChatInputBinding>() {
    override fun initData() {
        vb.chatInput.setMenuContainerHeight(800)
        vb.chatInput.setMenuClickListener(object :OnMenuClickListener{
            override fun switchToMicrophoneMode(): Boolean {
                return true
            }

            override fun switchToEmojiMode(): Boolean {
                return true
            }

            override fun switchToCameraMode(): Boolean {
                return false
            }

            override fun onSendTextMessage(input: CharSequence?): Boolean {
                e(msg = input.toString())
                vb.qqFace.text = input.toString()
                return true
            }

            override fun onSendFiles(list: MutableList<FileItem>?) {
            }

            override fun switchToGalleryMode(): Boolean {
                return true
            }
        })
    }

}