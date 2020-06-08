package com.cybo42.healthyliving.ui.topstories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybo42.healthyliving.R
import com.cybo42.healthyliving.network.dto.Article
import com.cybo42.healthyliving.ui.OnArticleSelectedListener
import com.cybo42.healthyliving.ui.model.GenericArticle
import com.cybo42.healthyliving.ui.model.PendingData
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopHealthStoriesFragment : Fragment(),
    OnArticleSelectedListener {
    private val viewModel: TopHealthStoriesViewModel by viewModel()

    private lateinit var articleAdapter: ArticleAdapter

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.top_health_stories_fragment, container, false)
        return view
    }

    private fun toggleLoading(loading: Boolean){
        if (loading) {
            loadingIndicator.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            loadingIndicator.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleAdapter = ArticleAdapter(this)

        recyclerView = view.findViewById(R.id.articleList)
        loadingIndicator = requireView().findViewById(R.id.loadingIndicator)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = articleAdapter
        }

        viewModel.articles.observe(viewLifecycleOwner, Observer { pendingData ->
            when(pendingData) {
                is PendingData.Loading -> {
                    toggleLoading(true)
                }

                is PendingData.Success<List<Article>> -> {
                    toggleLoading(false)
                    articleAdapter.updateArticleList(pendingData.data)
                    articleAdapter.notifyDataSetChanged()
                }

                is PendingData.Error -> {
                    toggleLoading(false)
                    Snackbar.make(requireView(), pendingData.throwable.message.toString(),
                        Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry)) {
                            viewModel.fetchArticles()
                    }.show()
                }
            }
        })
    }

    override fun onArticleSelected(article: Article) {
        findNavController().navigate(TopHealthStoriesFragmentDirections.actionTopStoriesToArticle(
            GenericArticle(article.uri, article.url, article.title, article.abstract, article.thumbnail)
        ))
    }
}
