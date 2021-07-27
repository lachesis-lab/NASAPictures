package ru.lachesis.nasapictures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.api.load
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.app.AppState
import ru.lachesis.nasapictures.databinding.PictureFragmentBinding
import ru.lachesis.nasapictures.viewmodel.MainViewModel
import java.util.*

class PictureFragment : Fragment() {
    private var isExpanded = false
    private var dateOffset: Int = 0
    private var _binding: PictureFragmentBinding? = null
    private val binding: PictureFragmentBinding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    companion object {
        const val BUNDLE_EXTRA = "dateOffset"
        fun newInstance(offset: Int): PictureFragment {
            val args = Bundle()
            args.putInt(BUNDLE_EXTRA, offset - 2)
            val fragment = PictureFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = PictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val picture = binding.picture
        picture.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(binding.pictureCont,
                TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform()))
            val params: ViewGroup.LayoutParams = picture.layoutParams

            if (isExpanded) {
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                picture.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                picture.scaleType = ImageView.ScaleType.FIT_CENTER
            }
        }


        dateOffset = arguments?.getInt(BUNDLE_EXTRA, 0)!!
        val dateInstance = Calendar.getInstance()
        dateInstance.add(Calendar.DAY_OF_MONTH, dateOffset)
        viewModel.getData(dateInstance).observe(viewLifecycleOwner, { renderData(it) })
    }


    private fun renderData(appState: AppState?) {
        when (appState) {

            is AppState.Success -> {
                val responseData = appState.serverResponseData
                val url = responseData.url
                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
                if (url.isNullOrEmpty())
                    toast("Link is empty")
                else {
                    binding.picture.load(url) {
                        lifecycle(this@PictureFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)

                    }
//                    binding.includeBottomSheetLayout.bottomSheetHeader.text = responseData.title
//                    binding.includeBottomSheetLayout.bottomSheetText.text = responseData.explanation

                }

            }
            is AppState.Loading -> {
                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                toast(appState.error.message!!)

            }

        }
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}