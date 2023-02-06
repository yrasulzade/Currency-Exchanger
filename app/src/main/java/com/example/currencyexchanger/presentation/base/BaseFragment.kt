package com.example.currencyexchanger.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<State, ViewModel : BaseViewModel<State>, B : ViewBinding> : Fragment() {

    lateinit var binding: B
    protected lateinit var viewmodel: ViewModel

    protected abstract val bindingCallback: (LayoutInflater, ViewGroup?, Boolean) -> B

    protected open val bindViews: B.() -> Unit = {}

    protected abstract fun getViewModelClass(): Class<ViewModel>

    protected open fun getViewModelScope(): ViewModelStoreOwner = this

    private fun init() {
        val owner = getViewModelScope()

        viewmodel = ViewModelProvider(
            owner = owner
        )[getViewModelClass()]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = bindingCallback.invoke(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(binding)

        viewmodel.state.observe(viewLifecycleOwner) { observeState(it) }
    }


    protected open fun observeState(state: State) {
        // override when observing
    }
}
