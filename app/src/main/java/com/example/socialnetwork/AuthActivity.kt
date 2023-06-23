package com.example.socialnetwork

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.socialnetwork.databinding.ActivityAuthBinding
import com.google.gson.Gson
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
        registrationBtn.setOnClickListener {
            val email: EditText = binding.loginText
            val password: EditText = binding.passwordText
            if (email.text.trim().isNotEmpty() && password.text.trim().isNotEmpty()) {
                fetchAuth(email.text.toString(), password.text.toString()).start()
            } else {
                println("enter email and password")
            }
        }
    }

    private fun fetchAuth(email: String, password: String): Thread {
        val postData = "email=$email&password=$password&rememberMe=true"
        return Thread {
            val url = URL("https://social-network.samuraijs.com/api/1.0/auth/login")
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("API-KEY", "f663c575-7944-474b-8d8c-64b77d9ea1e6")
            connection.setRequestProperty("Content-Length", postData.length.toString())
            DataOutputStream(connection.outputStream).use { it.writeBytes(postData) }
            if (connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                inputStreamReader.close()
                inputSystem.close()
                if (request.resultCode == 0) {
                    val pressed = Intent(this, MainActivity::class.java)
                    startActivity(pressed)
                } else {
                    println("error: ${request.messages[0]}")
                }
            } else {
                println("connection failed")
            }
        }

    }
}

class Request(
    var data: RequestData,
    var messages: Array<String>,
    var fieldsErrors: Array<FieldsErrors>,
    var resultCode: Int,
)

class RequestData(
    val userId: String
)

class FieldsErrors(
    val field: String,
    val error: String
)