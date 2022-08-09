package kg.junesqo.rickandmorty.presentation.fragments.characters_list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kg.junesqo.rickandmorty.R
import kg.junesqo.rickandmorty.databinding.FragmentCharactersListBinding
import kg.junesqo.rickandmorty.databinding.FragmentFilterDialogBinding

@AndroidEntryPoint
class FilterDialogFragment: DialogFragment() {

    private val viewModel: CharactersViewModel by activityViewModels()
    lateinit var binding: FragmentFilterDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val radioButtonText = when (binding.radioGroupStatus.checkedRadioButtonId) {
                R.id.radio_btn_alive -> binding.radioBtnAlive.text
                R.id.radio_btn_dead -> binding.radioBtnDead.text
                R.id.radio_btn_unknown -> binding.radioBtnUnknown.text
                else -> ""
            }
            viewModel.select(radioButtonText.toString())
            Log.e("Filter dialog", radioButtonText.toString())
            setSharedPref(radioButtonText.toString())
//            dismiss()
            findNavController().navigateUp()
        }
    }

    private fun setSharedPref(statusString: String){
        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        prefs.edit().putString("STATUS", statusString).apply();
    }

}