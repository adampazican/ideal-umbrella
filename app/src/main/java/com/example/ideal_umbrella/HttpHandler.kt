package com.example.ideal_umbrella

import com.example.ideal_umbrella.ChooseMeal.Meal
import com.example.ideal_umbrella.ChooseMeal.MealType
import org.json.JSONArray
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpHandler {
    const val PREFIX = "https://c721bada.ngrok.io"
    const val UPDATE_DB = PREFIX + "/update-db"

    fun getAllMeals(callback: (mealArray: ArrayList<Meal>) -> Unit) {
        Thread {
            val url = URL(UPDATE_DB);
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            val inputStream: InputStream = conn.inputStream
            val reader = InputStreamReader(inputStream)
            val result = JSONArray(reader.readText());

            val mealArray = ArrayList<Meal>()
            for (i in 0 until result.length()) {
                val item = result.getJSONObject(i)
                val meal = Meal(item["id"] as Int, item["meal_name"] as String, MealType.fromInt(item["meal_type"] as Int), item["price"] as Int)
                mealArray.add(meal)
            }

            reader.close()
            callback(mealArray)

        }.start();
    }
}