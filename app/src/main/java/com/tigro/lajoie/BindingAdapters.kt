package com.tigro.lajoie

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tigro.lajoie.adapters.CommentAdapter
import com.tigro.lajoie.adapters.HistoryAdapter
import com.tigro.lajoie.adapters.KnowledgeAdapter
import com.tigro.lajoie.adapters.WallAdapter
import com.tigro.lajoie.models.Comment
import com.tigro.lajoie.models.History
import com.tigro.lajoie.models.Knowledge
import com.tigro.lajoie.models.Wall
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("listWall")
fun bindWallRecyclerView(
    recyclerView: RecyclerView,
    data: List<Wall>?
) {
    val adapter = recyclerView.adapter as WallAdapter
    adapter.submitList(data)
}

@BindingAdapter("listComment")
fun bindCommentRecyclerView(
    recyclerView: RecyclerView,
    data: List<Comment>?
) {
    val adapter = recyclerView.adapter as CommentAdapter
    adapter.submitList(data)
}

@BindingAdapter("listHistory")
fun bindHistoryRecyclerView(
    recyclerView: RecyclerView,
    data: List<History>?
) {
    val adapter = recyclerView.adapter as HistoryAdapter
    adapter.submitList(data)
}

@BindingAdapter("listKnowledge")
fun bindKnowledgeRecyclerView(
    recyclerView: RecyclerView,
    data: List<Knowledge>?
) {
    val adapter = recyclerView.adapter as KnowledgeAdapter
    adapter.submitList(data)
}

@BindingAdapter("profileUrl")
fun getImageProfile(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val ucareCDN = "https://ucarecdn.com/${url}/"
        url.let {
            val imageUri = ucareCDN.toUri().buildUpon().scheme("https").build()
            Log.d("TEST", imageUri.toString())
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_animation)
            }
        }
    } else {
        imageView.load(R.drawable.userplaceholder)
    }
}

@BindingAdapter("imageUrl")
fun getImage(imageView: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        val ucareCDN = "https://ucarecdn.com/${url}/"
        url.let {
            val imageUri = ucareCDN.toUri().buildUpon().scheme("https").build()
            Log.d("TEST", imageUri.toString())
            imageView.load(imageUri) {
                placeholder(R.drawable.loading_animation)
            }
        }
    } else {
        imageView.load(R.drawable.ic_image)
    }
}

@BindingAdapter("prettyDate")
fun formatDate(textView: TextView, date: String) {
    val prettyTime = PrettyTime()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
    val convertedDate = dateFormat.parse(date)
    textView.text = prettyTime.format(convertedDate)

}

@BindingAdapter("isShownIfExpert")
fun isExpert(view: View, type: Int) {
    if (type == 2) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
