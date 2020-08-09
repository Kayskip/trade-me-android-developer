package nz.co.trademe.wrapper.service

import nz.co.trademe.wrapper.dto.ClosingSoonListings
import retrofit2.Call
import retrofit2.http.GET

interface ListingService {

    @GET("v1/Listings/closing.json")
    fun retrieveClosingSoonListings(): Call<ClosingSoonListings>

}
