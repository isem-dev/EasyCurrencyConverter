package dev.isem.easycurrencyconverter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dev.isem.easycurrencyconverter.databinding.FragmentRatesBinding

class RatesFragment : Fragment() {

    private val ratesViewModel: RatesViewModel by activityViewModels()

    private lateinit var adapterNotify: RatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRatesBinding.inflate(inflater)

        val adapter = RatesAdapter(RatesAdapter.RateItemListener {
                rate -> ratesViewModel.swapRateHeader(rate, adapterNotify)
        }, ratesViewModel)

        adapterNotify = adapter

        binding.apply {
            lifecycleOwner = this@RatesFragment
            viewModel = ratesViewModel
            ratesList.adapter = adapter
        }

        ratesViewModel.ratesList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        return binding.root
    }
}
