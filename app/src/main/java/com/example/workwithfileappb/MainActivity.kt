package com.example.workwithfileappb

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

const val PHOTO_PICKER_REQUEST_CODE = 20

class MainActivity : AppCompatActivity() {
    lateinit var photoImageView: ImageView
    lateinit var getPhotoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoImageView = findViewById(R.id.photo_image_view)
        getPhotoButton = findViewById(R.id.get_photo_button)

        getPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, getImageCollectionUri())
            startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Log.i("Camera", "cant get photo")
        } else {
            photoImageView.setImageURI(data?.data)
        }
    }


    private fun getImageCollectionUri() = sdk29AndUp {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    private inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else null
    }
}