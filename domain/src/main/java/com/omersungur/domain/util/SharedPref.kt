package com.omersungur.domain.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPref(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(KEY_OF_SHARED_PREF, MODE_PRIVATE)

    fun saveOnboardingShowingState() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_OF_ONBOARDING_SHOWING_STATE, false)
        editor.apply()
    }

    fun saveLoggedInState() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_OF_LOGGED_IN_STATE, false)
        editor.apply()
    }

    fun isShowingOnboardingScreen(): Boolean = sharedPreferences.getBoolean(KEY_OF_ONBOARDING_SHOWING_STATE, true)

    fun isLoggedIn(): Boolean = sharedPreferences.getBoolean(KEY_OF_LOGGED_IN_STATE, false)

    companion object {
        private const val KEY_OF_SHARED_PREF = "MySharedPref"
        private const val KEY_OF_ONBOARDING_SHOWING_STATE = "onboardingShowingPrefKey"
        private const val KEY_OF_LOGGED_IN_STATE = "loggedInPrefKey"
    }
}
