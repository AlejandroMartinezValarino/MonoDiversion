package com.example.monodiversion.helper

import java.util.Locale

class Country {

    fun countriesList():List<Pair<String,String>>{
        val isoCountries = Locale.getISOCountries()
        val countries= emptyMap<String,String>().toMutableMap()
        for (code in isoCountries){
            val locale = Locale(Locale.getDefault().isO3Language, code)
            countries += Pair(code,locale.displayCountry)
        }
        return countries.toList()
    }
}