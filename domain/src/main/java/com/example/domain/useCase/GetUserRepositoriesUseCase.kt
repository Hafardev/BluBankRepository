package com.example.domain.useCase

import com.example.domain.entityModel.GetOfflineDataMockModel
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetUserRepositoriesUseCase @Inject constructor(val repository: UserRepository):
    BaseUseCase<Unit, List<GetOfflineDataMockModel>>() {

    override suspend fun onExecute(param: Unit?): List<GetOfflineDataMockModel> {
      return repository.getDataItems()
    }

}