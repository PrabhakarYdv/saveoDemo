package com.tejeet.saveodemoapp.dataModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponseDTO(

	@field:SerializedName("score")
	val score: Any? = null,

	@field:SerializedName("show")
	val show: ShowDTO? = null
) : Serializable