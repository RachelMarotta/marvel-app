package com.rachelmarotta.marvelapp.ui.view

import com.rachelmarotta.marvelapp.ui.adapter.CharacterAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rachelmarotta.marvelapp.data.remote.ApiService
import com.rachelmarotta.marvelapp.data.remote.MarvelService
import com.rachelmarotta.marvelapp.data.repository.CharacterRepositoryImpl
import com.rachelmarotta.marvelapp.databinding.FragmentCharacterListBinding
import com.rachelmarotta.marvelapp.domain.usecase.GetCharactersUseCase
import com.rachelmarotta.marvelapp.ui.viewmodel.CharacterViewModel
import com.rachelmarotta.marvelapp.ui.viewmodel.CharacterViewModelFactory

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
    private val adapter = CharacterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            // Handle error
        }

        viewModel.fetchCharacters(20, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
