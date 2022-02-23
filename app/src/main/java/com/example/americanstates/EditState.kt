package com.example.americanstates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

class EditState : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_state)

        val bundle: Bundle? = intent.extras
        val population = bundle!!.getString("population")
        val id = bundle!!.getString("id")
        val year = bundle!!.getString("year")
        val slug = bundle!!.getString("slug")
        val stateTitle = bundle!!.getString("state")
        print(population)

        val textEditID = findViewById<EditText>(R.id.textEditID)
        val textEditState = findViewById<EditText>(R.id.textEditState)
        val textEditYear = findViewById<EditText>(R.id.textEditYear)
        val textEditPop = findViewById<EditText>(R.id.textEditPop)
        val textEditSlug = findViewById<EditText>(R.id.textEditSlug)
        val titleView = findViewById<TextView>(R.id.titleView)

        val editableID = SpannableStringBuilder(id)
        val editableState = SpannableStringBuilder(stateTitle)
        val editableYear = SpannableStringBuilder(year)
        val editablePop = SpannableStringBuilder(population)
        val editableSlug = SpannableStringBuilder(slug)

        val myDB = DatabaseHelper(this@EditState)


        titleView.text = "EDITAR DADOS DO $stateTitle"

        textEditID.text = editableID
        textEditState.text = editableState
        textEditYear.text = editableYear
        textEditPop.text = editablePop
        textEditSlug.text = editableSlug

        val format = NumberFormat.getInstance(Locale.getDefault())
        val numberPOP = format.parse(textEditPop.text.toString())


        val save_button = findViewById<Button>(R.id.button)
        save_button.setOnClickListener {
            val intent = Intent(this@EditState, MainActivity::class.java)
            myDB.addSavedData(
                textEditID.text.toString().trim(),
                textEditState.text.toString().trim(),
                textEditYear.text.toString().trim(),
                numberPOP.toInt(),
                textEditSlug.text.toString().trim()
            )

            startActivity(intent)
        }
    }

}