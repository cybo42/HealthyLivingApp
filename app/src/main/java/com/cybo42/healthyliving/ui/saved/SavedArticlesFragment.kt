package com.cybo42.healthyliving.ui.saved

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cybo42.healthyliving.R
import com.cybo42.healthyliving.datastore.db.SavedArticle
import com.cybo42.healthyliving.ui.OnSavedArticleSelectedListener
import com.cybo42.healthyliving.ui.model.GenericArticle
import com.cybo42.healthyliving.ui.model.PendingData
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedArticlesFragment : Fragment(), OnSavedArticleSelectedListener {
    private val viewModel: SavedArticlesViewModel by viewModel()

    private lateinit var savedArticleAdapter: SavedArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.saved_articles_fragment, container, false)

        savedArticleAdapter = SavedArticleAdapter(this, viewModel)
        view.findViewById<RecyclerView>(R.id.savedArticleList)?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            adapter = savedArticleAdapter
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.fetchSavedArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.savedArticles.observe(viewLifecycleOwner, Observer { pendingData ->
            when (pendingData) {
                is PendingData.Success<List<SavedArticle>> -> {
                    savedArticleAdapter.updateArticleList(pendingData.data)
                    savedArticleAdapter.notifyDataSetChanged()
                }

                is PendingData.Error -> {
                    Snackbar.make(
                        requireView(), pendingData.throwable.message.toString(),
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction(getString(R.string.retry)) {
                        viewModel.fetchSavedArticles()
                    }.show()
                }
            }
        })
    }

    override fun onArticleSelected(article: SavedArticle) {
        findNavController().navigate(SavedArticlesFragmentDirections.actionSavedArticlesToArticle(
            GenericArticle(article.uri, article.url, article.title,
                article.summary, article.thumbnailUrl)
        ))
    }
}
