package com.example.wordsnchars.text_editor.selectors

import android.graphics.Typeface.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wordsnchars.databinding.TextEditorStyleSelectBinding
import com.example.xconspectus.ui.text_editor.ViewModelTextEditor

class StyleSelectorFragment : Fragment() {
    private lateinit var binding: TextEditorStyleSelectBinding
    private val viewModel: ViewModelTextEditor by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = TextEditorStyleSelectBinding.inflate(inflater)

        binding.normal.setOnClickListener{
            viewModel.styleChange(NORMAL)
            findNavController().navigateUp()
        }

        binding.italic.setOnClickListener{
            viewModel.styleChange(ITALIC)
            findNavController().navigateUp()
        }

        binding.bold.setOnClickListener{
            viewModel.styleChange(BOLD)
            findNavController().navigateUp()
        }

        binding.boldItalic.setOnClickListener{
            viewModel.styleChange(BOLD_ITALIC)
            findNavController().navigateUp()
        }
        return binding.root
    }

}