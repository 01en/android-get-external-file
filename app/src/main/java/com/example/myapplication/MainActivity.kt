package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {
    companion object {
        val CHOSE_FILE_CODE = 123
        val TAG = "tag-file-reader"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.button)
        textView.setOnClickListener {
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, CHOSE_FILE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == CHOSE_FILE_CODE && resultCode == RESULT_OK) {
                val filePath = data?.dataString ?: ""
                Toast.makeText(this, filePath, Toast.LENGTH_LONG).show()
                Log.d(TAG, filePath)

                val uri = data?.data
                if (uri != null) {
                    readFile(uri)
                } else {
                    Toast.makeText(this, "ファイルの読み込みに失敗しました", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: UnsupportedEncodingException) {
            Log.d(TAG, e.toString())
        }
    }

    private fun readFile(uri: Uri) {
        val builder = StringBuilder()
        try {
            val inputStream = contentResolver.openInputStream(uri)
            if (inputStream === null) {
                return
            }
            inputStream.bufferedReader().use { stream ->
                BufferedReader(stream).use { reader ->
                    var line: String? = reader.readLine()
                    while (line != null) {
                        builder.append(line)
                        line = reader.readLine()
                    }
                }
            }
            val content = builder.toString()
            Toast.makeText(this, content, Toast.LENGTH_LONG).show()
            Log.d(TAG, content)
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }
}