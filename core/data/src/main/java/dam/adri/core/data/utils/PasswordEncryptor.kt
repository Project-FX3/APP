package dam.adri.core.data.utils

import java.security.MessageDigest

object PasswordEncryptor {
    fun encryptPassword(password: String): String {
        val bytes = password.toByteArray()
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(bytes)
        return bytesToHex(hashBytes)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexChars = "0123456789ABCDEF"
        val hex = StringBuilder(bytes.size * 2)
        for (i in bytes.indices) {
            val v = bytes[i].toInt() and 0xFF
            hex.append(hexChars[v ushr 4])
            hex.append(hexChars[v and 0x0F])
        }
        return hex.toString()
    }
}
