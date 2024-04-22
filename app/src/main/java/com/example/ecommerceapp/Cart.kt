package com.example.ecommerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Cart : AppCompatActivity() {

    lateinit var tx:RecyclerView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        tx=findViewById(R.id.txt)

//        var tt=intent.getStringExtra("name")

//        tx.setText(tt)

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

                for (i in 0 until mapfinal.size) {

                    val product = mapfinal[i]

                    Log.d("===---like", "onBindViewHolder: ${product["like"] as Long}")
                    var a  : Long = 1
                    if (product["like"] as Long == a) {
                        likelist.add(mapfinal[i])
                    }
                }

                var adapter = Cartadapter(likelist)
                tx.adapter = adapter

                Log.d("=====", "oncreate:$likelist")

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)
    }

}