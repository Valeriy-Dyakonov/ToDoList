package com.example.myapplication.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*


object LocaleUtils {
    fun updateLanguage(context: Context, locale: Locale): Configuration {
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        context.applicationContext.resources.configuration.setLocale(locale)
        return config
    }

    fun attachBaseContext(context: Context): Context? {
        return updateResources(context)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context): Context? {
        val resources: Resources = context.resources
        val locale = context.applicationContext.resources.configuration.locales[0]
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        configuration.setLocales(LocaleList(locale))
        return context.createConfigurationContext(configuration)
    }
}