package dev.isem.easycurrencyconverter.data

data class Rates(
    val baseCurrency: String,
    val rates: Map<String, Double>
)

data class Rate(
    val currencyId: Long,
    val currencyCode: String,
    val currencyValue: Double
)

data class RateHeader(
    val currencyId: Long,
    var currencyCode: String,
    var currencyInputValue: Double?
)
