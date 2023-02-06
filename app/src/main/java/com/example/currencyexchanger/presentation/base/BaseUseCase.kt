package com.example.currencyexchanger.presentation.base

abstract class BaseUseCase<P, R> {

    operator fun invoke(params: P): R {
        return execute(params)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameter: P): R

}
