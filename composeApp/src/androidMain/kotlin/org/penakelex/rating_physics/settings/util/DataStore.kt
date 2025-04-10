package org.penakelex.rating_physics.settings.util

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

suspend fun getStringPreference(context: Application, key: String): String? =
    context.dataStore.data.map { preferences ->
        preferences[stringPreferencesKey(key)]
    }.first()

suspend fun <T> savePreference(context: Application, key: String, value: T) =
    context.dataStore.edit { preferences ->
        preferences[stringPreferencesKey(key)] = value.toString()
    }
