package com.example.listatelefonica.adapter.listener

import com.example.listatelefonica.model.ContactModel

class ContactOnClickListener (val clickListener: (contact : ContactModel) -> Unit) {
    fun onClick(contact: ContactModel) = clickListener
}