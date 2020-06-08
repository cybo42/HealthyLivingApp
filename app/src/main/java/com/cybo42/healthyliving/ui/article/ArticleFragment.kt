package com.cybo42.healthyliving.ui.article

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

import com.cybo42.healthyliving.R
import com.cybo42.healthyliving.ui.model.GenericArticle
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {
    private val args: ArticleFragmentArgs by navArgs()

    private val viewModel: ArticleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView = view.findViewById<WebView>(R.id.articleWebView)
        webView.webViewClient = WebViewClient()

        webView.loadUrl(args.article.url)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.article_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.share -> shareArticle(args.article)
            R.id.save -> {
                viewModel.saveArticle(args.article)
                // TODO: Should make toast only appear on success
                Toast.makeText(requireContext(), R.string.article_saved, Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareArticle(article: GenericArticle) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message, article.url))
            putExtra(Intent.EXTRA_SUBJECT, article.title)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

}
