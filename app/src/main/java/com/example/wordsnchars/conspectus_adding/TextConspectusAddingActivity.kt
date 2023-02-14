package com.example.wordsnchars.conspectus_adding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.wordsnchars.R
import com.example.wordsnchars.databinding.ActivityTextEditorBinding
import com.example.wordsnchars.text_editor.TextEditor
import com.example.xconspectus.ui.text_editor.ViewModelTextEditor

class TextConspectusAddingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_close_24)
        val viewModelTextEditor : ViewModelTextEditor by viewModels()
       TextEditor(viewModelTextEditor, binding.editText)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}