package vn.tiki.android.androidhometest

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import vn.tiki.android.androidhometest.adapter.TikiAdapter
import vn.tiki.android.androidhometest.data.api.ApiServices
import vn.tiki.android.androidhometest.data.api.response.Deal
import vn.tiki.android.androidhometest.di.initDependencies
import vn.tiki.android.androidhometest.di.inject
import vn.tiki.android.androidhometest.di.releaseDependencies

class MainActivity : AppCompatActivity(), TikiAdapter.PromotionListener {

    val apiServices by inject<ApiServices>()
    lateinit var adapter: TikiAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDependencies(this)

        setContentView(R.layout.activity_main)

        adapter = TikiAdapter(this, this)
        val recyclerView: RecyclerView = findViewById(R.id.rv_promotion)
        recyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?;
        recyclerView.adapter = adapter

        object : AsyncTask<Unit, Unit, List<Deal>>() {
            override fun doInBackground(vararg params: Unit?): List<Deal> {
                return apiServices.getDeals()
            }

            override fun onPostExecute(result: List<Deal>?) {
                super.onPostExecute(result)
                result.orEmpty()
                        .forEach { deal ->
                            println(deal)
                        }
                onLoadDataSuccess(result.orEmpty())
            }
        }.execute()
    }

    fun onLoadDataSuccess(data: List<Deal>) {
        adapter?.let {
            val items = adapter!!.items
            items.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun finishTime(position: Int) {

    }

    override fun promotionOnClick(position: Int) {}

    override fun onDestroy() {
        super.onDestroy()
        releaseDependencies()
        adapter?.let { adapter.clearAll() }
    }
}
