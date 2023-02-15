package com.wordsnchars.ui_xml

import android.graphics.Typeface.*
import android.os.Bundle
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.wordsnchars.R
import com.wordsnchars.databinding.TextEditorHomeBinding
import com.wordsnchars.ViewModelTextEditor

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

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
        lifecycleScope.launchWhenStarted {
            viewModel.fontSizeMultiplier.onEach { modifier ->
                binding.size.text = modifier.toString()
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.highlightColor.onEach { color ->
                binding.highlight.setBackgroundColor(color)
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.underlined.onEach { toggle ->
                when (toggle) {
                    true -> binding.underlined.background =
                        resources.getDrawable(R.drawable.text_editor_button_pressed)
                    false -> binding.underlined.background =
                        resources.getDrawable(R.drawable.text_editor_button_unpressed)
                }
            }.collect()
        }
        lifecycleScope.launchWhenStarted {
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

        lifecycleScope.launchWhenStarted {
            viewModel.scription.onEach { scription ->
                if (scription == null) {
                    binding.scription.text = "N"
                } else {
                    when (scription::class.java) {
                        SuperscriptSpan::class.java -> {
                            binding.scription.text = "UP"
                        }
                        SubscriptSpan::class.java -> {
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