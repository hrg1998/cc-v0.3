package com.crossclassify.examlpeapp


import com.crossclassify.trackersdk.service.config.TrackerSdkApplication

//Add Base Class And Pass IdSite For Configuration
class MyApplication : TrackerSdkApplication() {
    override fun onCreate() {
        createDefaultConfig(7)
        super.onCreate()
    }
}