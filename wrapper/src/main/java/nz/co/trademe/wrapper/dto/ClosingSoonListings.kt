package nz.co.trademe.wrapper.dto

import com.squareup.moshi.Json

data class ClosingSoonListings(
    @Json(name = "TotalCount") val totalCount: Int,
    @Json(name = "List") val list: List<Object>
)
