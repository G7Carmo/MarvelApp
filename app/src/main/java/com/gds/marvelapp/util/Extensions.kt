package com.gds.marvelapp.util

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gds.marvelapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.toast(messagem : String , time : Int = Toast.LENGTH_LONG){
    Toast.makeText(
        requireContext(),
        messagem,
        time
    ).show()
}
fun View.show(){
    visibility = View.VISIBLE
}
fun View.hide(){
    visibility = View.INVISIBLE
}


fun String.limitDescription(characters : Int) : String{
    if (this.length > characters){
        val firstCharacter = 0
        return "${this.substring(firstCharacter,characters)}..."
    }
    return this
}

fun Fragment.dialog(title: String,messagem: String){
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(title)
        .setMessage(messagem)
        .setNegativeButton(getString(R.string.close_dialog)){ dialog, _->
            dialog.dismiss()
        }
        .show()
}
fun loadImage(
    imagemView : ImageView,
    path : String,
    extension : String
){
    Glide.with(imagemView.context)
        .load("$path.$extension")
        .into(imagemView)
}