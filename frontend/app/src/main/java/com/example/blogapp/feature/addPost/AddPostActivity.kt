package com.example.blogapp.feature.addPost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.blogapp.R
import com.example.blogapp.beans.UserPost
import com.example.blogapp.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_add_post.*
import java.util.*

class AddPostActivity : AppCompatActivity() {

    private lateinit var mAddPostViewModel: AddPostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        mAddPostViewModel = ViewModelProvider(this).get(AddPostViewModel::class.java)
        mAddPostViewModel.initRetrofit()

        initView()
    }

    private fun initView() {
        add_post_button.setOnClickListener {
            add_post_progressbar.visibility = View.VISIBLE

            val title = add_post_title_edittext.text.toString()
            val content = add_post_content_edittext.text.toString()
            val author = add_post_author_edittext.text.toString()

            if (title.isEmpty() || content.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val userPost = UserPost()
                userPost._id = UUID.randomUUID().toString()
                userPost.title = title
                userPost.content = content
                userPost.author = author

                mAddPostViewModel.addPost(userPost).observe(this, Observer {
                    if (it) {
                        add_post_progressbar.visibility = View.GONE
                        Toast.makeText(this, "Post added!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

}