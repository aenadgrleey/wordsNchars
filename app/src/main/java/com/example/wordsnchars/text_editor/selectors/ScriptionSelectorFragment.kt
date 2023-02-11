package com.example.xconspectus.ui.text_editor.selectors

import android.os.Bundle
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.xconspectus.R
import com.example.xconspectus.databinding.TextEditorScriptionSelectBinding
import com.example.xconspectus.ui.text_editor.ViewModelTextEditor

class ScriptionSelectorFragment : Fragment() {
    private lateinit var binding: TextEditorScriptionSelectBinding
    private val viewModel: ViewModelTextEditor by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = TextEditorScriptionSelectBinding.inflate(inflater)

        binding.normal.setOnClickListener {
            viewModel.scriptionChange(null)
            findNavController().navigateUp()
        }
        binding.subscription.setOnClickListener {
            viewModel.scriptionChange(SubscriptSpan())
            findNavController().navigateUp()
        }
        binding.superscription.setOnClickListener {
            viewModel.scriptionChange(SuperscriptSpan())
            findNavController().navigateUp()
        }
        return binding.root
    }
}