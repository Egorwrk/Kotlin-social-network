package com.example.socialnetwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.socialnetwork.databinding.ActivityAuthBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registrationBtn: Button = binding.logRegBtn
        registrationBtn.setOnClickListener() {
            fetchAuth().start()
        }
    }

    private fun fetchAuth(): Thread {
        val myJsonObject = JSONObject()
        myJsonObject.put("email", "egorsocialnetwork@gmail.com")
        myJsonObject.put("password", "socialnetwork")
        myJsonObject.put("rememberMe", true)
        val postData = "email=egorsocialnetwork@gmail.com&password=socialnetwork&rememberMe=true"
        return Thread {
            val url = URL("https://social-network.samuraijs.com/api/1.0/auth/login")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("API-KEY", "f663c575-7944-474b-8d8c-64b77d9ea1e6")
            connection.setRequestProperty("Content-Length", postData.length.toString())
            DataOutputStream(connection.outputStream).use { it.writeBytes(postData) }
            if (connection.responseCode == 200) {
                val inputStream = connection.inputStream
                val inputStreamReader= InputStreamReader(inputStream, "UTF-8")
                val jsonObject: JsonObject = JsonParser.parseReader(inputStreamReader).asJsonObject

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(JsonParser.parseString(jsonObject.toString()))
                val request = Gson().fromJson(prettyJson, Request::class.java)
                if (request.resultCode == 0) {
                    val pressed = Intent(this,MainActivity::class.java)
                    startActivity(pressed)
                } else {
                    Toast.makeText(this, "incorrect login or password", Toast.LENGTH_SHORT).show()
                }
            } else{
                Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

class Request (
    var data: RequestData,
    var messages: Array<String>,
    var fieldsErrors: Array<String>,
    var resultCode: Int,
)

class RequestData (
    val userId: String
)