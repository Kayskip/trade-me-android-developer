package nz.co.trademe.wrapper.dto

import com.squareup.moshi.Json

data class SingleListItem(
        @Json(name = "ListingId") val id: Int
)
