package ru.lachesis.nasapictures.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import ru.lachesis.nasapictures.R
import ru.lachesis.nasapictures.app.AppState
import ru.lachesis.nasapictures.databinding.MainFragmentBinding
import ru.lachesis.nasapictures.viewmodel.MainViewModel
import java.util.*

class MainFragment : Fragment() {
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!
    private val viewModel: MainViewModel by lazy {
//        ViewModelProviders.of(this).get(MainViewModel::class.java)
        ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    companion object {
        const val BUNDLE_EXTRA = "Theme"
        fun newInstance(bundle: Bundle?): MainFragment {
            val fragment = MainFragment()
            fragment.arguments = bundle
            return fragment
        }
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
        setHasOptionsMenu(true)
        setBottomMenu()
        viewModel.getLiveData().observe(this@MainFragment.viewLifecycleOwner,  { renderData(it)})
//        viewModel.getData(Calendar.getInstance())
//            .observe(this@MainFragment.viewLifecycleOwner, Observer<AppState> { renderData(it) })

        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.includeBottomSheetLayout.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        val viewPagerAdapter =MainViewPagerAdapter(childFragmentManager)
        val viewPager  = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.offscreenPageLimit=0
        viewPager.adapter  = viewPagerAdapter
//        viewPagerAdapter.obs
        binding.tabLayout.setupWithViewPager(viewPager)
/*
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("SelectedPosition",tabLayout.selectedTabPosition.toString())
//                viewPagerAdapter.getItem(viewPagerAdapter.items[position].id)
                viewPagerAdapter.getRegisteredItem(tabLayout.selectedTabPosition)// currentItem=position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
*/
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {
 //               viewPager.currentItem=position// .setCurrentItem(position,true)
            }

            override fun onPageSelected(position: Int) {
                Log.d("SelectedPosition",position.toString())
                viewPagerAdapter.instantiateItem(viewPager,position)
//                viewPagerAdapter.getItem(viewPagerAdapter.items[position].id)
//                viewPagerAdapter.getRegisteredItem(viewPager,viewPager.currentItem)// currentItem=position
//                viewPagerAdapter.notifyDataSetChanged()
            // .setCurrentItem(position,true)
//                viewPagerAdapter.getItem(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.bottom_menu,menu)
    }
    private fun setBottomMenu() {
        val bottomAppBar = requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar)
//        bottomAppBar.inflateMenu(R.menu.bottom_menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_settings){
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_container,SettingsFragment.newInstance(),"")
                ?.addToBackStack(null)
                ?.commit()
        }
        return super.onOptionsItemSelected(item)
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
//                    binding.picture.load(url) {
//                        lifecycle(this@MainFragment)
//                        error(R.drawable.ic_load_error_vector)
//                        placeholder(R.drawable.ic_no_photo_vector)
//
//                    }
                    binding.includeBottomSheetLayout.bottomSheetHeader.text = responseData.title
                    binding.includeBottomSheetLayout.bottomSheetText.text = responseData.explanation

                }

            }
//            is AppState.Loading -> {
//                binding.includeLoadingLayout.loadingLayout.visibility = View.VISIBLE
//            }
//            is AppState.Error -> {
//                toast(appState.error.message!!)
//
//            }

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