package ru.lachesis.nasapictures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.app.AppState
import ru.lachesis.nasapictures.databinding.MainFragmentBinding
import ru.lachesis.nasapictures.viewmodel.MainViewModel

class MainFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.main_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData()
            .observe(this@MainFragment.viewLifecycleOwner, Observer<AppState> { renderData(it) })
        bottomSheetBehavior =
            BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet_container))
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(appState: AppState?) {
        when (appState) {

            is AppState.Success -> {
                val responseData = appState.serverResponseData
                val url = responseData.url
                if (url.isNullOrEmpty())
                    toast("Link is empty")
                else {
                    binding.picture.load(url) {
                        lifecycle(this@MainFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)

                    }
                    requireActivity().findViewById<TextView>(R.id.bottom_sheet_header).text =
                        responseData.title
                    requireActivity().findViewById<TextView>(R.id.bottom_sheet_text).text =
                        responseData.explanation

                }

            }
            is AppState.Error ->{
                toast(appState.error.message!!)

            }

        }
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text , Toast.LENGTH_LONG).show()
    }
}