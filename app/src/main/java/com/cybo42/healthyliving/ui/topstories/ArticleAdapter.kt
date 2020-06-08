package com.cybo42.healthyliving.ui.topstories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cybo42.healthyliving.BR
import com.cybo42.healthyliving.R
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.ui.OnArticleSelectedListener

class ArticleAdapter(private val articleSelectedListener: OnArticleSelectedListener): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val articles = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ArticleViewHolder(articleSelectedListener, binding)
    }

    override fun getItemCount(): Int  = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.article_item
    }

    fun updateArticleList(new: List<Article>) {
        articles.clear()
        articles.addAll(new)
    }



    class ArticleViewHolder(private val listener: OnArticleSelectedListener,
                            private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: Article) {
            binding.setVariable(BR.article, article)
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                listener.onArticleSelected(article)
            }
        }
    }
}
