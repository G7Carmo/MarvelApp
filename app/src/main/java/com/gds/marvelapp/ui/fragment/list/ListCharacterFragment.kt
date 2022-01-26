package com.gds.marvelapp.ui.fragment.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gds.marvelapp.databinding.FragmentListCharacterBinding
import com.gds.marvelapp.ui.fragment.base.BaseFragment

class ListCharacterFragment : BaseFragment<FragmentListCharacterBinding,ListCharacterViewModel>() {
    override val viewModel: ListCharacterViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?): FragmentListCharacterBinding =
        FragmentListCharacterBinding.inflate(inflater,container,false)


}