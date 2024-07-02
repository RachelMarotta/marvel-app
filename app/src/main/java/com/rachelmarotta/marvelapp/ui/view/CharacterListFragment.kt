package com.rachelmarotta.marvelapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rachelmarotta.marvelapp.data.remote.ApiService
import com.rachelmarotta.marvelapp.data.remote.MarvelService
import com.rachelmarotta.marvelapp.data.repository.CharacterRepositoryImpl
import com.rachelmarotta.marvelapp.databinding.FragmentCharacterListBinding
import com.rachelmarotta.marvelapp.ui.adapter.CharacterAdapter
import com.rachelmarotta.marvelapp.ui.viewmodel.CharacterViewModel
import com.rachelmarotta.marvelapp.ui.viewmodel.CharacterViewModelFactory
import com.rachelmarotta.marvelapp.domain.usecase.GetCharactersUseCase

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            GetCharactersUseCase(
                CharacterRepositoryImpl(
                    ApiService.retrofit.create(MarvelService::class.java)
                )
            )
        )
    }

    private lateinit var adapter: CharacterAdapter
    private var offset = 0
    private var limit = 20

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        fetchCharacters()
    }

    private fun setupRecyclerView() {
        adapter = CharacterAdapter(viewModel)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == adapter.itemCount - 1 && !viewModel.isLoading()) {
                    fetchCharacters()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.characters.observe(viewLifecycleOwner, Observer { characters ->
            adapter.submitList(characters, viewModel.isLoading())
        })
    }

    private fun fetchCharacters() {
        viewModel.fetchCharacters(offset, limit)
        offset += limit
        adapter.addLoadingFooter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
