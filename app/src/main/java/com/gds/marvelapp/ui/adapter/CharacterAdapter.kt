package com.gds.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gds.marvelapp.R
import com.gds.marvelapp.data.model.character.CharacterModel
import com.gds.marvelapp.databinding.ItemCharacterBinding
import com.gds.marvelapp.ui.adapter.CharacterAdapter.CharacterViewHolder
import com.gds.marvelapp.util.limitDescription
import com.gds.marvelapp.util.loadImage

class CharacterAdapter : RecyclerView.Adapter<CharacterViewHolder>() {

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<CharacterModel>() {

        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description &&
                    oldItem.thumbnail.path == newItem.thumbnail.path &&
                    oldItem.thumbnail.extension == newItem.thumbnail.extension
        }

    }
    private val differ = AsyncListDiffer(this, differCallback)

    var characters: List<CharacterModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun getItemCount(): Int = characters.size


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.binding.apply {
            tvNameCharacter.text = character.name
            if (character.description == ""){
                tvDescriptionCharacter.text =
                    holder.itemView.context.getString(R.string.text_description_empty)
            }else{
                tvDescriptionCharacter.text = character.description.limitDescription(100)
            }
            loadImage(imgCharacter,character.thumbnail.path,character.thumbnail.extension)
        }
        holder.itemView.setOnClickListener {
            onItemClickListner?.let {
                it(character)
            }
        }
    }
    private var onItemClickListner : ((CharacterModel) -> Unit)? = null

    fun setOnClickListner(listner : (CharacterModel) -> Unit){
        onItemClickListner = listner
    }

    fun getCharcterPosition(position : Int): CharacterModel {
        return characters[position]
    }
}