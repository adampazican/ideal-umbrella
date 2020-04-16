package com.example.ideal_umbrella

import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.MealTypeMenu.MealType
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

object HttpHandler {
    private const val PREFIX = "https://d657941e.ngrok.io"
    private const val UPDATE_DB = "$PREFIX/update-db"
    private const val VERIFY_USER = "$PREFIX/verify-user"

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

    fun verifyUser(email: String, password: String, callback: (success: Boolean) -> Unit) {
        Thread {
            try {
                val url = URL(VERIFY_USER)
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

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
}