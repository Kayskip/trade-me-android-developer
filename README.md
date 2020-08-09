# Android Test Application Report

A short summary of the application.

## Things that work

A user can browse the closing soon listings provided by the API. This can be significantly improved by using proper serialization methods, rather than parsing each individual JSONObject field. Originally I was using the GSON library to parse the list as a JSONArray which, then de-serialised into the Auction data class. However, the json data can vary in element consistently. eg. "pictureHref" may or may not exist. This resulted in errors that could not be resolved easily using that library. I have decided to deconstruct my own json array, adding Auctions manually and optionally checking values that may/may not exist. This method is most likely more resource consuming and unsafe than the other method.

These listing are displayed with a title, start price, buy now price, image and location of a given listing. It also supports portrait and landscape modes.


## Things that don't work
Unfortunately, I was not able to grab information from the Listing API call once a user has clicked on a Auction. This was due to an "unauthorized" response from the API. I have created my own SingleListItem service which uses retrofit, similar to the provided skeleton code. Even though I was passing through correct GET() url data used by the retrofit library, I kept receiving this error. I had no problem with the originally listing call, but once I started adding dynamic data, I started noticing response issues. 
```
val auctionId = intent.getIntExtra(CustomViewHolder.AUCTION_ID,-1); 
val auctionDetailUrl = "https://api.trademe.co.nz/v1/Listings/$auctionId.json" 
TradeMeApi.singleListService.retrieveSingleListing(auctionDetailUrl).enqueue(this); 

Response from the API: {
protocol=http/1.1, 
code=401,
message=Unauthorized, 
url=https://api.trademe.co.nz/v1/Listings/2147483647.json
}
```
### Extra
A view more button needs to be implemented properly by increasing the MainActivityAdapters item count.
Categorization of listings would help improve usability, eg: lowest price.
