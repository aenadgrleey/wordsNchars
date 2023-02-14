package com.wordsnchars.ui_xml.selectors.size_selector

import alirezat775.lib.carouselview.CarouselAdapter
import alirezat775.lib.carouselview.CarouselModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.wordsnchars.databinding.TextEditorSizeSelectEmptyItemBinding
import com.wordsnchars.databinding.TextEditorSizeSelectItemBinding
import com.wordsnchars.ui_xml.selectors.size_selector.SizeSelectFragment.*


class SizeSelectAdapter : CarouselAdapter() {
    private val emptyItemFlag = 0
    private val normalItemFlag = 1

    var onClick: OnClick? = null

    override fun getItemViewType(position: Int): Int {
        return when (getItems()[position]) {
            is EmptyModel -> emptyItemFlag
            else -> normalItemFlag
        }
    }

    fun setOnClickListener(onClick: OnClick?) {
        this.onClick = onClick
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        when (holder) {
            is MyCarouselHolder -> {
                (holder as MyCarouselHolder).content.text =
                    (getItems()[position] as MultiplierModel).multiplier.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            normalItemFlag -> {
                MyCarouselHolder(TextEditorSizeSelectItemBinding.inflate(inflater))
            }
            else -> {
                EmptyMyCarouselHolder(TextEditorSizeSelectEmptyItemBinding.inflate(inflater))
            }
        }
    }


    inner class MyCarouselHolder(itemBinding: TextEditorSizeSelectItemBinding) :
        CarouselViewHolder(itemBinding.root) {
        val content: TextView = itemBinding.content

        init {
            itemBinding.item.setOnClickListener { onClick?.click(getItems()[adapterPosition] as MultiplierModel) }
        }
    }

    inner class EmptyMyCarouselHolder(itemBinding: TextEditorSizeSelectEmptyItemBinding) :
        CarouselViewHolder(itemBinding.root) {
        init {
            itemBinding.emptyItem.setOnClickListener { onClick?.click(getItems()[adapterPosition] as EmptyModel) }
        }
    }

    interface OnClick {
        fun click(model: CarouselModel)
    }
}