package com.example.myapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CardListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    private val cardList = mutableListOf<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(cardList, { card ->
            val intent = Intent(this, CardViewActivity::class.java)
            intent.putExtra("card", card)
            startActivity(intent)
        }, { card ->
            showDeleteDialog(card)
        })
        recyclerView.adapter = cardAdapter

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, CardEditActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_CARD)
        }

        // Пример добавления карточек в список
        val sampleCard = Card("1", "What is the capital of France?", "Paris is the capital.", "Paris", "Париж", "")
        cardList.add(sampleCard)
        cardAdapter.notifyDataSetChanged()
    }

    private fun showDeleteDialog(card: Card) {
        AlertDialog.Builder(this)
            .setTitle("Delete Card")
            .setMessage("Are you sure you want to delete this card?")
            .setPositiveButton("Yes") { _, _ ->
                cardList.remove(card)
                cardAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_CARD && resultCode == RESULT_OK) {
            val card = data?.getSerializableExtra("card") as? Card
            if (card != null) {
                cardList.add(card)
                cardAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_ADD_CARD = 1
    }
}
