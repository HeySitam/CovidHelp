package com.sitamadex11.covidhelp

data class Response(
	val stateCode: StateCode? = null
)

data class Delta(
	val recovered: String? = null,
	val deceased: String? = null,
	val tested: String? = null,
	val vaccinated: String? = null,
	val confirmed: String? = null
)

data class StateCode(
	val dates: Dates? = null
)

data class Delta7(
	val recovered: String? = null,
	val deceased: String? = null,
	val tested: String? = null,
	val vaccinated: String? = null,
	val confirmed: String? = null
)

data class Total(
	val recovered: String? = null,
	val deceased: String? = null,
	val tested: String? = null,
	val vaccinated: String? = null,
	val confirmed: String? = null
)

data class YYYYMMDD(
	val total: Total? = null,
	val delta: Delta? = null,
	val delta7: Delta7? = null
)

data class Dates(
	val yYYYMMDD: YYYYMMDD? = null
)

