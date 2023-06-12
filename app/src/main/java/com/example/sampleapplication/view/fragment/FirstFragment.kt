package com.example.sampleapplication.view.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entityModel.GetBLUOfflineDataMock
import com.example.mybasicapplication.viewModel.BluViewModel
import com.example.sampleapplication.R
import com.example.sampleapplication.databinding.FragmentFirstBinding
import com.example.sampleapplication.view.adapter.BluDataMockItemsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userViewModel: BluViewModel by activityViewModels()
    private lateinit var mAdapter: BluDataMockItemsAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>
    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.callGetMockDataItemsRequest()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            userViewModel.mockDataItemsFlow.collect {
                mAdapter = BluDataMockItemsAdapter(it as ArrayList<GetBLUOfflineDataMock>, requireContext())

                val recyclerView = view.findViewById<RecyclerView>(R.id.sheet_recyclerview)

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(context)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = mAdapter
                }
            }
        }

        val bottomSheet = view.findViewById<FrameLayout>(R.id.standard_bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)


        val bottomSheetLayoutParams = bottomSheet.layoutParams
        bottomSheetLayoutParams.height = (Resources.getSystem().getDisplayMetrics().heightPixels )
        val expandedHeight = bottomSheetLayoutParams.height
        val peekHeight = (expandedHeight /1.5).toInt() //Peek height to 70% of expanded height (Change based on your view)

        //Setup bottom sheet
        bottomSheet.layoutParams = bottomSheetLayoutParams
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = false
        BottomSheetBehavior.from(bottomSheet).peekHeight = peekHeight
        BottomSheetBehavior.from(bottomSheet).isHideable = false

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //TODO- handle onSlide
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //TODO- handle onStateChanged by when(newState)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}