package com.gds.marvelapp.ui.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gds.marvelapp.databinding.FragmentSearchCharacterBinding
import com.gds.marvelapp.ui.fragment.base.BaseFragment

class SearchCharacterFragment : BaseFragment<FragmentSearchCharacterBinding,SearchCharacterViewModel>() {
    override val viewModel: SearchCharacterViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?): FragmentSearchCharacterBinding =
        FragmentSearchCharacterBinding.inflate(inflater,container,false)
}