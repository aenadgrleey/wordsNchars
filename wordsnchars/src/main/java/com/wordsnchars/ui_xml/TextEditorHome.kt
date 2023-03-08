package com.wordsnchars.ui_xml

import android.graphics.Typeface.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.databinding.TextEditorHomeBinding
import com.wordsnchars.ui_compose.Toolbar

internal class TextEditorHome : Fragment() {
    private lateinit var binding: TextEditorHomeBinding
    private val viewModel: ViewModelTextEditor by activityViewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = TextEditorHomeBinding.inflate(inflater)

        binding.composeView.setContent {
            Toolbar(viewModelTextEditor = viewModel)
        }

        return binding.root
    }
}