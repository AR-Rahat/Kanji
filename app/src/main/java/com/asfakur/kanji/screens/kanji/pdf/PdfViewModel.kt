package com.asfakur.kanji.screens.kanji.pdf

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.os.AsyncTask
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PdfViewModel: ViewModel() {

    private val _pdfStream = MutableLiveData<InputStream?>()
    val pdfStream: LiveData<InputStream?> = _pdfStream

    fun loadPdf(url: String) {
        PdfLoaderTask().execute(url)
    }

    @SuppressLint("StaticFieldLeak")
    private inner class PdfLoaderTask : AsyncTask<String, Void, InputStream?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String): InputStream? {
            val url = URL(params[0])
            val urlConnection = url.openConnection() as HttpsURLConnection
            return try {
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedInputStream(urlConnection.inputStream)
                } else {
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: InputStream?) {
            _pdfStream.value = result
        }
    }
}
