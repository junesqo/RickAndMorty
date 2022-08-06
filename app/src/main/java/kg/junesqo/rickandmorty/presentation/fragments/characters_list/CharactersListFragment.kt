package kg.junesqo.rickandmorty.presentation.fragments.characters_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.junesqo.rickandmorty.R
import kg.junesqo.rickandmorty.databinding.FragmentCharactersListBinding
import kg.junesqo.rickandmorty.presentation.CharactersPagingAdapter
import kg.junesqo.rickandmorty.presentation.CharactersViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    lateinit var binding: FragmentCharactersListBinding
    private val viewModel: CharactersViewModel by viewModels()
    private val adapter: CharactersPagingAdapter by lazy {
        CharactersPagingAdapter(
            CharactersPagingAdapter.OnclickListener { characterId ->
                findNavController().navigate(
                    R.id.action_charactersListFragment_to_characterDetailsFragment, Bundle().apply {
                        putSerializable("character_id", characterId)
                    }
                )
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_characters_list, container, false)
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.adapter = adapter
        lifecycleScope.launch {
            viewModel.characters.collect(
                adapter::submitData
            )
        }
    }

}