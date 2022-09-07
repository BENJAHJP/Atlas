package com.example.atlas.data

class PostRepositoryImpl(
    private val postApi: PostApi
): PostRepository {
    override suspend fun getPosts(): List<Post> {
        return postApi.getPosts()
    }

    override suspend fun pushPost(post: Post){
        postApi.pushPost(post)
    }

    override suspend fun deletePost(postId: Int) {
        postApi.deletePost(postId)
    }

    override suspend fun updatePost(postId: Int, post: Post) {
        postApi.updatePost(postId, post)
    }

    override suspend fun getPostById(postId: Int): Post? {
        return postApi.getPostById(postId)
    }
}