package vn.tiki.android.androidhometest.adapter

import android.content.Context
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.binder.TikiViewHolder
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.util.Utils
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by Khoa on 7/7/18.
 */
class TikiAdapter(val context: Context, val listener: PromotionListener) : RecyclerView.Adapter<TikiViewHolder>() {
    val items = ArrayList<Deal>()
    val mapTimer = HashMap<Int, CountDownTimer>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TikiViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tiki_category, parent, false);
        return TikiViewHolder(view, context, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TikiViewHolder, position: Int) {
        holder.setData(items.get(position))
        holder.timer?.let {
            holder.timer!!.cancel()
        }
        var time = items.get(position).endDate.time - Calendar.getInstance().timeInMillis

        holder.timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                holder.updatePromotion(millisUntilFinished)
            }

            override fun onFinish() {
                val handel = Handler()
                handel.post(Runnable {
                    kotlin.run {
                        items.removeAt(position)
                        clearAll()
                        notifyDataSetChanged()
                    }
                })

            }
        }
        holder.timer!!.start()
        mapTimer.put(position, holder.timer as CountDownTimer);
    }

    fun clearAll() {
        for (timer in mapTimer.values) {
            timer.cancel()
        }
    }

    interface PromotionListener {
        fun finishTime(position: Int)
        fun promotionOnClick(position: Int)
    }
}