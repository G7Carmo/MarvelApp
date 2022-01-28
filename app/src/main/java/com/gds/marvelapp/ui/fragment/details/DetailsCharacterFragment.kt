package com.gds.marvelapp.ui.fragment.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gds.marvelapp.R
import com.gds.marvelapp.data.model.character.CharacterModel
import com.gds.marvelapp.databinding.FragmentDetailsCharacterBinding
import com.gds.marvelapp.ui.adapter.ComicAdapter
import com.gds.marvelapp.ui.fragment.base.BaseFragment
import com.gds.marvelapp.ui.state.ResourceState
import com.gds.marvelapp.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsCharacterFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {
    override val viewModel: DetailsCharacterViewModel by viewModels()
    private val comicAdapter by lazy { ComicAdapter() }
    private lateinit var characterModel : CharacterModel
    private val args : DetailsCharacterFragmentArgs by navArgs()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

    override fun onInject(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        characterModel = args.characterId
        viewModel.fetch(characterModel.id)
        setupRecyclerView()
        onLoadCharacter(characterModel)
        collectObserve()
        descriptionCharacter()
    }

    private fun descriptionCharacter() {
        binding.tvDescriptionCharacterDetails.setOnClickListener {
            onShowDialog(characterModel)
        }
    }

    private fun onShowDialog(characterModel: CharacterModel) {
        dialog(characterModel.name,characterModel.description)
    }

    private fun collectObserve() = lifecycleScope.launch {
        viewModel.details.collect { result->
            when(result){
                is ResourceState.Success->{
                    binding.progressBarDetail.hide()
                    result.data?.let {values->
                        if (values.data.results.count() > 0){
                            comicAdapter.comics = values.data.results.toList()
                        }else{
                            toast(getString(R.string.empty_list_comics))
                        }
                    }
                }
                is ResourceState.Error->{
                    binding.progressBarDetail.hide()
                    result.message?.let { message->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("DatailsCharacterFragment").e("Error-> ${message}")
                    }
                }
                is ResourceState.Loanding->{
                    binding.progressBarDetail.show()
                }
                else->{}
            }
        }
    }

    private fun onLoadCharacter(characterModel: CharacterModel) = with(binding) {
        tvNameCharacterDetails.text = characterModel.name
        if (characterModel.description.isEmpty()){
            tvDescriptionCharacterDetails.text = requireContext().getString(R.string.text_description_empty)
        }else{
            tvDescriptionCharacterDetails.text = characterModel.description.limitDescription(100)
        }
        Glide.with(requireContext())
            .load("${characterModel.thumbnail.path+"."+characterModel.thumbnail.extension}")
            .into(imgCharacterDetails)
    }

    private fun setupRecyclerView() = with(binding) {
        rvComics.apply {
            adapter = comicAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite->{
                viewModel.insert(characterModel)
                toast(getString(R.string.saved_successfully))
            }
        }


        return super.onOptionsItemSelected(item)
    }
}