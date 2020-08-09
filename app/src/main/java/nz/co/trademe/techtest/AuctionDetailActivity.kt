package nz.co.trademe.techtest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.SingleListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Activity for when a user clicks on a listing
 */
class AuctionDetailActivity: AppCompatActivity(), Callback<SingleListItem>{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView((R.layout.activity_main))

        /**
         * For some reason I am not authorised to access an individual listing.
         * I have made sure to replicate the same format as provided, with the exception of
         * dynamic data. However, I still get an unauthorized response from the API even if I
         * use static or dynamic data to retrieve listing information. I am not sure why this doesn't work >:(
         */

        val auctionId = intent.getIntExtra(CustomViewHolder.AUCTION_ID,-1); // returns the listing id ot be used
        val auctionDetailUrl = "https://api.trademe.co.nz/v1/Listings/$auctionId.json" // the listing id URL
        TradeMeApi.singleListService.retrieveSingleListing(auctionDetailUrl).enqueue(this); // the api call, made sure its running on the same thread
        recyclerView_main.layoutManager = LinearLayoutManager(this);
        recyclerView_main.adapter = AuctionDetailAdapter();
        val title = intent.getStringExtra(CustomViewHolder.AUCTION_TITLE_KEY); // quick title set
        supportActionBar?.title = title;
    }

    override fun onFailure(call: Call<SingleListItem>, t: Throwable) {
        println(t)
    }

    override fun onResponse(call: Call<SingleListItem>, response: Response<SingleListItem>) {
        println(response)
    }

    /**
     * This class and its functions would be populated similar as the MainActivity if the
     * API response above worked.eg.
     * Photos (if available)
     * Title
     * Description
     * Seller's username, location and number of positive and negative feedback.
     * Supports portrait and landscape modes.
     */

    private class AuctionDetailAdapter: RecyclerView.Adapter<AuctionViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context);
            val customView = layoutInflater.inflate(R.layout.auction_details, parent, false);
            return AuctionViewHolder(customView);
        }

        override fun getItemCount(): Int {
            return 1;
        }

        override fun onBindViewHolder(holder: AuctionViewHolder, position: Int) {}
    }

    //Super call customView for auction details
    private class AuctionViewHolder(val customView: View): RecyclerView.ViewHolder(customView){}




}