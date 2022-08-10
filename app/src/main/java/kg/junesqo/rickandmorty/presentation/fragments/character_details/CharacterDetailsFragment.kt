package kg.junesqo.rickandmorty.presentation.fragments.character_details

import android.os.Bundle
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
import kg.junesqo.rickandmorty.util.NetworkStatus
import kg.junesqo.rickandmorty.util.NetworkStatusHelper
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    lateinit var binding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by viewModels()

    private var connectionStatus: Boolean = false
    private var characterId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterId = arguments?.getInt("character_id")

        lifecycleScope.launch {
            checkConnection()
            characterId?.let {
                viewModel.getSingleCharacter(it, connectionStatus).collect { character ->
                    Glide.with(binding.root.context)
                        .load(character.image)
                        .into(binding.ivCharacter)
                    binding.tvCharacter.text = character.name
                    binding.tvStatus.text = character.status
                    binding.tvGender.text = character.gender
                    binding.tvOrigin.text = "Origin: " + character.origin.name
                    binding.tvLocation.text = "Location: " + character.location.name
                    binding.tvSpecies.text = "Species: " + character.species
                }
            }
        }
    }

    private fun checkConnection() {
        NetworkStatusHelper(requireContext()).observe(viewLifecycleOwner) {
            connectionStatus = it == NetworkStatus.Available
            if (it == NetworkStatus.Available){
                connectionStatus = true
            } else {
                connectionStatus = false
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
            }
        }
    }

}