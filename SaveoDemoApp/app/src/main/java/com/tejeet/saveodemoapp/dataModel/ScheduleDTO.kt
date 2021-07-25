package com.tejeet.saveodemoapp.dataModel

import java.io.Serializable

data class ScheduleDTO(
	val days: List<String?>? = null,
	val time: String? = null
)