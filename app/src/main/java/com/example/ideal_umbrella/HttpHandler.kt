package com.example.ideal_umbrella

import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.MealTypeMenu.MealType
import com.example.ideal_umbrella.Order.Order
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Trieda starajuca sa o komunikaciu so serverom
 * @author Adam Pažičan
 */
object HttpHandler {
    private const val PREFIX = "https://idealumbrella.herokuapp.com"
    private const val UPDATE_DB = "$PREFIX/update-db"
    private const val VERIFY_USER = "$PREFIX/verify-user"
    private const val STORE_ORDER = "$PREFIX/store-order"
    private const val GET_ALL_ORDERS = "$PREFIX/get-all-orders"
    private const val ORDER_FINISHED = "$PREFIX/order-finished"

    /**
     * Na novom vlakne poziada server o vsetky jedla. Po spracovani dat do listu sa zavola [callback] s parametrom mealArray predstavujucim list jedal a success predstavujuci uspesnost poziadavky.
     */
    fun getAllMeals(callback: (mealArray: ArrayList<Meal>?, success: Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(UPDATE_DB)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream: InputStream = conn.inputStream
                val reader = InputStreamReader(inputStream)

                val result = JSONArray(reader.readText())

                val mealArray = ArrayList<Meal>()
                for (i in 0 until result.length()) {
                    val item = result.getJSONObject(i)
                    val meal = Meal(item["id"] as Int, item["meal_name"] as String, MealType.fromInt(item["meal_type"] as Int), item["price"] as Int)
                    mealArray.add(meal)
                }

                reader.close()
                callback(mealArray, true)
            }
            catch (e: Exception) {
                callback(null, false)
            }

        }.start()
    }

    /**
     * Na novom vlakne poziada server o validaciu pouzivatelskych udajov
     * @param email predstavuje email pouzivatela
     * @param password predstavuje heslo pouzivatela
     * @param callback predstavuje callback, ktory sa zavola po ukonceni poziadavky
     */
    fun verifyUser(email: String, password: String, callback: (success: Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(VERIFY_USER)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

                val user = JSONObject()
                user.put("email", email)
                user.put("password", password)

                val outputStream: OutputStream = conn.outputStream
                outputStream.write(user.toString().toByteArray())
                outputStream.close()

                val inputStream: InputStream = conn.inputStream
                val reader = InputStreamReader(inputStream)

                val result = JSONObject(reader.readText())

                reader.close()
                callback(result["success"] as Boolean)
            }
            catch (e: Exception) {
                callback(false)
            }

        }.start()
    }

    /**
     * Na novom vlakne poziada server o evidenciu objednavky
     * @param order predstavuje objednavku
     * @param callback predstavuje callback, ktory sa zavola po ukonceni poziadavky
     */
    fun storeOrder(order: Order, callback: (success: Boolean, id: Int) -> Unit) {
        Thread {
            try {
                val url = URL(STORE_ORDER)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

                val jOrder = JSONObject()
                jOrder.put("tableNumber", order.tableNumber)
                jOrder.put("sumTotal", order.sumTotal)
                jOrder.put("finished", order.finished)
                val meals = JSONArray()
                order.meals.forEach {
                    val meal = JSONObject()
                    meal.put("id", it.id)
                    meal.put("mealName", it.mealName)
                    meal.put("price", it.price)
                    meal.put("numberOfOrders", it.numberOfOrders)
                    meal.put("mealType", it.mealType.value)
                    meals.put(meal)
                }
                jOrder.put("meals", meals)

                val outputStream: OutputStream = conn.outputStream
                outputStream.write(jOrder.toString().toByteArray())
                outputStream.close()

                val inputStream: InputStream = conn.inputStream
                val reader = InputStreamReader(inputStream)

                val result = JSONObject(reader.readText())

                reader.close()
                callback(result["success"] as Boolean, result["id"] as Int)
            }
            catch (e: Exception) {
                callback(false, -1)
            }

        }.start()
    }

    /**
     * Na novom vlakne poziada server o vsetky evidovane objednavky
     * @param callback predstavuje callback, ktory sa zavola po ukonceni poziadavky
     */
    fun getAllOrders(callback: (ordersArray: ArrayList<Order>?, success: Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(GET_ALL_ORDERS)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream: InputStream = conn.inputStream
                val reader = InputStreamReader(inputStream)

                val result = JSONArray(reader.readText())

                val orderArray = ArrayList<Order>()
                for (i in 0 until result.length()) {
                    val item = result.getJSONObject(i)
                    val order = Order(tableNumber = item["tableNumber"] as Int, sumTotal = item["sumTotal"] as Int, finished = item["finished"] as Boolean, id = item["id"] as Int)

                    val meals = item.getJSONArray("meals")
                    for (j in 0 until meals.length()) {
                        val meal = meals.getJSONObject(j)
                        order.meals.add(Meal(meal["id"] as Int, meal["mealName"] as String, MealType.fromInt(meal["mealType"] as Int), meal["price"] as Int, meal["numberOfOrders"] as Int))
                    }

                    orderArray.add(order)
                }

                reader.close()
                callback(orderArray, true)
            }
            catch (e: Exception) {
                callback(null, false)
            }

        }.start()
    }

    /**
     * Na novom vlakne poziada server o evidovanie obejdnavky ako ukoncenej (pripadne neukoncenej)
     * @param orderId predstavuje id objednavky
     * @param callback predstavuje callback, ktory sa zavola po ukonceni poziadavky
     */
    fun makeOrderFinished(orderId: Int, finished: Boolean, callback: (success: Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(ORDER_FINISHED)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")

                val jOrder = JSONObject()
                jOrder.put("id", orderId)
                jOrder.put("finished", finished)

                val outputStream: OutputStream = conn.outputStream
                outputStream.write(jOrder.toString().toByteArray())
                outputStream.close()

                val inputStream: InputStream = conn.inputStream
                val reader = InputStreamReader(inputStream)

                val result = JSONObject(reader.readText())

                reader.close()
                callback(result["success"] as Boolean)
            }
            catch (e: Exception) {
                callback(false)
            }

        }.start()
    }
}