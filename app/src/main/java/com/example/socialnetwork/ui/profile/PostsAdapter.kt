package com.example.socialnetwork.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.socialnetwork.R

class PostsAdapter(private val context: Context, private val arrayList: List<PostItem>) :
    BaseAdapter() {
    private var revertList: List<PostItem> = arrayList.reversed()
    private lateinit var postText: TextView
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return if (convertView == null) {
            val converterView =
                LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false)
            postText = converterView.findViewById(R.id.postText)
            postText.text = revertList[position].postText
            converterView
        } else {
            convertView
        }
    }
}