package com.example.atlas.events

import com.example.atlas.data.Post

sealed class PostListEvents{
    data class OnDelete(val post: Post): PostListEvents()
    data class OnPostClicked(val post: Post): PostListEvents()
    object OnCreatePostClicked: PostListEvents()
    object OnGetPosts: PostListEvents()
    object OnUndoDelete: PostListEvents()
}
