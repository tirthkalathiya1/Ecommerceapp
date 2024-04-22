package com.example.ecommerceapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.ByteArrayOutputStream

class Addproduct : AppCompatActivity() {

    lateinit var image: ImageView
    lateinit var btn: Button
    lateinit var name: EditText
    lateinit var pass: EditText

    lateinit var firebaseAuth: FirebaseAuth
    var storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addproduct)

        image=findViewById(R.id.image)
        btn=findViewById(R.id.btn)
        name=findViewById(R.id.name)
        pass=findViewById(R.id.pass)
        storage = Firebase.storage

        firebaseAuth=FirebaseAuth.getInstance()

        image.setOnClickListener {

            CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

        }
        btn.setOnClickListener {

            // Get the data from an ImageView as bytes
            image.isDrawingCacheEnabled = true
            image.buildDrawingCache()
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val storageRef = storage.reference
            val mountainsRef = storageRef.child("mountains${Math.random()}.jpg")
            mountainsRef.name == mountainsRef.name // true
            mountainsRef.path == mountainsRef.path // false
            var uploadTask = mountainsRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
            }
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                mountainsRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val database = Firebase.database
                    val myRef = database.getReference("message/user").push()

                    var dataa=Mydata(downloadUri.toString(),name.text.toString(),pass.text.toString(),myRef.key)
                    myRef.setValue(dataa)

                } else {
                    // Handle failures
                    // ...
                }
            }

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                image.setImageURI(resultUri)
//                var dataa=Mydata(name.text.toString(),pass.text.toString())

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }


    }
}