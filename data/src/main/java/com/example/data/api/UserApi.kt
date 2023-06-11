package com.example.data.api

import com.example.data.entityModel.GetBLUOfflineDataMock
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserApi {

  @Headers("mock:true")
  @GET("/mocks/datamock")
  suspend fun getDataItems(): List<GetBLUOfflineDataMock>
}