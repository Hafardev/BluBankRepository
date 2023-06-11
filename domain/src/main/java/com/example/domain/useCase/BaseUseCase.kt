package com.example.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseUseCase<P,R>  {

    var executorDispatcher = Dispatchers.Default

    abstract suspend fun onExecute(param :P ?) :R ?

   open suspend fun executeHandler (param: P ? ) : R ?{
        return withContext(executorDispatcher){
            onExecute(param)
        }
    }

}