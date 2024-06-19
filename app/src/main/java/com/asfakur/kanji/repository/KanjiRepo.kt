package com.asfakur.kanji.repository

import com.asfakur.kanji.models.Kanji
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class KanjiRepo {
    suspend fun getKanjiList(week: String) : List<Kanji> {
        val kanjiList: MutableList<Kanji> = mutableListOf()
        val fb = Firebase.firestore
        val kanjiScope = fb.collection("fields").document(week).collection("kanji").get().await()
        //println(kanjiScope.documents)
        for(kanji in kanjiScope){
            //println(kanji.id)
            val kanjiData = Kanji(
                id = kanji.id,
                audio = kanji.get("audio").toString(),
                kanji = kanji.get("kanji").toString(),
                meaning = kanji.get("meaning").toString(),
                pdf = kanji.get("pdf").toString(),
                usage = kanji.get("usage").toString(),
                video = kanji.get("video").toString(),
                word = kanji.get("word").toString()
            )
            kanjiList.add(kanjiData)
        }
        return kanjiList
    }
    suspend fun fetchKanjiDetails(week: String,id: String): Kanji {
        val fb = Firebase.firestore
        val kanji = fb.collection("fields").document(week).collection("kanji").document(id).get().await()
        val kanjiData = Kanji(
            id = kanji.id,
            audio = kanji.get("audio").toString(),
            kanji = kanji.get("kanji").toString(),
            meaning = kanji.get("meaning").toString(),
            pdf = kanji.get("pdf").toString(),
            usage = kanji.get("usage").toString(),
            video = kanji.get("video").toString(),
            word = kanji.get("word").toString()
        )
        return kanjiData
    }
}