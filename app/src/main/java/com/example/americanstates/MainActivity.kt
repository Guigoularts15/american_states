package com.example.americanstates

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.InputStream
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Item>
    private lateinit var tempArrayList: ArrayList<Item>
    var state = ArrayList<String>()
    var population = ArrayList<String>()
    var id = ArrayList<String>()
    var year = ArrayList<String>()
    var slug = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchView = findViewById<SearchView>(R.id.searchView)

        change_hint_color()


        newRecyclerView = findViewById(R.id.recyclerView)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Item>()
        tempArrayList = arrayListOf<Item>()

        addToTheList()


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if (it.state.lowercase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }

                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }
                else {
                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })
    }

    fun change_hint_color() {
        //Muda a cor do hint
        val searchBar: SearchView = findViewById(R.id.searchView)
        val textColor =
            searchBar.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val textView = searchBar.findViewById<TextView>(textColor)
        textView.setHintTextColor(Color.parseColor("#c5d1db"))
    }

    fun jsonData(): String {
        val inputStream: InputStream = assets.open("data.json")
        val sizeOfFile = inputStream.available()
        val bufferData =ByteArray(sizeOfFile)
        inputStream.read(bufferData)
        inputStream.close()
        val json = String(bufferData, UTF_8)

        return json
    }



    fun addToTheList() {
        val jsonObject = JSONObject(jsonData())
        var jsonArray = jsonObject.getJSONArray("data")
        val formatter = NumberFormat.getInstance(Locale.ITALY)

        for (i in 0..jsonArray.length() - 1 ) {
            val userdata = jsonArray.getJSONObject(i)
            state.add(userdata.getString("State"))
            population.add(formatter.format(userdata.getString("Population").toInt()))
            id.add(userdata.getString("ID State"))
            year.add(userdata.getString("Year"))
            slug.add(userdata.getString("Slug State"))
        }

        for (i in 0..jsonArray.length() - 1) {
            val item = Item(state[i], population[i], id[i], year[i], slug[i])
            newArrayList.add(item)
        }

        var adapter = MyAdapter(tempArrayList)
        newRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, EditState::class.java)
                intent.putExtra("state", newArrayList[position].state)
                intent.putExtra("population", newArrayList[position].population)
                intent.putExtra("id", newArrayList[position].id)
                intent.putExtra("year", newArrayList[position].year)
                intent.putExtra("slug", newArrayList[position].slug)
                intent.putExtra("jsonData", jsonData())
                startActivity(intent)
            }

        })

        tempArrayList.addAll(newArrayList)

    }

}