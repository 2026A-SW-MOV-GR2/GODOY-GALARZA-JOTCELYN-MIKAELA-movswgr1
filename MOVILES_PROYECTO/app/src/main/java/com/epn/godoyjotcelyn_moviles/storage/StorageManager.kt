package com.epn.godoyjotcelyn_moviles.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.first

// Extensión para crear el DataStore una sola vez
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "secrets_datastore")

object StorageManager {

    // --- SharedPreferences simple ---
    private fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("secrets_simple", Context.MODE_PRIVATE)
    }

    fun saveSharedPrefs(context: Context, key: String, value: String) {
        getSharedPrefs(context).edit().putString(key, value).apply()
    }

    fun getSharedPrefs(context: Context, key: String): String? {
        return getSharedPrefs(context).getString(key, null)
    }

    // --- DataStore ---
    suspend fun saveDataStore(context: Context, key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.dataStore.edit { prefs ->
            prefs[prefKey] = value
        }
    }

    suspend fun getDataStore(context: Context, key: String): String? {
        val prefKey = stringPreferencesKey(key)
        val prefs = context.dataStore.data.first()
        return prefs[prefKey]
    }

    // --- EncryptedSharedPreferences ---
    private fun getEncryptedPrefs(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secrets_encrypted",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveEncrypted(context: Context, key: String, value: String) {
        getEncryptedPrefs(context).edit().putString(key, value).apply()
    }

    fun getEncrypted(context: Context, key: String): String? {
        return getEncryptedPrefs(context).getString(key, null)
    }
}