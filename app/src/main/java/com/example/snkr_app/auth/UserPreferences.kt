package com.example.snkr_app.auth


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.snkr_app.auth.model.User

// DataStore instance
private val Context.dataStore by preferencesDataStore("user_prefs")

object UserPreferences {

    // Keys
    private val USER_ID = intPreferencesKey("user_id")
    private val USER_CREATED_AT = stringPreferencesKey("user_created_at")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_EMAIL = stringPreferencesKey("user_email")
    private val USER_ROLE_ID = intPreferencesKey("user_role_id")

    // Guardar usuario
    suspend fun saveUser(context: Context, user: User) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_CREATED_AT] = user.created_at
            prefs[USER_NAME] = user.name
            prefs[USER_EMAIL] = user.email
            prefs[USER_ROLE_ID] = user.role_id
        }
    }

    // Obtener usuario
    fun getUser(context: Context): Flow<User?> {
        return context.dataStore.data.map { prefs ->
            val id = prefs[USER_ID]
            val createdAt = prefs[USER_CREATED_AT]
            val name = prefs[USER_NAME]
            val email = prefs[USER_EMAIL]
            val roleId = prefs[USER_ROLE_ID]

            if (id != null && createdAt != null && name != null && email != null && roleId != null) {
                User(
                    id = id,
                    created_at = createdAt,
                    name = name,
                    email = email,
                    role_id = roleId
                )
            } else {
                null
            }
        }
    }

    // Borrar usuario (logout)
    suspend fun clearUser(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}
