package com.example.wordsnchars

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aendgrleey.wordsnchars.databinding.ActivityTextEditorBinding
import com.wordsnchars.R.drawable

class TextConspectusAddingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(drawable.baseline_close_24)
        val viewModelTextEditor : com.wordsnchars.text_editor.ViewModelTextEditor by viewModels()
        com.wordsnchars.text_editor.TextEditor(viewModelTextEditor, binding.editText)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}