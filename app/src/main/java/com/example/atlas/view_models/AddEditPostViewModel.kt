package com.example.atlas.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlas.data.Post
import com.example.atlas.data.PostRepository
import com.example.atlas.events.AddEditPostEvents
import com.example.atlas.util.Routes
import com.example.atlas.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var post by mutableStateOf<Post?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var isUpdate: Boolean = false

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        val postId = savedStateHandle.get<Int>("postId")!!

        if (postId != -1){
            viewModelScope.launch {
                isUpdate = true
                postRepository.getPostById(postId).let { post ->
                    title = post?.title?:""
                    description = post?.description?: ""
                    this@AddEditPostViewModel.post = post
                }
            }
        }
    }
    fun onEvent(addPostEvents: AddEditPostEvents){
        when(addPostEvents){
            is AddEditPostEvents.OnSaveClicked->{
                viewModelScope.launch {
                    if (title.isBlank() || description.isBlank()){
                        sendUiEvent(UiEvents.ShowSnackBar(
                            message = "Fill in all text fields"
                        ))
                    }else{
                        try {
                        postRepository.pushPost(
                            Post(
                            title = title,
                            description = description)
                        )
                        sendUiEvent(UiEvents.OnPopBackStack)
                        }catch (e: Exception){
                        sendUiEvent(UiEvents.ShowSnackBar(
                            message = e.toString()
                        ))
                        }
                    }
                }
            }
            is AddEditPostEvents.OnDescriptionChanged->{
                description = addPostEvents.description
            }
            is AddEditPostEvents.OnTittleChanged->{
                title = addPostEvents.title
            }
            is AddEditPostEvents.OnUpdateClicked->{
                viewModelScope.launch {
                    try {
                        post?.id?.let {
                            postRepository.updatePost(postId = it,
                                post = Post(
                                    title = title,
                                    description = description
                                )
                            )
                            sendUiEvent(UiEvents.OnPopBackStack)
                            sendUiEvent(UiEvents.ShowSnackBar(
                                message = "updated successfully"
                            ))
                        }
                    }catch (e: Exception){
                        sendUiEvent(UiEvents.ShowSnackBar(
                            message = e.toString()
                        ))
                    }
                }
            }
        }
    }

    private fun sendUiEvent(uiEvents: UiEvents){
        viewModelScope.launch {
            _uiEvent.send(uiEvents)
        }
    }
}