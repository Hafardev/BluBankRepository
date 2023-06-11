package com.example.sampleapplication

//import androidx.multidex.MultiDex
import android.app.Application
import android.preference.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BluApplication @Inject constructor() : Application() {

 /*   override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //MultiDex.install(this)
    }*/

}