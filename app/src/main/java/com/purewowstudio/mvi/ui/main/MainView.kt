package com.purewowstudio.mvi.ui.main

interface MainView {
    data class State(val count: Int)

    sealed class Event {
        object IncrementPressed: Event()
        object DecrementPressed: Event()
    }

    sealed class Effect {
        data class ShowUserMessage(val message: String) : Effect()
    }
}
