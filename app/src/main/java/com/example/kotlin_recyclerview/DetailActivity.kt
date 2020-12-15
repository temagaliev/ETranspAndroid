package com.example.kotlin_recyclerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import java.io.IOException
import kotlin.concurrent.thread


class DetailActivity : AppCompatActivity() {

    private var url = "https://mobile.ettu.ru/station/1168"
    var dataTransport = arrayListOf<ItemStation>()
    val recyclerDetailAdapter = RecyclerDetailAdapter(dataTransport)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)



        val intent = intent
        val link = intent.getStringExtra("link")
        val nameStationText = intent.getStringExtra("nameStation")
        actionBar.title = nameStationText
        url = link


        getData()
        Log.d("AdapterArray", dataTransport.toString())


        recyclerDetailView.apply {
            adapter = recyclerDetailAdapter

            addItemDecoration(
                DividerItemDecoration(
                    this@DetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
        }

        var isUpdate = false
        while (isUpdate != true) {
            if (dataTransport.size == 0) {
                recyclerDetailAdapter.notifyDataSetChanged()
                isUpdate = false
            } else {
                isUpdate = true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    fun getData() {
        thread {
            try {
                val document = Jsoup.connect(url).get()
                val element = document.select("div")
                val newElement = element.toString()
                var numberTranText = ""
                var timeTranText = ""
                var meterTranText = ""

                val replaceElement = newElement
                    .replace("<div style=\"width: 3em;display:inline-block;text-align:center;\">", "")
                    .replace("<div style=\"width: 4em;display:inline-block;text-align:right;\">", "")
                    .replace("<div style=\"width: 5em;display:inline-block;text-align:right;\">", "")
                    .replace("</div>", "")
                    .replace("I/System.out:", "")
                    .replace("<b>", "ъ")
                    .replace("</b>", "й")
                    .replace("<p>", "")
                    .replace("</p>", "")
                    .replace("<div style=\"max-width: 35em; margin: auto;\"> <a id=\"gotoSite\" target=\"_blank\" style=\"display:none;font-size:large\">Перейти на m.ettu.ru</a> ", "")
                    .replace("<h2><a href=\"/m/Main\"> Где трамвай </a></h2> ", "")
                    .replace("<p> 1-й км (на Пионерскую), <b> 20:05</b> </p> ", "")
                    .replace("<div style=\"text-align: right;\"> <a href=\"javascript:location.reload(true)\">Обновить</a> ", "")
                    .replace("<div>", "")
                    .replace("мин","ё")
                    .replace("м", "ь")
                    .replace("<br>","")
                    .replace("<ah", "♘")
                    .replace("Карта\">", "♕")
                    .replace("Feedback", "♘")
                    .replace("\n", "")
                    .replace(" ", "")


                var sortedDataArray = ""


                for (i in 0..replaceElement.length - 1) {
                    if (replaceElement[i] == '♕') {
                        sortedDataArray = replaceElement.removeRange(0,i)
                    }
                }

                for (i in 0..sortedDataArray.length) {

                    Log.d("Check", sortedDataArray[i].toString())
                    if (sortedDataArray[i] == 'ъ') {
                        Log.d("rw[i] == 'ъ'", "true")
                        var currentCount = 1
                        var text = ""
                        while (sortedDataArray[i + currentCount] != 'й') {
                            text = text + sortedDataArray[i + currentCount]
                            currentCount = currentCount + 1
                        }
                        numberTranText = text + " т"
                        Log.d("numberTranText", numberTranText)
                    } else if (sortedDataArray[i] == 'й') {
                        Log.d("rw[i] == 'й'", "true")
                        var currentCount = 1
                        var text = ""
                        while (sortedDataArray[i + currentCount] != 'ё') {
                            text = text + sortedDataArray[i + currentCount]
                            currentCount = currentCount + 1
                        }
                        timeTranText = text + " мин"
                        Log.d("timeTranText", timeTranText)
                    } else if (sortedDataArray[i] == 'ё') {
                        Log.d("rw[i] == 'ё'", "true")
                        var currentCount = 1
                        var text = ""
                        while (sortedDataArray[i + currentCount] != 'ь') {
                            text = text + sortedDataArray[i + currentCount]
                            currentCount = currentCount + 1
                        }
                        meterTranText = text + " м"
                        Log.d("meterTranText", meterTranText)
                    } else if (sortedDataArray[i] == '♘') {
                        break
                    }
                    if (meterTranText != "") {
                        dataTransport.add(ItemStation(numberTranText, timeTranText, meterTranText))
                        Log.d("dataTransport", dataTransport.toString())
                        meterTranText = ""
                    }

                }

            } catch (e: IOException) {
                //e.printStackTrace()
            }
        }
    }
}

