package vn.tiki.android.androidhometest.binder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import vn.tiki.android.androidhometest.R
import vn.tiki.android.androidhometest.data.api.response.Deal
import android.os.CountDownTimer
import android.os.Handler
import vn.tiki.android.androidhometest.adapter.TikiAdapter
import vn.tiki.android.androidhometest.util.Utils
import vn.tiki.android.androidhometest.util.readBitmap
import java.util.*


/**
 * Created by Khoa on 7/7/18.
 */
class TikiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var context: Context? = null
    private lateinit var listener: TikiAdapter.PromotionListener
    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDate: TextView
    private lateinit var ivThumbnail: ImageView
    var timer: CountDownTimer? = null


    constructor(itemView: View, context: Context, listener: TikiAdapter.PromotionListener) : this(itemView) {
        this.context = context
        this.listener = listener
        tvTitle = itemView.findViewById(R.id.tv_name)
        tvDate = itemView.findViewById(R.id.tv_time_out)
        tvPrice = itemView.findViewById(R.id.tv_price)
        ivThumbnail = itemView.findViewById(R.id.iv_thumnail)
    }

    fun setData(item: Deal) {
        var time = item.endDate.time - Calendar.getInstance().timeInMillis
        tvDate.setText(context!!.getString(R.string.tiki_date_promotion, Utils.countdownPromotion(context!!, time)))
        val bitmap = context!!.assets.readBitmap(item.productThumbnail)
        bitmap?.let {
            ivThumbnail.setImageBitmap(bitmap)
        }
        tvTitle.setText(item.productName)
        tvPrice.setText(Utils.priceTiki(item.productPrice))
        itemView.setOnClickListener(View.OnClickListener { listener?.let { listener.promotionOnClick(adapterPosition) } })
    }

    fun updatePromotion(time:Long) {
        tvDate.setText(context!!.getString(R.string.tiki_date_promotion, Utils.countdownPromotion(context!!, time)))
    }
}