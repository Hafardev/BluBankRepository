package com.example.data.entityModel

import com.example.domain.entityModel.GetOfflineDataMockModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetBLUOfflineDataMock(
    @SerializedName("serviceName") @Expose var serviceName: String? = null,
    @SerializedName("amount") @Expose var amount: String? = null,
    @SerializedName("persianDateTime") @Expose var persianDateTime: String? = null,
    @SerializedName("iconName") @Expose var iconName: String? = null,
    @SerializedName("isHighlight") @Expose var isHighlight: Boolean = false
) : GetOfflineDataMockModel