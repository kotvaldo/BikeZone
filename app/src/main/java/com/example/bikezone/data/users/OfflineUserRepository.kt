package com.example.bikezone.data.users

import kotlinx.coroutines.flow.Flow


class OfflineUserRepository(private val userDao: UserDao) : UserRepository {
    override fun getAuthUserStream(auth: Boolean): Flow<User?> = userDao.getAuthUser(auth)

    override fun getUserByIdStream(id: Int): Flow<User?> = userDao.getUserById(id)
    override fun getUserByEmailAndPasswordStream(email: String, password: String): Flow<User?> = userDao.getUserByEmailAndPassword(email, password)
    override fun getUserByEmailStream(email: String): Flow<User?> = userDao.getUserByEmail(email)

    override suspend fun insert(user: User) = userDao.insert(user)

    override suspend fun delete(user: User) = userDao.delete(user)

    override suspend fun update(user: User) = userDao.update(user)

}