package com.codigoj.studyapp

import BlogRecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.codigoj.studyapp.Interface.OnBlogListener
import com.codigoj.studyapp.database.DataSource
import com.codigoj.studyapp.utils.MyItemTouchHelper
import com.codigoj.studyapp.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnBlogListener {

    private lateinit var blogAdapter: BlogRecyclerAdapter
    private lateinit var callback: ItemTouchHelper.Callback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        addDataSet()
        var callback :ItemTouchHelper.Callback = MyItemTouchHelper(blogAdapter)
        var itemTouchHelper = ItemTouchHelper(callback)
        blogAdapter.setTouchHelper(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        blogAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingItemDecoration = TopSpacingItemDecoration(30, 15)
            addItemDecoration(topSpacingItemDecoration)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            blogAdapter = BlogRecyclerAdapter(this@MainActivity)
            recycler_view.adapter = blogAdapter
        }
    }

    override fun onBlogClick(position: Int) {
        Toast.makeText(this, blogAdapter.items.get(position).username, Toast.LENGTH_SHORT).show()
    }

}
