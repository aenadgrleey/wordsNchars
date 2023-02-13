package com.wordsnchars.text_editor.selectors

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.wordsnchars.R
import com.wordsnchars.databinding.TextEditorHighlightColorSelectorBinding
import com.wordsnchars.text_editor.ViewModelTextEditor


class HighlightColorSelectorFragment : Fragment() {
    private lateinit var binding: TextEditorHighlightColorSelectorBinding
    private val viewModel: ViewModelTextEditor by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        val colors = arrayListOf<Int?>(resources.getColor(R.color.transparent), resources.getColor(R.color.primary), resources.getColor(R.color.purple_200), resources.getColor(R.color.blue), resources.getColor(R.color.pink), resources.getColor(R.color.thirdOnPalette))
        binding = TextEditorHighlightColorSelectorBinding.inflate(inflater)
        for (i in 0 until binding.holder.childCount){
            binding.holder[i].setOnClickListener{
                with(viewModel.highlightColor.value){
                    Log.v("hh", viewModel.highlightColorChange(colors[i]!!).toString())
                    findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }

}