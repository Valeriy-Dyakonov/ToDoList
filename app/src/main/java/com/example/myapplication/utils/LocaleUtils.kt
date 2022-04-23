package com.example.myapplication.utils

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import androidx.core.os.ConfigurationCompat

object LocaleUtils {
    fun setAppLocale(activity: Activity) {
        val resources: Resources = activity.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(ConfigurationCompat.getLocales(resources.configuration)[0])
        activity.applicationContext.createConfigurationContext(configuration)
    }
}