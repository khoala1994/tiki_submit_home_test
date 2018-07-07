package vn.tiki.android.androidhometest.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.item_tiki_category.view.*
import vn.tiki.android.androidhometest.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Khoa on 7/7/18.
 */
class Utils {
    companion object {
        fun priceTiki(double: Double): String {
            var price = double.toString() + " VND";
            return price;
        }

        fun countdownPromotion(context: Context, date: Long): String {
            var countdown = ""

            var fmt = SimpleDateFormat(Constant.DATE_FORMAT, Locale.getDefault())
            var time = fmt.format(date);
            var countDate = fmt.calendar.get(Calendar.DATE)
            if (countDate > 1) {
                countdown = String.format(context.getString(R.string.tiki_date_promotion_day), countDate, time)
            } else {
                countdown = time
            }
            return countdown;
        }
    }
}