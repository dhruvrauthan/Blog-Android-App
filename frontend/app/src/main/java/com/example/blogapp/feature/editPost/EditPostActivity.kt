package com.example.blogapp.feature.editPost

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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_edit_post.*

class EditPostActivity : AppCompatActivity() {

    private lateinit var mEditPostViewModel: EditPostViewModel
    private var mUserPost: UserPost = UserPost()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        mUserPost._id = intent.getStringExtra("_id")!!
        mUserPost.title = intent.getStringExtra("title")!!
        mUserPost.content = intent.getStringExtra("content")!!
        mUserPost.author = intent.getStringExtra("author")!!

        initView()

        mEditPostViewModel = ViewModelProvider(this).get(EditPostViewModel::class.java)
        mEditPostViewModel.initRetrofit()
    }

    private fun initView() {
        edit_post_title_edittext.setText(mUserPost.title)
        edit_post_content_edittext.setText(mUserPost.content)
        edit_post_author_edittext.setText(mUserPost.author)

        edit_post_button.setOnClickListener {
            edit_post_progressbar.visibility = View.VISIBLE

            val title = edit_post_title_edittext.text.toString()
            val content = edit_post_content_edittext.text.toString()
            val author = edit_post_author_edittext.text.toString()

            if (title.isEmpty() || content.isEmpty() || author.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val userPost = UserPost()
                userPost._id = mUserPost._id
                userPost.title = title
                userPost.content = content
                userPost.author = author

                mEditPostViewModel.editPost(userPost).observe(this, androidx.lifecycle.Observer {
                    if (it) {
                        edit_post_progressbar.visibility = View.GONE
                        Toast.makeText(this, "Post updated!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        delete_post_button.setOnClickListener {
            MaterialAlertDialogBuilder(this).setTitle("Delete Post")
                .setMessage("Are you sure you want to delete this post?")
                .setNegativeButton("Cancel") { dialog, which ->
                }.setPositiveButton("Yes"){dialog, which->
                    edit_post_progressbar.visibility=View.VISIBLE

                    mEditPostViewModel.deletePost(mUserPost._id).observe(this, Observer {
                        if(it){
                            edit_post_progressbar.visibility=View.GONE
                            Toast.makeText(this, "Post deleted!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                        }
                    })
                }.show()
        }
    }
}