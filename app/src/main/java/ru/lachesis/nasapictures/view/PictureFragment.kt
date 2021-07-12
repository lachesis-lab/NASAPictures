package ru.lachesis.nasapictures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.app.AppState
import ru.lachesis.nasapictures.databinding.PictureFragmentBinding
import ru.lachesis.nasapictures.viewmodel.MainViewModel
import java.util.*

class PictureFragment: Fragment() {
    private var dateOffset =0
    private var _binding: PictureFragmentBinding? = null
    private val binding: PictureFragmentBinding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    companion object{
        const val BUNDLE_EXTRA = "dateOffset"
        fun newInstance(bundle:Bundle?): PictureFragment {
            val args = Bundle()

            val fragment = PictureFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null){
            dateOffset = savedInstanceState.getInt(BUNDLE_EXTRA,0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dateInstance = Calendar.getInstance()
        dateInstance.add(Calendar.DAY_OF_MONTH,dateOffset)
        viewModel.getData(dateInstance)
        _binding = PictureFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val liveData = MainViewModel().getLiveData().observe(viewLifecycleOwner,{renderData(it)})

    }


    private fun renderData(appState: AppState?) {
        when (appState) {

            is AppState.Success -> {
                val responseData = appState.serverResponseData
                val url = responseData.url
//                binding.includeLoadingLayout.loadingLayout.visibility = View.GONE
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
//            is AppState.Loading -> {
//                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
//            }
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