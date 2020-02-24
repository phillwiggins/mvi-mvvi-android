package com.purewowstudio.mvi.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.purewowstudio.mvi.ui.main.MainView.Event.IncrementPressed
import com.purewowstudio.mvi.ui.main.MainView.Event.DecrementPressed
import com.purewowstudio.mvi.ui.main.MainView.Effect.ShowUserMessage

class MainViewModel : ViewModel() {

    private val effect: MutableLiveData<Effect<MainView.Effect>> = MutableLiveData()
    private val state: MutableLiveData<MainView.State> = MutableLiveData()

    val viewState: LiveData<MainView.State> = state
    val viewEffects: LiveData<Effect<MainView.Effect>> = effect

    init {
        updateViewState(MainView.State(0))
    }

    fun onEvent(event: MainView.Event) {
        when (event) {
            is IncrementPressed -> incrementCount()
            is DecrementPressed -> decrementCount()
        }
    }

    private fun dispatchEffect(effect: MainView.Effect) {
        this.effect.value = Effect(effect)
    }

    private fun updateViewState(state: MainView.State) {
        this.state.value = state
    }

    private fun incrementCount() {
        viewState.value?.let {
            val updatedCount = it.count + 1

            updateViewState(it.copy(count = updatedCount))

            if (updatedCount == 10)
                dispatchEffect(ShowUserMessage("You reached 10!"))
        }
    }

    private fun decrementCount() {
        viewState.value?.let {
            updateViewState(it.copy(count = it.count - 1))
        }
    }
}