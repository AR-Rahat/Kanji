package com.asfakur.kanji.app

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class KanjiApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.firestore
    }
}