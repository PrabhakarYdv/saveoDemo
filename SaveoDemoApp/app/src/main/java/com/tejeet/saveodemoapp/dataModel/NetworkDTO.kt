package com.tejeet.saveodemoapp.dataModel

import java.io.Serializable

data class NetworkDTO(
	val country: CountryDTO? = null,
	val name: String? = null,
	val id: Int? = null
) : Serializable