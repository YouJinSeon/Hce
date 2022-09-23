package com.tmonet.hcetest.data.repository

import com.js.shard.model.User

interface UserRepository {
    suspend fun getUser(name: String): User
}