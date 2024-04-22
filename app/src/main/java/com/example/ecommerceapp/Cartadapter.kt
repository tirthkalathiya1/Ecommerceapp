package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Cartadapter(var mapfinal: MutableList<Map<*, *>>) : RecyclerView.Adapter<Cartadapter.Cartview>() {
    class Cartview(item:View):RecyclerView.ViewHolder(item) {

        var iamge: ImageView = item.findViewById(R.id.image)
        var name: TextView = item.findViewById(R.id.name)
        var amount: TextView = item.findViewById(R.id.amount)
        var delete: Button = item.findViewById(R.id.delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cartview {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.carttadap, parent, false)

        return Cartview(view)
    }

    override fun getItemCount(): Int {

        return mapfinal.size
    }

    override fun onBindViewHolder(holder: Cartview, position: Int) {

        val product = mapfinal[position]

        holder.delete.visibility = View.VISIBLE
        holder.name.text = "name:" + product["name"].toString()
        holder.amount.text = "Amount" + product["password"].toString()

        Glide.with(holder.itemView.context).load(product["dowlondurl"].toString())
            .into(holder.iamge)

        holder.delete.setOnClickListener {

            mapfinal.removeAt(position)
            notifyDataSetChanged()


            var databasee: DatabaseReference =
                Firebase.database.reference.child("/message/user/${product["key"]}/like")
            databasee.setValue(0)

        }

    }

}
