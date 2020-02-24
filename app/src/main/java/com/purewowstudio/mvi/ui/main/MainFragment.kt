package com.purewowstudio.mvi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.purewowstudio.mvi.R
import kotlinx.android.synthetic.main.main_fragment.*
import com.purewowstudio.mvi.ui.main.MainView.Event.IncrementPressed
import com.purewowstudio.mvi.ui.main.MainView.Event.DecrementPressed
import com.purewowstudio.mvi.ui.main.MainView.Effect.ShowUserMessage

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        addEventListeners()
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(this, Observer(::updateUI))
        viewModel.viewEffects.observe(this, EffectObserver(::handleEffect))
    }

    private fun updateUI(viewState: MainView.State) {
        textCount.text = "${viewState.count}"
    }

    private fun handleEffect(effect: MainView.Effect) {
        when (effect) {
            is ShowUserMessage -> showSnackBar(effect.message)
        }
    }

    private fun showSnackBar(message: String) {
        with(
            Snackbar.make(
                rootView,
                message,
                Snackbar.LENGTH_LONG
            )
        ) {
            show()
        }
    }

    private fun addEventListeners() {
        buttonIncrement.setOnClickListener {
            viewModel.onEvent(IncrementPressed)
        }

        buttonDecrement.setOnClickListener {
            viewModel.onEvent(DecrementPressed)
        }
    }
}