package kaan.tabcorp.data

import retrofit2.http.GET

interface BFFApi {

    // region In app messaging
    @GET(ENDPOINT_LAUNCHES)
    suspend fun getFlights(): Any
    // endregion

    companion object {
        private const val ENDPOINT_LAUNCHES: String = "launches/next"
        private const val ENDPOINT_LAUNCH_NEXT: String = "launches/next"


    }
}









//        private const val BASKET_PAYMENT: String = "v1/basket/payment/{storeId}"


//    // region Remote Config
//    @GET(CONFIGS_ENDPOINT)
//    suspend fun getRemoteConfigurations(
//        @Query("appName") appName: String,
//        @Query("appVersion") appVersion: String,
//        @Query("platformName") platformName: String,
//        @Query("platformVersion") platformVersion: String,
//        @Query("appBuildType") appBuildType: String,
//        @Query("timezone") timezone: String
//    ): RemoteConfigResponse
//    // endregion
//
//    // region Offers
//    @GET(OFFER_ENDPOINT)
//    suspend fun getOfferList(): OffersResult
//    // endregion
//
//    // region Stores
//    @GET(STORES_ENDPOINT)
//    suspend fun getStore(
//        @Path("storeId") storeId: String,
//        @Query("nearByStoresDistance") nearByStoresDistance: String? = null,
//        @Query("porLatitude") porLatitude: Double? = null,
//        @Query("porLongitude") porLongitude: Double? = null
//    ): Store
//
//    @GET(STORE_SUGGESTIONS_ENDPOINT)
//    suspend fun getStoreSuggestions(
//        @Query("searchTerm") searchTerm: String,
//        @Query("limit") limit: Int
//    ): StoreSearchSuggestions
//
//    @GET(STORE_SEARCH_ENDPOINT)
//    suspend fun getStores(
//        @Query("brandIds") brandIds: List<Int>,
//        @Query("latitude") latitude: String,
//        @Query("longitude") longitude: String,
//        @Query("distance") distance: Double? = null,
//        @Query("numberOfStores") numberOfStores: Int? = null,
//        @Query("porLatitude") porLatitude: Double? = null,
//        @Query("porLongitude") porLongitude: Double? = null
//    ): StoreList
//    //endregion
//
//    // region Basket
//    @POST(BASKET_FUEL)
//    suspend fun getBasketFuel(
//        @Path("storeId") storeId: String,
//        @Body pumpRequest: PumpNumbersRequest,
//        @Tag authorization: AuthorizationType = AuthorizationType.COLES_ACCESS_TOKEN
//    ): BasketResponse
//
//    @POST(BASKET_PAYMENT)
//    suspend fun pay(
//        @Path("storeId") storeId: String,
//        @Body paymentConfirmationRequest: PaymentConfirmationRequest,
//        @Tag authorization: AuthorizationType = AuthorizationType.COLES_ACCESS_TOKEN
//    ): PayResponse
//    //endregion
//
//    // region Paydock
//    @GET(PAYDOCK_WIDGET)
//    suspend fun getPaydockWidget(
//        @Tag authorization: AuthorizationType = AuthorizationType.COLES_ACCESS_TOKEN
//    ): PaydockWidgetResponse
//    // endregion
