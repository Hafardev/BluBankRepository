package com.example.domain.repository

import com.example.domain.entityModel.GetOfflineDataMockModel

interface UserRepository {

    suspend fun getDataItems():  List<GetOfflineDataMockModel>
}