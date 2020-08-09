package nz.co.trademe.wrapper.service

import nz.co.trademe.wrapper.dto.SingleListItem
import retrofit2.Call
import retrofit2.http.*

/**
 * Similar to the Listing service, this interface 'should' get the dynamic string URL passed
 * into the following function. This does not work and returns an auth error response even
 * though the request is correct :(
 */
interface SingleListService {
    @GET
    fun retrieveSingleListing(@Url id: String): Call<SingleListItem>
}
