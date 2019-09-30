package com.pix.testcoordinatorlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_test_recycler_view.*

/**
 * 这个来测试CoordinatorLayout和RecyclerView结合产生的滑动问题
 */
class TestRecyclerView : AppCompatActivity() {
    var layoutManager:LinearLayoutManager? = null
    var adapter:RcvAdapter? = null
    var dataList:MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_recycler_view)
        layoutManager = LinearLayoutManager(this)
        rcv_list.layoutManager = layoutManager
        adapter = RcvAdapter()
        adapter?.dataList = dataList
        rcv_list.adapter = adapter
        initData()
    }


    fun initData() {
        for (i in 1..20) {
            dataList.add("＝＝＝＝＝＝＝＝第${i}条数据＝＝＝＝＝＝＝＝＝＝")
        }
        adapter?.notifyDataSetChanged()
    }
}
