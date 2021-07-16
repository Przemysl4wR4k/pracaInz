package com.example.inz20.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inz20.R
import com.example.inz20.adapter.BookInfo
import com.example.inz20.adapter.BooksAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)


        adapter = BooksAdapter(this, books())
        viewPagerBooks.adapter = adapter

        TabLayoutMediator(
            tabLayout,
            viewPagerBooks
        ) { _, _ -> }.attach()

    }

    private fun books() = listOf(
        BookInfo(
            "Ewa Woydyłło",
            "Bo jesteś człowiekiem. Żyć z depresją, ale nie w depresji",
            R.drawable.bo_jestes_czlowiekiem
        ),
        BookInfo(
            "Gdziekolwiek jesteś bądź",
            "Jon Kabat-Zinn",
            R.drawable.gdziekolwiek_jestes_badz
        ),
        BookInfo(
            "Żyję! Depresja mnie nie pokona",
            "Marchenko Gillian",
            R.drawable.zyje_depresja_mnie_nie_pokona
        ),
    )
}