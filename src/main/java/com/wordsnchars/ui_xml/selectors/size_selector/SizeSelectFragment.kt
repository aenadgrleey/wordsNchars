package com.wordsnchars.ui_xml.selectors.size_selector

import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselListener
import alirezat775.lib.carouselview.CarouselModel
import alirezat775.lib.carouselview.CarouselView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.wordsnchars.databinding.TextEditorSizeSelectBinding
import com.wordsnchars.text_editor.ViewModelTextEditor

class SizeSelectFragment : Fragment() {

    private val viewModel: ViewModelTextEditor by activityViewModels()

    private lateinit var binding: TextEditorSizeSelectBinding

    private lateinit var carousel: Carousel
    private var adapter = SizeSelectAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = TextEditorSizeSelectBinding.inflate(inflater)

        setUpCarousel(binding.carousel)

        return binding.root
    }



    private fun setUpCarousel(carouselView: CarouselView){
        carousel = Carousel(activity as AppCompatActivity, carouselView, adapter)

        //create values for carousel
        val modifiers = createModifierList()
        carousel.addAll(modifiers)

        //custom carousel-lib was a bad idea, due to some issues it's hard to choose a multiplier (selection shift while being chosen)
        //set up carousel
        carousel.setOrientation(CarouselView.HORIZONTAL, false)
        carousel.scaleView(true)
        carousel.scrollSpeed(75F)
        adapter.setOnClickListener(object : SizeSelectAdapter.OnClick {
            override fun click(model: CarouselModel) {
                findNavController().navigateUp()
            }

        })
        carousel.addCarouselListener(object : CarouselListener {
            override fun onPositionChange(position: Int) {
                //crutches to fix lib's issues with first and last elements
                if (carousel.getCurrentPosition() == 0){
                    //set pos according to VM on init or to the first one
                    carousel.setCurrentPosition((viewModel.fontSizeMultiplier.value / 0.25F).toInt() - 1)
                    return
                }
                if (carousel.getCurrentPosition() == modifiers.size - 1){
                    carousel.setCurrentPosition(modifiers.size - 2)
                    return
                }

                viewModel.setModifier((modifiers[carousel.getCurrentPosition()] as MultiplierModel).multiplier)
            }
            override fun onScroll(dx: Int, dy: Int) {}
        })
    }

    private fun createModifierList(): MutableList<CarouselModel> {
        return mutableListOf<CarouselModel>().also {
            //create start "spacer" for proper work of CarouselView
            it.add(EmptyModel())
            //fill list with values of font size modifiers
            for (i in 0..8) it.add(MultiplierModel(i * 0.25F + 0.5F))
            //create end "spacer" for proper work of CarouselView
            it.add(EmptyModel())
        }
    }



    //data models for carousel view
    inner class MultiplierModel(val multiplier: Float) : CarouselModel()
    inner class EmptyModel : CarouselModel()


}