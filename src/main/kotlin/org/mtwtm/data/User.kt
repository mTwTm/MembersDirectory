package org.mtwtm.data

data class User(
    val cardId: String,
    val name: String,
    val phone: String,
    val other: String,
    val timeStamp: Long
) {
    override fun toString(): String {
        return "$cardId,$name,$phone,$other,$timeStamp"
    }

    fun toShortString(): String {
        return "$name\n$cardId\n$phone\n$other"
    }
}
