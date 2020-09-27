package com.panda.commonlibrary

import java.io.File

/**
 * <pre>
 *     Created by ppW
 *     e-mail : wangpanpan05@163.com
 *     time   : 2020/09/10 11:18
 *     desc   :
 *     version: 1.0   初始化
 *     params:
 *  <pre>
 */
fun main() {
    println("改名开始")
    changeFileNames(
        "更多(4)",
        "ic_school_more",
        "C:\\Users\\Administrator\\Desktop\\网球校园六月\\ST校园修改切图\\android",
        "D:\\Common\\commonlibrary\\src\\main\\res"
    )
    println("改名结束")
}

fun changeFileNames(
    oldName: String,
    newName: String,
    fromDir: String?,
    toDir: String
) {
    val file = File(fromDir)
    val files = file.listFiles()
    for (f in files) {
        if (f.isDirectory) {
            changeFileNames(oldName, newName, f.absolutePath, toDir)
        } else {
            var parentFile = f.parentFile.absolutePath
            val parentPath = parentFile.substring(parentFile.lastIndexOf(File.separatorChar))
            val name = f.name
            val off = name.substring(name.lastIndexOf("."))
            val fileName = name.substring(0, name.lastIndexOf("."))
            if (fileName == oldName) {
                println("修改的文件:${parentFile}-${name}->${newName}")
                f.renameTo(File(f.parent + File.separatorChar + newName + off))
                val toFilePath = File("$toDir${File.separatorChar}$parentPath")
                if (!toFilePath.exists()) {
                    toFilePath.mkdirs()
                }
                val toFile = File(toFilePath, newName + off)
                f.copyTo(toFile, true)
            }
        }
    }
}