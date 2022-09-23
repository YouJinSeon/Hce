package com.tmonet.hcetest.data.repository

import com.js.shard.model.User
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(

): UserRepository {
    /**
     * try/catch
     */
    override suspend fun getUser(name: String): User {
        return User(name)
    }
}