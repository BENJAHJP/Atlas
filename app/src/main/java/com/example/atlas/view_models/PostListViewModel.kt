package com.example.atlas.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atlas.data.Post
import com.example.atlas.data.PostRepository
import com.example.atlas.events.PostListEvents
import com.example.atlas.util.Routes
import com.example.atlas.util.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val postRepository: PostRepository
): ViewModel(){
    var posts by mutableStateOf<List<Post?>>(emptyList())

    private var deletedPost: Post? = null

    private val _uiEvent = Channel<UiEvents>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(postListEvents: PostListEvents){
        when(postListEvents){
            is PostListEvents.OnPostClicked->{
                try {
                    sendUiEvent(UiEvents.OnNavigate(Routes.ADD_EDIT_POST + "?postId=${postListEvents.post.id}"))
                }catch (e: HttpException){
                    sendUiEvent(UiEvents.ShowSnackBar(
                        message = e.toString()
                    ))
                }
            }
            is PostListEvents.OnUndoDelete->{
                deletedPost?.let { post->
                    viewModelScope.launch {
                        postRepository.pushPost(post)
                    }
                }
            }
            is PostListEvents.OnCreatePostClicked->{
                sendUiEvent(UiEvents.OnNavigate(Routes.ADD_EDIT_POST))
            }
            is PostListEvents.OnGetPosts->{
                viewModelScope.launch {
                    try {
                        posts = postRepository.getPosts()
                    }catch (e: Exception){
                        sendUiEvent(UiEvents.ShowSnackBar(
                            message = e.toString()
                        ))
                    }
                }
            }
            is PostListEvents.OnDelete->{
                viewModelScope.launch {
                    try {
                        deletedPost = postListEvents.post
                        postListEvents.post.id?.let { postId ->
                            postRepository.deletePost(postId)
                        }
                        sendUiEvent(UiEvents.ShowSnackBar(
                            message = "deleted successfully reload the page",
                            action = "undo"
                        ))
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