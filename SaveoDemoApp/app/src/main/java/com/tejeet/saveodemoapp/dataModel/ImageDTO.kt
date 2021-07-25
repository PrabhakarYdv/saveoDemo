package com.tejeet.saveodemoapp.dataModel

import java.io.Serializable

data class ImageDTO(
	val original: String? = null,
	val medium: String? = null
) : Serializable