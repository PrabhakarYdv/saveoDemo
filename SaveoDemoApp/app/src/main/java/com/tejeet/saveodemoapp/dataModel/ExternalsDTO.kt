package com.tejeet.saveodemoapp.dataModel

import java.io.Serializable

data class ExternalsDTO(
	val thetvdb: Int? = null,
	val imdb: String? = null,
	val tvrage: Int? = null
) : Serializable