package dam.adri.core.presentation.userManager

import dam.adri.core.data.ConstantInfo.KEY_USER_ID
import dam.adri.core.data.ConstantInfo.KEY_USER_LOGGED_IN
import android.content.SharedPreferences

class UserManager(private val sharedPreferences: SharedPreferences) {

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_USER_LOGGED_IN, false)
    }

    fun logIn(userId: Int) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_USER_LOGGED_IN, true)
            putInt(KEY_USER_ID, userId)
            apply()
        }
    }

    fun logout() {
        sharedPreferences.edit().apply {
            putBoolean(KEY_USER_LOGGED_IN, false)
            remove(KEY_USER_ID)
            apply()
        }
    }

    fun getUserId(): Int? {
        return if (isUserLoggedIn()) {
            sharedPreferences.getInt(KEY_USER_ID, -1)
        } else null
    }
}


