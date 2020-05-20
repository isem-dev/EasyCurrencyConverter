package dev.isem.easycurrencyconverter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import dev.isem.easycurrencyconverter.ui.RatesApiStatus

@BindingAdapter("ratesApiStatus")
fun bindStatus(statusImageView: ImageView, status: RatesApiStatus?) {
    when (status) {
        RatesApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        RatesApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        RatesApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("currencyValueText")
fun TextView.setCurrencyValue(currencyValue: Double) {
    currencyValue.let {
        text = if (currencyValue == 0.0) { "" } else { currencyValue.format2F() }
    }
}

@BindingAdapter("currencyNameText")
fun TextView.setCurrencyName(currencyCode: String) {
    currencyCode.let {
        text = when (currencyCode) {
            CurrencyCodes.AUD.name -> resources.getString(R.string.aud)
            CurrencyCodes.BGN.name -> resources.getString(R.string.bgn)
            CurrencyCodes.BRL.name -> resources.getString(R.string.brl)
            CurrencyCodes.CAD.name -> resources.getString(R.string.cad)
            CurrencyCodes.CHF.name -> resources.getString(R.string.chf)
            CurrencyCodes.CNY.name -> resources.getString(R.string.cny)
            CurrencyCodes.CZK.name -> resources.getString(R.string.czk)
            CurrencyCodes.DKK.name -> resources.getString(R.string.dkk)
            CurrencyCodes.EUR.name -> resources.getString(R.string.eur)
            CurrencyCodes.GBP.name -> resources.getString(R.string.gbp)
            CurrencyCodes.HKD.name -> resources.getString(R.string.hkd)
            CurrencyCodes.HRK.name -> resources.getString(R.string.hrk)
            CurrencyCodes.HUF.name -> resources.getString(R.string.huf)
            CurrencyCodes.IDR.name -> resources.getString(R.string.idr)
            CurrencyCodes.ILS.name -> resources.getString(R.string.ils)
            CurrencyCodes.INR.name -> resources.getString(R.string.inr)
            CurrencyCodes.ISK.name -> resources.getString(R.string.isk)
            CurrencyCodes.JPY.name -> resources.getString(R.string.jpy)
            CurrencyCodes.KRW.name -> resources.getString(R.string.krw)
            CurrencyCodes.MXN.name -> resources.getString(R.string.mxn)
            CurrencyCodes.MYR.name -> resources.getString(R.string.myr)
            CurrencyCodes.NOK.name -> resources.getString(R.string.nok)
            CurrencyCodes.NZD.name -> resources.getString(R.string.nzd)
            CurrencyCodes.PHP.name -> resources.getString(R.string.php)
            CurrencyCodes.PLN.name -> resources.getString(R.string.pln)
            CurrencyCodes.RON.name -> resources.getString(R.string.ron)
            CurrencyCodes.RUB.name -> resources.getString(R.string.rub)
            CurrencyCodes.SEK.name -> resources.getString(R.string.sek)
            CurrencyCodes.SGD.name -> resources.getString(R.string.sgd)
            CurrencyCodes.THB.name -> resources.getString(R.string.thb)
            CurrencyCodes.USD.name -> resources.getString(R.string.usd)
            CurrencyCodes.ZAR.name -> resources.getString(R.string.zar)
            else ->  resources.getString(R.string.currency_name_label)
        }
    }
}

@BindingAdapter("currencyImage")
fun ImageView.setCurrencyIcon(currencyCode: String) {
    setImageResource(
        when (currencyCode) {
            CurrencyCodes.AUD.name -> R.drawable.ic_australia
            CurrencyCodes.BGN.name -> R.drawable.ic_bulgaria
            CurrencyCodes.BRL.name -> R.drawable.ic_brazil
            CurrencyCodes.CAD.name -> R.drawable.ic_canada
            CurrencyCodes.CHF.name -> R.drawable.ic_switzerland
            CurrencyCodes.CNY.name -> R.drawable.ic_china
            CurrencyCodes.CZK.name -> R.drawable.ic_czech_republic
            CurrencyCodes.DKK.name -> R.drawable.ic_denmark
            CurrencyCodes.EUR.name -> R.drawable.ic_european_union
            CurrencyCodes.GBP.name -> R.drawable.ic_united_kingdom
            CurrencyCodes.HKD.name -> R.drawable.ic_hong_kong
            CurrencyCodes.HRK.name -> R.drawable.ic_croatia
            CurrencyCodes.HUF.name -> R.drawable.ic_hungary
            CurrencyCodes.IDR.name -> R.drawable.ic_indonesia
            CurrencyCodes.ILS.name -> R.drawable.ic_israel
            CurrencyCodes.INR.name -> R.drawable.ic_bhutan
            CurrencyCodes.ISK.name -> R.drawable.ic_iceland
            CurrencyCodes.JPY.name -> R.drawable.ic_japan
            CurrencyCodes.KRW.name -> R.drawable.ic_south_korea
            CurrencyCodes.MXN.name -> R.drawable.ic_mexico
            CurrencyCodes.MYR.name -> R.drawable.ic_malaysia
            CurrencyCodes.NOK.name -> R.drawable.ic_norway
            CurrencyCodes.NZD.name -> R.drawable.ic_new_zealand
            CurrencyCodes.PHP.name -> R.drawable.ic_philippines
            CurrencyCodes.PLN.name -> R.drawable.ic_poland
            CurrencyCodes.RON.name -> R.drawable.ic_romania
            CurrencyCodes.RUB.name -> R.drawable.ic_russia
            CurrencyCodes.SEK.name -> R.drawable.ic_sweden
            CurrencyCodes.SGD.name -> R.drawable.ic_singapore
            CurrencyCodes.THB.name -> R.drawable.ic_thailand
            CurrencyCodes.USD.name -> R.drawable.ic_united_states_of_america
            CurrencyCodes.ZAR.name -> R.drawable.ic_south_africa
            else -> R.drawable.ic_launcher_foreground
        }
    )

}
