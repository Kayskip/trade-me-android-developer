package nz.co.trademe.techtest

import Auction
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.auction_main.view.*
import nz.co.trademe.wrapper.TradeMeApi
import nz.co.trademe.wrapper.dto.ClosingSoonListings
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), Callback<ClosingSoonListings> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TradeMeApi.listingService.retrieveClosingSoonListings().enqueue(this)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
    }

    override fun onFailure(call: Call<ClosingSoonListings>, t: Throwable) {
        Toast.makeText(this, "Error loading closing soon listings.", Toast.LENGTH_SHORT).show()
    }

    /**
     * Originally I was using the gson library to parse the list as a jsonarray which,
     * then de-serialised into the Auction data class. However, the
     * json data can vary in element consistently. eg. "pictureHref" which may or may not exist.
     * This resulted in errors that could not be resolved easily using that library. I have decided to deconstruct
     * my own json array, adding Auctions manually and optionally checking values that may/may not exist.
     * This method is most likely more resource consuming/not as good practice.
     */

    override fun onResponse(call: Call<ClosingSoonListings>, response: Response<ClosingSoonListings>) {
        val body = response.body()

        if (body != null) {
            val array = JSONArray(body.list); // lets turn the list we retrieve into a json array to access elements easier
            val list: MutableList<Auction> = ArrayList() // this is an array of auctions, possessing the important information
            for(index in 0 until array.length()){
                list.add(
                        Auction(
                                array.getJSONObject(index).getInt("ListingId"),
                                array.getJSONObject(index).getString("Title"),
                                array.getJSONObject(index).optString("PictureHref"),
                                array.getJSONObject(index).optJSONArray("PhotoUrls"),
                                array.getJSONObject(index).optDouble("StartPrice"),
                                array.getJSONObject(index).optDouble("BuyNowPrice"),
                                array.getJSONObject(index).optString("Suburb"),
                                array.getJSONObject(index).optString("PriceDisplay"),
                                array.getJSONObject(index).optInt("PositiveReviewCount"),
                                array.getJSONObject(index).optInt("TotalReviewCount"),
                                array.getJSONObject(index).optJSONObject("AdditionalData")
                        )
                )
            }
            recyclerView_main.adapter = Adapter(list);
        }
    }


}

class CustomViewHolder(val view: View, var auction: String? = null, var id: Int? = null ): RecyclerView.ViewHolder(view){

    companion object{
        const val AUCTION_TITLE_KEY = "AUCTION_TITLE"
        const val AUCTION_ID = "AUCTION_ID"
    }

    /**
     * Give data to the other screen
     */
    init {
        view.setOnClickListener {
            val intent = Intent(view.context, AuctionDetailActivity::class.java);
            intent.putExtra(AUCTION_TITLE_KEY, auction)
            intent.putExtra(AUCTION_ID, id)
            view.context.startActivity(intent)
        }
    }
}

/**
 * Adapter used to display the list
 * for this application I am using a recycler view.apater which implements the following
 * functions. This is especially helpful for list like content like I am displaying
 */

private class Adapter(list: MutableList<Auction>): RecyclerView.Adapter<CustomViewHolder>() {

    private val listings = list;
    private var maxLines = 4;

    override fun getItemCount(): Int {
        return maxLines;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        val cellForRow = layoutInflater.inflate(R.layout.auction_main, parent, false);
        return CustomViewHolder(cellForRow);
    }

    /**
     * Basic information displaying from the auction objects we created earlier
     */
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val title = listings[position].title
        val id = listings[position].id
        val startPrice = "Start Price: $"+listings[position].startPrice;
        val buyNowPrice = "Buy Now Price: $"+listings[position].buyNowPrice;
        val location = "Location: "+listings[position].location;
        val image = listings[position]?.pictureHref;
        val imageView = holder.view.imageView;

        if(!image.isNullOrEmpty()){
            Picasso.with(holder.view.context).load(image).into(imageView);
        } else {
            // this should be a local file (fix later)
            Picasso.with(holder.view.context).load("https://previews.123rf.com/images/latkun/latkun1712/latkun171200130/92172856-empty-transparent-background-seamless-pattern.jpg").into(imageView);
        }
        holder.view.textView_auction_title?.text = title;
        holder.view.startPrice?.text = startPrice;
        holder.view.buyNow.text = buyNowPrice;
        holder.view.location.text = location;
        holder.auction = title
        holder.id = id
    }

}


