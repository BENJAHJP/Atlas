package com.example.atlas.data


interface PostRepository {

    suspend fun getPosts(): List<Post>

    suspend fun pushPost(post: Post)

    suspend fun deletePost(postId:Int)

    suspend fun updatePost(postId:Int, post: Post)

    suspend fun getPostById(postId: Int): Post?
}