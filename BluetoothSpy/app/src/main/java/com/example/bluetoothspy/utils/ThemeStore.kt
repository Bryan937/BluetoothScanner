package com.example.bluetoothspy.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeStore(private val context: Context) {
    // Singleton, only one instance of the datastore
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("AppTheme")
        val THEME_KEY = booleanPreferencesKey("darkTheme")
    }

    val getTheme: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: false}

    suspend fun saveTheme (darkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = darkTheme
        }
    }
}