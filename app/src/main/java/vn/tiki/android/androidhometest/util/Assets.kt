package vn.tiki.android.androidhometest.util

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.nio.charset.Charset

fun AssetManager.readFile(file: String): String {
  return open(file).bufferedReader(Charset.forName("utf-8")).use { it.readText() }
}
fun AssetManager.readBitmap(file: String): Bitmap {
  return BitmapFactory.decodeStream(open(file))
}
