package com.example.listatelefonica.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.listatelefonica.R

class ContactViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.imageContact)
    val textName: TextView = view.findViewById(R.id.textContactName)
}