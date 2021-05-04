package org.mtwtm.data

import java.util.*

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

    fun toUserString(): String {
        return "${strings.getString(FIELD_NAME)}: $name\n" +
                "${strings.getString(FIELD_CARD)}: $cardId\n" +
                "${strings.getString(FIELD_PHONE)}: $phone\n" +
                "${strings.getString(FIELD_OTHER)}: $other\n"
    }

    companion object {
        private val strings: ResourceBundle = ResourceBundle.getBundle("strings")
        private const val FIELD_CARD = "org.mtwtm.membersDirectory.field.card"
        private const val FIELD_NAME = "org.mtwtm.membersDirectory.field.name"
        private const val FIELD_PHONE = "org.mtwtm.membersDirectory.field.phone"
        private const val FIELD_OTHER = "org.mtwtm.membersDirectory.field.other"
        private const val EMPTY_FIELD = "org.mtwtm.membersDirectory.emptyField"
    }
}
