package com.wordsnchars.ui_xml

import android.graphics.Typeface.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.wordsnchars.R
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.databinding.TextEditorHomeBinding
import com.wordsnchars.text_editor.core.custom_spans.subscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.superscriptionFlag
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class TextEditorHome : Fragment() {
    private lateinit var binding: TextEditorHomeBinding
    private val viewModel: ViewModelTextEditor by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = TextEditorHomeBinding.inflate(inflater)

        binding.size.setOnClickListener {
            findNavController().navigate(R.id.size_select)
        }
        binding.highlight.setOnClickListener {
            findNavController().navigate(R.id.highlight_color_select)
        }
        binding.style.setOnClickListener {
            findNavController().navigate(R.id.style_select)
        }
        binding.scription.setOnClickListener {
            findNavController().navigate(R.id.scription_select)
        }
        binding.underlined.setOnClickListener {
            viewModel.underlinedToggle()
        }
        viewModel.viewModelScope.launch {
            viewModel.fontSizeMultiplier.onEach { modifier ->
                binding.size.text = modifier.toString()
            }.collect()
        }
        viewModel.viewModelScope.launch {
            viewModel.highlightColor.onEach { color ->
                binding.highlight.setBackgroundColor(color)
            }.collect()
        }
        viewModel.viewModelScope.launch {
            viewModel.underlined.onEach { toggle ->
                when (toggle) {
                    true -> binding.underlined.background =
                        resources.getDrawable(R.drawable.text_editor_button_pressed)
                    false -> binding.underlined.background =
                        resources.getDrawable(R.drawable.text_editor_button_unpressed)
                }
            }.collect()
        }
        viewModel.viewModelScope.launch {
            viewModel.style.onEach { style ->
                when (style) {
                    NORMAL -> {
                        binding.style.text = "N"
                        binding.style.setTypeface(null, NORMAL)
                    }
                    BOLD -> {
                        binding.style.text = "B"
                        binding.style.setTypeface(null, BOLD)
                    }
                    ITALIC -> {
                        binding.style.text = "IT"
                        binding.style.setTypeface(null, ITALIC)
                    }
                    BOLD_ITALIC -> {
                        binding.style.text = "B&I"
                        binding.style.setTypeface(null, BOLD_ITALIC)
                    }
                }
            }.collect()
        }

        viewModel.viewModelScope.launch {
                viewModel.scription.onEach { scription ->
                if (scription == null) {
                    binding.scription.text = "N"
                } else {
                    when (scription) {
                        superscriptionFlag -> {
                            binding.scription.text = "UP"
                        }
                        subscriptionFlag -> {
                            binding.scription.text = "DW"
                        }
                    }
                }
            }.collect()
//            lifecycleScope.launchWhenStarted {
//                viewModel.underlined.onEach { toggle ->
//                    when(toggle){
//                        true -> binding.underlined.
//                    }
//                }
//            }
        }


        return binding.root
    }
}