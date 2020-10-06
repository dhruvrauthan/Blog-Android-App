package com.example.blogapp.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blogapp.R
import com.example.blogapp.beans.UserPost
import com.example.blogapp.feature.addPost.AddPostActivity
import com.example.blogapp.feature.editPost.EditPostActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickListener {

    private lateinit var mMainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        mMainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mMainViewModel.initRetrofit()

        getPosts()
    }

    private fun initView() {
        main_progressbar.visibility = View.VISIBLE
        add_post_fab.setOnClickListener {
            startActivity(Intent(this, AddPostActivity::class.java))
        }
    }

    private fun getPosts() {
        mMainViewModel.getPosts().observe(this, Observer {
            if (it) {
                mMainViewModel.mUserPostsLiveData.observe(this, Observer {
                    initRecyclerView(it)
                })
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView(arrayList: ArrayList<UserPost>) {
        main_progressbar.visibility = View.GONE
        main_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        main_recyclerview.adapter = MainAdapter(arrayList, this, this)
    }

    override fun onItemClick(userPost: UserPost) {
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra("_id", userPost._id)
        intent.putExtra("title", userPost.title)
        intent.putExtra("content", userPost.content)
        intent.putExtra("author", userPost.author)

        startActivity(intent)
    }

}