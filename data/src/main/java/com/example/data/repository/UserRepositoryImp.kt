package com.example.data.repository

import com.example.data.api.UserApi
import com.example.domain.entityModel.GetOfflineDataMockModel
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(val userApi: UserApi) : UserRepository {

    override suspend fun getDataItems(): List<GetOfflineDataMockModel> {
        return userApi.getDataItems()
    }
}