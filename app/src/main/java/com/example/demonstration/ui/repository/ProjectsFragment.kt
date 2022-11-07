package com.example.demonstration.ui.repository

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.demonstration.databinding.FragmentProjectBinding


class ProjectsFragment : Fragment() {

    private var _binding : FragmentProjectBinding ?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProjectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username: String? = arguments?.getString("arg")
        if (username != null) {
            if(username.isNotEmpty()) {
                val webViewClient: WebViewClient = object: WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        view?.loadUrl(request?.url.toString())
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        showProgressDialog()
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        hideProgressDialog()
                        super.onPageFinished(view, url)
                    }
                }
                binding.webView.apply {
                    this.webViewClient = webViewClient
                    this.settings.defaultTextEncodingName = "utf-8"
                    this.loadUrl("https://github.com/${username}?tab=repositories")
                }
            }
        }
    }

    private fun hideProgressDialog() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressDialog() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}