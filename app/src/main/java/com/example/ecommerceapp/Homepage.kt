package com.example.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Homepage : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var database: DatabaseReference
    lateinit var recycle: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        recycle = findViewById(R.id.recycle)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerlayout)
        navView = findViewById(R.id.nav_view)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        setupNavigation()

        database = Firebase.database.reference

        var likelist = mutableListOf<Map<*, *>>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                var map = dataSnapshot.child("/message/user").children

//                var mapfinal=map.get("-Nsluk1eJcL9Ivvo3he4") as Map<*, *>

                val mapfinal = mutableListOf<Map<*, *>>()
                map.forEach { productSnapshot ->
                    val product = productSnapshot.value as Map<*, *>
                    mapfinal.add(product)
                }

//                for (i in 0 until mapfinal.size) {
//
//                    val product = mapfinal[i]
//
//                    Log.d("===---like", "onBindViewHolder: ${product["like"] as Long}")
//                    var a  : Long = 1
//                    if (product["like"] as Long == a) {
//                        likelist.add(mapfinal[i])
//                    }
//                }

                var adapter = Myadapter(mapfinal)
                recycle.adapter = adapter

                Log.d("=====", "oncreate:$mapfinal")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)
    }

    private fun setupNavigation() {
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle Home click
                    //homeactivity()
                    val intent = Intent(this, Homepage::class.java)
                    startActivity(intent)
                    true
                }

                R.id.add_product -> {
                    // Handle Home click
                    //homeactivity()
                    val intent = Intent(this, Addproduct::class.java)
                    startActivity(intent)
                    true
                }

                R.id.Logout -> {

                    val intent = Intent(this, Logout::class.java)
                    startActivity(intent)
                    true
                }

                R.id.cart -> {
                    val intent = Intent(this, Cart::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }

        }


    }
}