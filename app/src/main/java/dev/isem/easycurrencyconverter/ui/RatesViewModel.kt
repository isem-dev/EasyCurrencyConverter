package dev.isem.easycurrencyconverter.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.isem.easycurrencyconverter.CurrencyCodes
import dev.isem.easycurrencyconverter.data.Rate
import dev.isem.easycurrencyconverter.data.RateHeader
import dev.isem.easycurrencyconverter.data.Rates
import dev.isem.easycurrencyconverter.data.RatesApi
import kotlinx.coroutines.*

enum class RatesApiStatus { LOADING, ERROR, DONE }

class RatesViewModel : ViewModel(), Observable {

    private val _status = MutableLiveData<RatesApiStatus>()
    val status: LiveData<RatesApiStatus>
        get() = _status

    private var _ratesList = MutableLiveData<List<Rate>>()
    val ratesList: LiveData<List<Rate>>
        get() = _ratesList

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    @Bindable
    val currencyInputValue = MutableLiveData("100.0")

    val rateHeader =
        MutableLiveData(
            RateHeader(
                Long.MIN_VALUE,
                CurrencyCodes.EUR.name,
                currencyInputValue.value?.toDouble()
            )
        )

    fun swapRateHeader(rate: Rate, adapterNotify: RatesAdapter) {
        currencyInputValue.value = rate.currencyValue.toString()
        rateHeader.value?.currencyCode = rate.currencyCode

        adapterNotify.notifyDataSetChanged()
    }

    init {
        startCoroutineTimer { rateHeader.value?.currencyCode?.let { getRates(it) } }
    }

    private inline fun startCoroutineTimer(
        delayMillis: Long = 0,
        repeatMillis: Long = 1000,
        crossinline action: () -> Unit
    ) = GlobalScope.launch {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private fun getRates(baseCurrency: String) {
        coroutineScope.launch {
            val ratesResponse: Rates
            try {
                _status.value = RatesApiStatus.LOADING
                ratesResponse = RatesApi.retrofitService.getRatesAsync(baseCurrency)
                _ratesList.value = convertRatesToList(ratesResponse)
                _status.value = RatesApiStatus.DONE
            } catch (e: Exception) {
                _status.value = RatesApiStatus.ERROR
                _ratesList.value = ArrayList()
            }
        }
    }

    private fun convertRatesToList(rates: Rates): List<Rate> {
        val list = mutableListOf<Rate>()
        var id = 100L
        for ((k, v) in rates.rates) {
            id += 1
            if (currencyInputValue.value.equals("")) { currencyInputValue.value = "0" }
            list.add(Rate(id, k, v * (currencyInputValue.value?.toDouble() ?: 0.0)))
        }
        return list
    }

   override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
   }

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
            callbacks.add(callback)
    }

}
