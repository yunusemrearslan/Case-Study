package com.example.takenote

import android.app.Application

class TakeNoteApplication: Application() {

    companion object {
        //Static instance for using application variables from other classes
        lateinit var instance: TakeNoteApplication
            private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}