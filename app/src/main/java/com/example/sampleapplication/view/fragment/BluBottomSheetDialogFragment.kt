package com.example.sampleapplication.view.fragment

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entityModel.GetBLUOfflineDataMock
import com.example.mybasicapplication.viewModel.BluViewModel
import com.example.sampleapplication.R
import com.example.sampleapplication.databinding.BluBottomSheetDialogFragmentBinding
import com.example.sampleapplication.view.adapter.BluDataMockItemsAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

/**
Created by Hadis Farmani
 */
class BluBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var binding: BluBottomSheetDialogFragmentBinding? = null
    private var buttonLayoutParams: ConstraintLayout.LayoutParams? = null
    private lateinit var mAdapter: BluDataMockItemsAdapter
    private val userViewModel: BluViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BluBottomSheetDialogFragmentBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            userViewModel.mockDataItemsFlow.collect {
                mAdapter = BluDataMockItemsAdapter(it as ArrayList<GetBLUOfflineDataMock>, requireContext())

                binding!!.sheetRecyclerview.apply {
                    layoutManager = LinearLayoutManager(context)
                    binding!!.sheetRecyclerview.setHasFixedSize(true)
                    binding!!.sheetRecyclerview.adapter = mAdapter
                }
            }
        }

        binding!!.sheetRecyclerview.setLayoutManager(
            LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
        )

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface: DialogInterface -> setupRatio(dialogInterface as BottomSheetDialog) }


        (dialog as BottomSheetDialog).behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Toast.makeText(context, "STATE_COLLAPSED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_EXPANDED -> Toast.makeText(context, "STATE_EXPANDED", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_DRAGGING -> Toast.makeText(context, "STATE_DRAGGING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_SETTLING -> Toast.makeText(context, "STATE_SETTLING", Toast.LENGTH_SHORT).show()
                    BottomSheetBehavior.STATE_HIDDEN -> Toast.makeText(context, "STATE_HIDDEN", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(context, "OTHER_STATE", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return dialog
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) ?: return
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        //Retrieve button parameters
        buttonLayoutParams = binding!!.sheetButton.getLayoutParams() as ConstraintLayout.LayoutParams?

        //Retrieve bottom sheet parameters
        BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
        val bottomSheetLayoutParams = bottomSheet.layoutParams
        bottomSheetLayoutParams.height = bottomSheetDialogDefaultHeight
        expandedHeight = bottomSheetLayoutParams.height
        val peekHeight = (expandedHeight / 1.3).toInt() //Peek height to 70% of expanded height (Change based on your view)

        //Setup bottom sheet
        bottomSheet.layoutParams = bottomSheetLayoutParams
        BottomSheetBehavior.from(bottomSheet).skipCollapsed = false
        BottomSheetBehavior.from(bottomSheet).peekHeight = peekHeight
        BottomSheetBehavior.from(bottomSheet).isHideable = true

        //Calculate button margin from top
        buttonHeight =
            binding!!.sheetButton.getHeight() + 40 //How tall is the button + experimental distance from bottom (Change based on your view)
        collapsedMargin = peekHeight - buttonHeight //Button margin in bottom sheet collapsed state
        buttonLayoutParams!!.topMargin = collapsedMargin
        binding!!.sheetButton.setLayoutParams(buttonLayoutParams)

        //OPTIONAL - Setting up margins
        val recyclerLayoutParams =
            binding!!.sheetRecyclerview.getLayoutParams() as ConstraintLayout.LayoutParams
        val k =
            (buttonHeight - 60) / buttonHeight.toFloat() //60 is amount that you want to be hidden behind button
        recyclerLayoutParams.bottomMargin =
            (k * buttonHeight).toInt() //Recyclerview bottom margin (from button)
        binding!!.sheetRecyclerview.setLayoutParams(recyclerLayoutParams)
    }

    //Calculates height for 90% of fullscreen
    private val bottomSheetDialogDefaultHeight: Int
        private get() = windowHeight * 90 / 100

    //Calculates window height for fullscreen use
    private val windowHeight: Int
        private get() {
            val displayMetrics = DisplayMetrics()
            (requireContext() as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

    private fun initString(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..34) list.add("Item $i")
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private var instance: BluBottomSheetDialogFragment? = null
        private var collapsedMargin = 0
        private var buttonHeight = 0
        private var expandedHeight = 0
        fun newInstance(): BluBottomSheetDialogFragment? {
            if (instance == null) instance = BluBottomSheetDialogFragment()
            return instance
        }
    }
}