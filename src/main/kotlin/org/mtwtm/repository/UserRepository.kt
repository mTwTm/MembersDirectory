package org.mtwtm.repository

import org.mtwtm.data.User
import java.io.*
import java.nio.file.Files
import java.nio.file.Path

class UserRepository(private val filePath: String) {
    private val phoneToUser = mutableMapOf<String, User>()
    private val cardToUser = mutableMapOf<String, User>()
    private val nameToUser = mutableMapOf<String, User>()
    private val otherToUser = mutableMapOf<String, User>()

    fun search(string: String): User? {
        return searchByName(string)
            ?: searchByPhone(string)
            ?: searchByCard(string)
            ?: searchByOther(string)
    }

    private fun searchByPhone(phoneNumber: String): User? {
        return phoneToUser[phoneNumber]
    }

    private fun searchByCard(cardId: String): User? {
        return cardToUser[cardId]
    }

    private fun searchByName(name: String): User? {
        return nameToUser[name]
    }

    private fun searchByOther(other: String): User? {
        return otherToUser[other]
    }

    fun insert(user: User) {
        val file = File(filePath)
        val fileClonePath = filePath + '_' + System.currentTimeMillis()
        val fileClone = File(fileClonePath)
        file.copyTo(fileClone)
        if (Files.mismatch(Path.of(filePath), Path.of(filePath)) != -1L) {
            throw IOException("File clone failed")
        }
        addUserToCache(user)
        val bw = BufferedWriter(FileWriter(file, true))
        bw.write(user.toString())
        bw.newLine()
        bw.close()
    }

    init {
        createNewFileIfNotExist()
    }

    private fun createNewFileIfNotExist() {
        val file = File(filePath)
        if (!file.exists()) {
            file.createNewFile()
            insert(User("0000", "游騰保", "0987654321", "", 0))
        } else {
            val br = BufferedReader(FileReader(file))
            while (true) {
                val line = br.readLine()?.takeIf { it.isNotEmpty() }?: break
                val split = line.split(",")
                val cardId = split[0]
                val name = split[1]
                val phone = split[2]
                val other = split[3]
                val timestamp = split[4].toLongOrNull() ?: 0
                addUserToCache(cardId, name, phone, other, timestamp)
            }
        }
    }

    private fun addUserToCache(cardId: String, name: String, phone: String, other: String, timestamp: Long) {
        val user = User(cardId, name, phone, other, timestamp)
        addUserToCache(user)
    }

    private fun addUserToCache(user: User) {
        insertIfNotEmpty(cardToUser, user.cardId, user)
        insertIfNotEmpty(nameToUser, user.name, user)
        insertIfNotEmpty(phoneToUser, user.phone, user)
        insertIfNotEmpty(otherToUser, user.other, user)
    }

    private fun insertIfNotEmpty(map: MutableMap<String, User>, key: String, value: User) {
        key.takeIf { it.isNotEmpty() }?.let {
            map[it] = value
        }
    }
}