package kg.junesqo.rickandmorty.presentation.fragments.characters_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.junesqo.rickandmorty.R
import kg.junesqo.rickandmorty.databinding.FragmentCharactersListBinding
import kg.junesqo.rickandmorty.presentation.CharactersPagingAdapter
import kg.junesqo.rickandmorty.presentation.CharactersViewModel
import kg.junesqo.rickandmorty.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import kg.junesqo.rickandmorty.util.NetworkStatus
import kg.junesqo.rickandmorty.util.NetworkStatusHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    lateinit var binding: FragmentCharactersListBinding
    private val viewModel: CharactersViewModel by activityViewModels()
    private var job: Job? = null
    private var statusFilter: String? = null
    private var searchString: String? = null

    private val adapter: CharactersPagingAdapter by lazy {
        CharactersPagingAdapter(
            CharactersPagingAdapter.OnclickListener { characterId ->
                findNavController().navigate(
                    R.id.action_charactersListFragment_to_characterDetailsFragment, Bundle().apply {
                        putInt("character_id", characterId)
                    }
                )
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCharacters.adapter = adapter
        checkConnection()
        initCharacters()
        setupSearchView()
        showFilterDialog()
    }

    private fun checkConnection() {
        NetworkStatusHelper(requireContext()).observe(viewLifecycleOwner) {
            if (it != NetworkStatus.Available) {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun showFilterDialog() {
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(
                R.id.action_charactersListFragment_to_filterDialogFragment
            )
        }
        viewModel.selectedStatus.observe(viewLifecycleOwner, Observer {
            statusFilter = it.toString()
            Log.e("fragment", it)
            lifecycleScope.launch {
                viewModel.getCharacters(searchString, statusFilter).collect(
                    adapter::submitData
                )
            }
        })
    }

    private fun setupSearchView() {
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                if (editable.toString().isNotEmpty()) {
                    searchString = editable.toString()
                    viewModel.getCharacters(editable.toString(), statusFilter)
                        .collect(
                            adapter::submitData,
                        )
                    hideSoftKeyboard()
                }
                else if (editable.toString().isEmpty()) {
                    viewModel.getCharacters("", statusFilter).collect(
                        adapter::submitData
                    )
                }
            }
        }
    }

    private fun hideSoftKeyboard() {
        val view = requireActivity().currentFocus
        view?.let {
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    private fun initCharacters() {
        lifecycleScope.launch {
            viewModel.getCharacters(null, statusFilter).collect(
                adapter::submitData
            )
        }
    }
}