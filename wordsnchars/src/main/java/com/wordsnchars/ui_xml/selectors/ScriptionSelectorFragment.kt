package com.wordsnchars.ui_xml.selectors

import android.os.Bundle
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.wordsnchars.databinding.TextEditorScriptionSelectBinding
import com.wordsnchars.ViewModelTextEditor
import com.wordsnchars.text_editor.core.custom_spans.noscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.subscriptionFlag
import com.wordsnchars.text_editor.core.custom_spans.superscriptionFlag

internal class ScriptionSelectorFragment : Fragment() {
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
            viewModel.scriptionChange(noscriptionFlag)
            findNavController().navigateUp()
        }
        binding.subscription.setOnClickListener {
            viewModel.scriptionChange(subscriptionFlag)
            findNavController().navigateUp()
        }
        binding.superscription.setOnClickListener {
            viewModel.scriptionChange(superscriptionFlag)
            findNavController().navigateUp()
        }
        return binding.root
    }
}