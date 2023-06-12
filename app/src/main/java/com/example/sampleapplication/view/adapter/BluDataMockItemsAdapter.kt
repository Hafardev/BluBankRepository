package com.example.sampleapplication.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entityModel.GetBLUOfflineDataMock
import com.example.sampleapplication.R
import com.example.sampleapplication.databinding.ItemRowBluAdapterBinding


class BluDataMockItemsAdapter(
    private val mockDataList: ArrayList<GetBLUOfflineDataMock>,
    private val context: Context
) :
    RecyclerView.Adapter<BluDataMockItemsAdapter.BluCustomViewHolder>() {

    lateinit var mContext: Context
    init {
        mContext = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluCustomViewHolder {
        val inflatedView = ItemRowBluAdapterBinding.inflate(LayoutInflater.from(mContext), parent, false)

        return BluCustomViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = mockDataList.size

    override fun onBindViewHolder(holder: BluCustomViewHolder, position: Int) {
        val itemData = mockDataList[position]


        holder.bluItemBinding.item = itemData

        holder.bindItems(itemData, mContext)
    }


    class BluCustomViewHolder(var bluItemBinding: ItemRowBluAdapterBinding) :
        RecyclerView.ViewHolder(bluItemBinding.root){


        @SuppressLint("ResourceAsColor")
        fun bindItems(itemModel: GetBLUOfflineDataMock, context: Context) {
            itemModel.iconName?.let {
                val id: Int = context.getResources().getIdentifier(itemModel.iconName, "drawable", context.getPackageName());
                try {
                    bluItemBinding.ivService.setImageDrawable(ContextCompat.getDrawable(context, id))

                } catch (e: Exception) {

                }
            }

            if (itemModel.isHighlight){
                bluItemBinding.tvAmount.setBackgroundColor(ContextCompat.getColor(context, R.color.blu_color_highlight_tint_text_transaction))
                bluItemBinding.ivService.setColorFilter(ContextCompat.getColor(context, R.color.blu_color_highlight_tint_transaction), android.graphics.PorterDuff.Mode.MULTIPLY)
            }

        }

    }
}