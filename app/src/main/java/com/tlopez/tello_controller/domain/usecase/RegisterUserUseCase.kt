package com.tlopez.tello_controller.domain.usecase

import com.tlopez.tello_controller.domain.models.AuthenticatedUser
import com.tlopez.tello_controller.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): Result<Unit> {
        return authRepository.registerUser(email, username, password)
    }
}