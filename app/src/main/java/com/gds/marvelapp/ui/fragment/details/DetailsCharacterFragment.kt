package com.gds.marvelapp.ui.fragment.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.gds.marvelapp.databinding.FragmentDetailsCharacterBinding
import com.gds.marvelapp.ui.fragment.base.BaseFragment

class DetailsCharacterFragment : BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {
    override val viewModel: DetailsCharacterViewModel by viewModels()

    override fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater,container,false)
}