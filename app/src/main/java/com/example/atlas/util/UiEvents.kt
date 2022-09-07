package com.example.atlas.util

sealed class UiEvents{
    object OnPopBackStack: UiEvents()
    data class OnNavigate(val route: String): UiEvents()
    data class ShowSnackBar(
        val message: String,
        val action: String? = null
    ): UiEvents()
}
