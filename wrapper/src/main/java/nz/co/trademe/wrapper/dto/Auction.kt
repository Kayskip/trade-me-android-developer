import org.json.JSONArray
import org.json.JSONObject

/**
 * Class for the auctions recieved on MainActivity
 */

data class Auction(
        var id: Int,
        var title: String,
        var pictureHref: String?,
        var photoUrls: JSONArray?,
        var startPrice: Double?,
        var buyNowPrice: Double?,
        var location: String?,
        var priceDisplay: String?,
        var positiveFeedback: Int?,
        var totalFeedbackCount: Int?,
        var additionalData: JSONObject?
)