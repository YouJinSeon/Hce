package com.js.domain.usecase.login

import com.js.domain.UseCase
import com.js.shard.di.IoDispatcher
import com.js.shard.model.User
import com.tmonet.hcetest.data.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher,
) : UseCase<GetUserUseCase.Param, User>(dispatcher) {

    override suspend fun execute(param: Param): User {
        return userRepository.getUser(param.name)
    }

    data class Param(
        val name: String
    )

}