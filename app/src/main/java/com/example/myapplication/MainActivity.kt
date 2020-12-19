package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.io.UnsupportedEncodingException

class MainActivity : AppCompatActivity() {
    companion  object {
        val CHOSE_FILE_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.button)
        textView.setOnClickListener{
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, MainActivity.CHOSE_FILE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if(requestCode === MainActivity.CHOSE_FILE_CODE && resultCode == RESULT_OK){
                val filePath = data?.dataString?.replace("file://", "") ?: ""
                Toast.makeText(this, filePath, Toast.LENGTH_LONG).show()
                Log.d("ichien", filePath)

            }
        } catch (e: UnsupportedEncodingException) {

        }
    }

}