package com.tejeet.saveodemoapp.dataModel

import java.io.Serializable

data class LinksDTO(
	val self: SelfDTO? = null,
	val previousepisode: PreviousepisodeDTO? = null
) : Serializable