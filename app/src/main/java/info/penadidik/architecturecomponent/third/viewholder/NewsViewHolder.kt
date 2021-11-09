package info.penadidik.architecturecomponent.third.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import info.penadidik.architecturecomponent.R
import info.penadidik.architecturecomponent.third.data.News

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(news: News?) {
        if (news != null) {
            itemView.findViewById<TextView>(R.id.txt_news_name).text = news.title
            if (!news.image.isNullOrEmpty()){
                Picasso.get().load(news.image).into(itemView.findViewById<ImageView>(R.id.img_news_banner))
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_third, parent, false)
            return NewsViewHolder(view)
        }
    }
}