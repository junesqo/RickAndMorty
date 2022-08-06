package kg.junesqo.rickandmorty.presentation.fragments.character_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import kg.junesqo.rickandmorty.R
import kg.junesqo.rickandmorty.databinding.FragmentCharacterDetailsBinding

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

}