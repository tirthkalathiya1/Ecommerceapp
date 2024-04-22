package com.example.ecommerceapp

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Myadapter(var mapfinal: MutableList<Map<*, *>>) : RecyclerView.Adapter<Myadapter.Temp>() {
    class Temp(item: View) : RecyclerView.ViewHolder(item) {

        var iamge: ImageView = item.findViewById(R.id.image)
        var name: TextView = item.findViewById(R.id.name)
        var amount: TextView = item.findViewById(R.id.amount)
        var addcart: Button = item.findViewById(R.id.addcart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Temp {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.qq, parent, false)

        return Temp(view)
    }

    override fun getItemCount(): Int {
        return mapfinal.size
    }

    override fun onBindViewHolder(holder: Temp, position: Int) {
        val product = mapfinal[position]

        holder.addcart.visibility = View.VISIBLE
        holder.name.text = "name:" + product["name"].toString()
        holder.amount.text = "Amount" + product["password"].toString()

        Glide.with(holder.itemView.context).load(product["dowlondurl"].toString())
            .into(holder.iamge)

        holder.addcart.setOnClickListener {
            var databasee: DatabaseReference =
                Firebase.database.reference.child("/message/user/${product["key"]}/like")
            databasee.setValue(1)

//            for (i in 0 until mapfinal.size) {
//
//                val product = mapfinal[i]
//
//                Log.d("===---like", "onBindViewHolder: ${product["like"] as Long}")
//                var a  : Long = 1
//                if (product["like"] as Long == a) {
//                    mapfinal.add(mapfinal[i])
//                }
//            }
            Toast.makeText(holder.itemView.context, "successfull", Toast.LENGTH_SHORT).show()

        }
    }

}
