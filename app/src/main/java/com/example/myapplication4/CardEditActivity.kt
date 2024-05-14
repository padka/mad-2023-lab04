package com.example.myapplication4

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class CardEditActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var exampleEditText: EditText
    private lateinit var answerEditText: EditText
    private lateinit var translateEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_edit)

        questionEditText = findViewById(R.id.questionEditText)
        exampleEditText = findViewById(R.id.exampleEditText)
        answerEditText = findViewById(R.id.answerEditText)
        translateEditText = findViewById(R.id.translateEditText)
        imageView = findViewById(R.id.imageView)
        saveButton = findViewById(R.id.saveButton)

        imageView.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        }

        saveButton.setOnClickListener {
            val question = questionEditText.text.toString()
            val example = exampleEditText.text.toString()
            val answer = answerEditText.text.toString()
            val translate = translateEditText.text.toString()

            if (question.isNotEmpty() && example.isNotEmpty() && answer.isNotEmpty() && translate.isNotEmpty() && imageUri != null) {
                val card = Card(
                    id = UUID.randomUUID().toString(),
                    question = question,
                    example = example,
                    answer = answer,
                    translate = translate,
                    imageUri = imageUri.toString()
                )
                val resultIntent = Intent()
                resultIntent.putExtra("card", card)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Заполните все поля и выберите изображение", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_GET)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(this, "Разрешение на доступ к галерее отклонено", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val REQUEST_IMAGE_GET = 1
        const val REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 2
    }
}
