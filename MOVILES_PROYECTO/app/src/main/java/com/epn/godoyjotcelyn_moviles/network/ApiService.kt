package com.epn.godoyjotcelyn.network

import com.epn.godoyjotcelyn_moviles.network.KtorClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class NetworkRepository {
    private val client = KtorClient.httpClient
    private val baseUrl = "https://jsonplaceholder.typicode.com/posts"

    suspend fun getPost(id: Int): Post {
        return client.get("$baseUrl/$id").body()
    }

    suspend fun updatePost(id: Int, post: Post): Post {
        return client.put("$baseUrl/$id") {
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body()
    }
}