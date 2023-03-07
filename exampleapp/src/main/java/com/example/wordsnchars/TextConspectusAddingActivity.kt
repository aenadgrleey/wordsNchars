package com.example.wordsnchars

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.exampleapp.R
import com.example.exampleapp.databinding.ActivityTextEditorBinding
import com.wordsnchars.TextEditor
import com.wordsnchars.ViewModelTextEditor

class TextConspectusAddingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTextEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTextEditorBinding.inflate(layoutInflater)
        setContentView(binding.root as View)
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