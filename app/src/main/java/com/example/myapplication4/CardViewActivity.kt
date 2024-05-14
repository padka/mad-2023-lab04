package com.example.myapplication4

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CardViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_view)

        val card = intent.getSerializableExtra("card") as? Card
        if (card != null) {
            val questionTextView: TextView = findViewById(R.id.questionTextView)
            val exampleTextView: TextView = findViewById(R.id.exampleTextView)
            val answerTextView: TextView = findViewById(R.id.answerTextView)
            val translateTextView: TextView = findViewById(R.id.translateTextView)
            val imageView: ImageView = findViewById(R.id.imageView)

            questionTextView.text = card.question
            exampleTextView.text = card.example
            answerTextView.text = card.answer
            translateTextView.text = card.translate
            Glide.with(this).load(card.imageUri).into(imageView)
        }
    }
}
