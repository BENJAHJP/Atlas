package com.example.atlas.data

import retrofit2.http.*

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @POST("posts")
    suspend fun pushPost(@Body post: Post)

    @DELETE("posts/{postId}")
    suspend fun deletePost(@Path("postId") postId:Int)

    @PUT("posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId:Int,
        @Body post: Post
    )

    @GET("{postId}")
    suspend fun getPostById(@Path ("postId") postId: Int ): Post?
}