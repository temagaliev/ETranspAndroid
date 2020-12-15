package com.example.kotlin_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    val dataClass = Data()
    val dataTram = dataClass.dataTram
    val displayList = ArrayList<Transport>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayList.addAll(dataTram)

        val recyclerAdapter = RecyclerAdapter(displayList, object: MainOnClickListener {
            override fun onClick(textName: String, nameStation: String) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("link", textName)
                intent.putExtra("nameStation", nameStation)
                startActivity(intent)
            }
        })

        recyclerView.apply {
            adapter = recyclerAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        val menuItem = menu!!.findItem(R.id.search)


       if (menuItem != null) {
           val searchView = menuItem.actionView as SearchView
           searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {


               override fun onQueryTextSubmit(query: String?): Boolean {
                   return true
               }



               override fun onQueryTextChange(newText: String?): Boolean {

                   if (newText!!.isNotEmpty()) {
                       displayList.clear()
                       val search = newText.toLowerCase()
                       dataTram.forEach {
                           if ((it.nameStation.toLowerCase().contains(search)) || (it.searchName.toLowerCase().contains(search)) ) {
                               displayList.add(it)
                           }
                       }
                       recyclerView.adapter!!.notifyDataSetChanged()
                   }
                   else {
                       displayList.clear()
                       displayList.addAll(dataTram)
                       recyclerView.adapter!!.notifyDataSetChanged()
                   }


                   return true
               }


           })
      }

      return super.onCreateOptionsMenu(menu)
  }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
      return super.onOptionsItemSelected(item)
  }
}
