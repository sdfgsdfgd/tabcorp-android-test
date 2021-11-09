package kaan.tabcorp.domain.flights

import android.util.Log
import kaan.tabcorp.data.BFFApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpacexRepository @Inject constructor(private val bffApi: BFFApi) {
//    fun flow(): StateFlow<Result<Any>> {
//        return flow
//    }

    suspend fun getLaunches() = withContext(Dispatchers.IO) {

        try {
            Log.d("XXX", "getFlights() ....... [START]")

            val flightsResponse = bffApi.getLaunches()

            Log.d("XXX", "flightsResponse: ${flightsResponse.size}")
            Log.d("XXX", "getFlights() ....... [END]")
        } catch (e: Throwable) {
            Log.d("XXX", "ERROR: $e")
        }
    }
}







//
//@Singleton
//class TransactionRepository @Inject constructor(
//    private val bffApi: BFFApi
//) {
//    @Throws(DomainError::class)
//    suspend fun fetchTransaction(storeId: String, pumpNumber: Int) = withContext(Dispatchers.IO) {
//        try {
//            val fuelResponse = bffApi.getBasketFuel(storeId, PumpNumbersRequest(listOf(pumpNumber)))
//            when (fuelResponse.basketState) {
//                "NO_TRANSACTIONS" -> throw NoDataError()
//                "MULTIPLE_TRANSACTIONS" -> throw MultipleTransactionsError("App only supports single transaction")
//                "SINGLE_TRANSACTION" -> {
//                    // explicitly checking for this enum to enforce basketState directive first, only then check data itself
//                }
//                else -> throw NoDataError()
//            }
//            if (fuelResponse.basketResponse.transactionLineItems.isEmpty()) {
//                throw NoDataError()
//            }
//            val transaction = fuelResponse.basketResponse.transactionLineItems.first()
//            TransactionDetails(
//                refId = fuelResponse.basketResponse.ref,
//                basketId = fuelResponse.basketResponse.basketId,
//                itemId = transaction.itemId,
//                fuelTxnId = transaction.fuelTransaction.fuelTxnId,
//                fuelPumpNumber = transaction.fuelTransaction.pumpNo.toInt(), // unclear why this is returned as string but input is int so converting
//                fuelType = transaction.itemDesc,
//                costPerUnit = transaction.unitAmt,
//                totalCost = fuelResponse.basketResponse.basketTotalAmt,
//                qtyUnits = transaction.qty,
//            )
//        } catch (e: Throwable) {
//            throw ErrorHandler.mapError(e)
//        }
//    }
//
//    @Throws(DomainError::class)
//    suspend fun pay(storeId: String, paymentConfirmationRequest: PaymentConfirmationRequest) = withContext(Dispatchers.IO) {
//        try {
//            bffApi.pay(storeId, paymentConfirmationRequest)
//        } catch (e: Throwable) {
//            throw ErrorHandler.mapError(e)
//        }
//    }
//}
