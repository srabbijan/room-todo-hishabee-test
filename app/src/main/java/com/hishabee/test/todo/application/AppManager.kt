package com.hishabee.test.todo.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.hishabee.test.todo.database.LocalDB

class AppManager : Application() {
    init {
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    val database by lazy { LocalDB.getDatabase(this) }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        private var instance: AppManager? = null
        fun appContext(): AppManager {
            return instance as AppManager
        }
    }
}