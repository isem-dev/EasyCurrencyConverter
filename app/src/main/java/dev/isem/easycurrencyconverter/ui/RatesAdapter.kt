package dev.isem.easycurrencyconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.isem.easycurrencyconverter.CurrencyCodes
import dev.isem.easycurrencyconverter.databinding.ListHeaderRateBinding
import dev.isem.easycurrencyconverter.databinding.ListItemRateBinding
import dev.isem.easycurrencyconverter.data.Rate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class RatesAdapter(private val clickListener: RateItemListener, private val viewModel: RatesViewModel) :
    ListAdapter<RatesAdapter.DataItem, RecyclerView.ViewHolder>(RatesDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(list: List<Rate>?) {
        adapterScope.launch {
            val rateHeader = Rate(Long.MIN_VALUE, CurrencyCodes.EUR.name, 0.0)
            val items = when(list) {
                null -> listOf(DataItem.Header(rateHeader))
                else -> listOf(DataItem.Header(rateHeader)) + list.map { DataItem.RateItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> ViewHolderHeader.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                val rateItem = getItem(position) as DataItem.RateItem
                holder.bind(clickListener, rateItem.rate)
            }
            is ViewHolderHeader -> { holder.bind(viewModel) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.RateItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    class ViewHolderHeader private constructor(val binding: ListHeaderRateBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: RatesViewModel) {
            binding.ratesViewModel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderHeader {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListHeaderRateBinding.inflate(layoutInflater)
                return ViewHolderHeader(binding)
            }
        }

    }

    class ViewHolder private constructor(val binding: ListItemRateBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: RateItemListener, item: Rate) {
            binding.rate = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemRateBinding.inflate(layoutInflater)
                return ViewHolder(binding)
            }
        }

    }

    class RateItemListener(val clickListener: (rate: Rate) -> Unit) {
        fun onClick(rate: Rate) {
            clickListener(rate)
        }
    }

    class RatesDiffCallback: DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }

    }

    sealed class DataItem {
        data class RateItem(val rate: Rate): DataItem() {
            override val id: Long = rate.currencyId
        }
        data class Header(val rate: Rate): DataItem() {
            override val id = rate.currencyId
        }
        abstract val id: Long
    }

}
