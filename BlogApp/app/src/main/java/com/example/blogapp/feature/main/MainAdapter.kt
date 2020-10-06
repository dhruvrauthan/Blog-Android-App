package com.example.blogapp.feature.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blogapp.R
import com.example.blogapp.beans.UserPost
import kotlinx.android.synthetic.main.item_user_post.view.*

class MainAdapter(
    val mUserPostsArrayList: ArrayList<UserPost>,
    val mOnItemClickListener: OnItemClickListener,
    val mContext: Context
) :
    RecyclerView.Adapter<MainAdapter.MainAdapterViewHolder>() {

    class MainAdapterViewHolder(val holderView: View) : RecyclerView.ViewHolder(holderView) {
        fun bindItems(userPost: UserPost, clickListener: OnItemClickListener) {
            holderView.recyclerview_item_title_textview.text = userPost.title
            holderView.recyclerview_item_content_textview.text = userPost.content
            holderView.recyclerview_item_author_textview.text = "~" + userPost.author

            holderView.setOnClickListener {
                clickListener.onItemClick(userPost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapterViewHolder {
        val root = LayoutInflater.from(mContext).inflate(R.layout.item_user_post, parent, false)

        return MainAdapterViewHolder(root)
    }

    override fun onBindViewHolder(holder: MainAdapterViewHolder, position: Int) {
        val userPost = mUserPostsArrayList[position]
        holder.bindItems(userPost, mOnItemClickListener)
    }

    override fun getItemCount(): Int {
        return mUserPostsArrayList.size
    }

    interface OnItemClickListener {
        fun onItemClick(userPost: UserPost)
    }

}