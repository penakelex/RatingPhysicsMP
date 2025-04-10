package org.penakelex.rating_physics.util

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun getFileNameByUri(uri: Uri, context: Context): String? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.contentResolver.query(uri, null, null, null)
            ?.use { cursor ->
                try {
                    val nameIndex = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    cursor.getString(nameIndex)
                } catch (exception: IllegalArgumentException) {
                    exception.printStackTrace()
                    null
                }
            }
    } else uri.lastPathSegment?.substringAfterLast('/')

@Throws(IOException::class)
fun saveFileToCache(uri: Uri, fileName: String, context: Context): String {
    val destinationFile = File(context.cacheDir, fileName)

    context.contentResolver.openInputStream(uri)
        ?.use { inputStream ->
            FileOutputStream(destinationFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

    return destinationFile.absolutePath
}