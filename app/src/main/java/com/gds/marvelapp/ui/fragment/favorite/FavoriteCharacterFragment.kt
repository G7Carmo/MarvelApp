package com.gds.marvelapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gds.marvelapp.databinding.FragmentFavoriteCharacterBinding
import com.gds.marvelapp.ui.fragment.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class FavoriteCharacterFragment : BaseFragment<FragmentFavoriteCharacterBinding, FavoriteCharacterViewModel>() {
    override val viewModel: FavoriteCharacterViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?): FragmentFavoriteCharacterBinding =
        FragmentFavoriteCharacterBinding.inflate(inflater,container,false)

    override fun onInject(savedInstanceState: Bundle?) {
    }
}