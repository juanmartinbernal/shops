package com.juanmartin.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel base para el patrón MVI.
 *
 * - [state]: fuente única de verdad, observable como [StateFlow].
 * - [effect]: eventos de una sola vez (navegación, mensajes).
 * - [onIntent]: punto de entrada para las acciones de la UI.
 */
abstract class MviViewModel<S : UiState, I : UiIntent, E : UiEffect> : ViewModel() {

    private val _state = MutableStateFlow(initialState())
    val state: StateFlow<S> = _state.asStateFlow()

    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect: Flow<E> = _effect.receiveAsFlow()

    protected val currentState: S get() = _state.value

    abstract fun initialState(): S

    /** Procesa una intención emitida por la UI. */
    abstract fun onIntent(intent: I)

    protected fun setState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected fun sendEffect(builder: () -> E) {
        viewModelScope.launch { _effect.send(builder()) }
    }
}
