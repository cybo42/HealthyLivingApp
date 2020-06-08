package com.cybo42.healthyliving.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.cybo42.healthyliving.BR
import com.cybo42.healthyliving.R
import com.cybo42.healthyliving.datastore.db.SavedArticle
import com.cybo42.healthyliving.ui.OnSavedArticleSelectedListener

class SavedArticleAdapter(
    private val articleSelectedListener: OnSavedArticleSelectedListener,
    private val viewModel: SavedArticlesViewModel
): RecyclerView.Adapter<SavedArticleAdapter.ArticleViewHolder>() {
    private val articles = mutableListOf<SavedArticle>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ArticleViewHolder(articleSelectedListener, viewModel, binding)
    }

    override fun getItemCount(): Int  = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.saved_article_item
    }

    fun updateArticleList(new: List<SavedArticle>) {
        articles.clear()
        articles.addAll(new)
    }



    class ArticleViewHolder(private val listener: OnSavedArticleSelectedListener,
                            private val viewModel: SavedArticlesViewModel,
                            private val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(article: SavedArticle) {
            binding.setVariable(BR.article, article)
            binding.setVariable(BR.viewModel, viewModel)
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                listener.onArticleSelected(article)
            }
        }
    }
}
