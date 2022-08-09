package kg.junesqo.rickandmorty.presentation.fragments.character_details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kg.junesqo.rickandmorty.databinding.FragmentCharacterDetailsBinding
import kg.junesqo.rickandmorty.presentation.fragments.characters_list.CharactersViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()
    private var characterId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterId = arguments?.getInt("character_id")

        lifecycleScope.launch {
            characterId?.let {
                viewModel.getSingleCharacter(it).collect { response ->
                    if (response.isSuccessful){
                        Glide.with(binding.root.context)
                            .load(response.body()?.image)
                            .into(binding.ivCharacter)
                        binding.tvCharacter.text = response.body()?.name
                        binding.tvStatus.text = response.body()?.status
                        binding.tvGender.text = response.body()?.gender
                    }
                }
            }
        }


    }

}