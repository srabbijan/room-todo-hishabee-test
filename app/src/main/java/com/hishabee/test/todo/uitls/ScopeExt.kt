package com.hishabee.test.todo.uitls

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Generic method to fetch data from repository
 */
fun CoroutineScope.callRepo(
    dispatcher: CoroutineDispatcher,
    liveData: MutableLiveData<DataResource<*>>,
    repoMethod: suspend () -> DataResource<*>?
) {
    liveData.postValue(DataResource.loading(null))
    launch(dispatcher) {
        liveData.postValue(repoMethod.invoke())
    }
}