package com.example.currencyexchanger.presentation.base

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel<State> : ViewModel() {
    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state


    @SuppressLint("NullSafeMutableLiveData")
    protected fun postState(state: State) {
        _state.value = state
    }

    fun launchAll(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(context, start = start) {
            try {
                block()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }

    protected fun <P, R, U : BaseSuspendUseCase<P, R>> U.launch(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseSuspendUseCase.Request<R>().apply(block)

            val proxy: CompletionBlock<R> = {
                onStart = {
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onCancel = {
                    actualRequest.onCancel?.invoke(it)
                }
                onTerminate = {
                    actualRequest.onTerminate
                }
            }
            execute(param, proxy)
        }
    }

}
