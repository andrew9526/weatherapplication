package com.example.weatherapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Hilt dependency injection
 * 
 * @HiltAndroidApp triggers Hilt's code generation
 * This must be declared in AndroidManifest.xml
 */
@HiltAndroidApp
class WeatherApplication : Application()
