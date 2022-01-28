package com.gds.marvelapp.ui.fragment.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gds.marvelapp.R
import com.gds.marvelapp.databinding.FragmentSearchCharacterBinding
import com.gds.marvelapp.ui.adapter.CharacterAdapter
import com.gds.marvelapp.ui.fragment.base.BaseFragment
import com.gds.marvelapp.ui.state.ResourceState
import com.gds.marvelapp.util.Constantes.DEFAULT_QUERY
import com.gds.marvelapp.util.Constantes.LAST_SEARCH_QUERY
import com.gds.marvelapp.util.hide
import com.gds.marvelapp.util.show
import com.gds.marvelapp.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint

class SearchCharacterFragment :
    BaseFragment<FragmentSearchCharacterBinding, SearchCharacterViewModel>() {
    override val viewModel: SearchCharacterViewModel by viewModels()
    private val characterAdapter by lazy { CharacterAdapter() }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCharacterBinding =
        FragmentSearchCharacterBinding.inflate(inflater, container, false)

    override fun onInject(savedInstanceState: Bundle?) {
        setupRecyclerVIew()
        clickAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        searchInit(query)
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.searchCharacter.collect { result ->
            when (result) {
                is ResourceState.Success -> {
                    binding.progressbarSearch.hide()
                    result.data?.let {
                        characterAdapter.characters = it.data.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.progressbarSearch.hide()
                    result.message?.let {message->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("SearchCharacterFragment").e("Error -> ${message}")
                    }
                }
                is ResourceState.Loanding -> {
                    binding.progressbarSearch.show()
                }

                else -> {}
            }
        }
    }

    private fun searchInit(query: String) = with(binding) {
        edSearchCharacter.setText(query)
        edSearchCharacter.setOnEditorActionListener { _, actiondId, _ ->
            if (actiondId == EditorInfo.IME_ACTION_GO) {
                updateCharacterList()
                true
            } else {
                false
            }
        }
        edSearchCharacter.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateCharacterList()
                true
            } else {
                false
            }
        }

    }

    private fun updateCharacterList() = with(binding) {
        edSearchCharacter.editableText.trim().let {
            if (it.isNotEmpty()) {
                searchQuery(it.toString())
            }
        }
    }

    private fun searchQuery(query: String) {
        viewModel.fetch(query)
    }

    private fun clickAdapter() {
        characterAdapter.setOnClickListner { characterModel ->
            val action =
                SearchCharacterFragmentDirections.actionSearchCharacterFragmentToDetailsCharacterFragment(
                    characterModel
                )
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerVIew() = with(binding) {
        rvSearchCharacter.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            LAST_SEARCH_QUERY,
            binding.edSearchCharacter.editableText.trim().toString()
        )
    }
}