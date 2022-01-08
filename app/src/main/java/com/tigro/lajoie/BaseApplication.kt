package com.tigro.lajoie

import android.app.Application
import com.google.android.material.color.DynamicColors

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}