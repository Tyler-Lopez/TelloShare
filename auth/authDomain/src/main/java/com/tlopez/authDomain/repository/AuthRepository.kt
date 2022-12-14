package com.tlopez.authDomain.repository

import com.tlopez.authDomain.models.AuthenticatedUser

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<Unit>
    suspend fun updatePassword(oldPassword: String, newPassword: String): Result<Unit>
    suspend fun getAccessToken(): Result<String>
    suspend fun getUser(): Result<AuthenticatedUser>
    suspend fun registerUser(email: String, username: String, password: String): Result<Unit>
    suspend fun resendVerificationEmail(username: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun verify(username: String, code: String): Result<Unit>
}