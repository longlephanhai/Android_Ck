package com.example.ck_android.repositories

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USER_NAME_KEY = stringPreferencesKey("user_name")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
    }

    // Lưu access_token
    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    // Lấy access_token
    fun getAccessToken(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }

    // Lưu thông tin người dùng
    suspend fun saveUser(_id: String, name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = _id
            prefs[USER_NAME_KEY] = name
            prefs[USER_EMAIL_KEY] = email
        }
    }

    // Lấy thông tin người dùng
    fun getUser(): Flow<Triple<String?, String?, String?>> {
        return context.dataStore.data.map { prefs ->
            Triple(
                prefs[USER_ID_KEY],
                prefs[USER_NAME_KEY],
                prefs[USER_EMAIL_KEY]
            )
        }
    }

    // Xóa access_token khi đăng xuất
    suspend fun clearAccessToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN_KEY)
        }
    }
}


