package com.example.atlas.events

sealed class AddEditPostEvents{
    data class OnTittleChanged(var title: String): AddEditPostEvents()
    data class OnDescriptionChanged(var description: String): AddEditPostEvents()
    object OnSaveClicked: AddEditPostEvents()
    object OnUpdateClicked: AddEditPostEvents()
}
