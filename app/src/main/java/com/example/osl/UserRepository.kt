package com.example.osl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun registerUser(name: String, usn: String, password: String) {
        withContext(Dispatchers.IO) {
            val user = User(name = name, usn = usn, password = password)
            userDao.insert(user)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }

    suspend fun login(usn: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.login(usn, password)
        }
    }

    suspend fun updatePassword(user: User) {
        withContext(Dispatchers.IO) {
            userDao.updatePassword(user)
        }
    }
}

