package com.example.mybasicapplication.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.entityModel.GetBLUOfflineDataMock
import com.example.domain.useCase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class BluViewModel @Inject constructor(
    val getUserRepositoriesUseCase: GetUserRepositoriesUseCase
) : ViewModel() {

    var messageError = MutableLiveData<String>()
    lateinit var mockDataItemsFlow: Flow<List<GetBLUOfflineDataMock>>


    fun callGetMockDataItemsRequest() {
        try {
            val executorDispatcher: CoroutineDispatcher = Dispatchers.IO
            val defaultExceptionHandler = CoroutineExceptionHandler { _, exception ->
                messageError.value = exception.message
            }

            var context = defaultExceptionHandler + executorDispatcher
            viewModelScope.launch(context) {
                val items = getUserRepositoriesUseCase.onExecute(null)
                mockDataItemsFlow = flowOf(items) as Flow<List<GetBLUOfflineDataMock>>
            }
        } catch (e: java.lang.IllegalStateException) {
            println("IllegalStateException ${e.message}")
        } catch (e: IOException) {
            println("IOException${e.message}")
        } catch (e: Exception) {
            println("Exception ${e.message}")
        }
    }

}