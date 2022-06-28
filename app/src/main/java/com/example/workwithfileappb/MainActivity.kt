package com.example.workwithfileappb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.workwithfileappb.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val photoUri = intent.data
        if (photoUri != null) {
           binding.photoImageView.setImageURI(photoUri)
        } else {
            Log.i("Camera", "No extras")
        }

        val fileUri = intent.data
        if(fileUri != null) {
            binding.fileContentTextView.text = readFromUri(fileUri)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val photoUri = intent?.data
        if (photoUri != null) {
            binding.photoImageView.setImageURI(photoUri)
        } else {
            Log.i("Camera", "No extras")
        }

        val fileUri = intent?.data
        if(fileUri != null) {
            binding.fileContentTextView.text = readFromUri(fileUri)
        }
    }

    @Throws(IOException::class)
    fun readFromUri(uri: Uri): String {
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }
}