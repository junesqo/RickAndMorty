package kg.junesqo.rickandmorty.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.junesqo.rickandmorty.databinding.ItemCharacterBinding
import kg.junesqo.rickandmorty.data.characters.remote.remote_models.Character

class CharactersPagingAdapter(private val onclickListener: OnclickListener) :
    PagingDataAdapter<Character, CharactersPagingAdapter.CharactersViewHolder>(CharactersDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersPagingAdapter.CharactersViewHolder {
        return CharactersViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharactersPagingAdapter.CharactersViewHolder, position: Int) {
        val character = getItem(position)
        holder.itemView.setOnClickListener {
            onclickListener.onClick(character!!.id)
        }
        holder.bind(character)
    }

    inner class CharactersViewHolder(
        private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character?) {
            binding.tvName.text = character?.name
            binding.tvStatus.text = character?.status
            binding.tvGender.text = character?.gender
            Glide.with(binding.root.context)
                .load(character?.image)
                .into(binding.ivCharacter)
        }
    }

    class OnclickListener(val clickListener: (characterId: Int) -> Unit) {
        fun onClick(characterId: Int) =
            clickListener(characterId)
    }

    object CharactersDiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Character,
            newItem: Character
        ): Boolean {
            return oldItem == newItem
        }
    }
}