package com.juanmartin.core.mvi

/** Marca el estado inmutable de una pantalla en el patrón MVI. */
interface UiState

/** Marca las intenciones (acciones del usuario) en el patrón MVI. */
interface UiIntent

/** Marca los efectos de una sola vez (navegación, toasts...) en el patrón MVI. */
interface UiEffect
