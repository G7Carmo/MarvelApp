package com.gds.marvelapp.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

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