package com.example.ekyc_demo_flutter

import android.content.Context
import android.os.Bundle
import com.scb.techx.ekycframework.BuildConfig
import com.scb.techx.ekycframework.util.EkycUtilities
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.util.UUID

class MainActivity: FlutterActivity() {
    var token = ""
    var devToken: kotlin.String? = "INSERT YOUR DEV TOKEN HERE"
    var sitToken: String = "INSERT YOUR SIT TOKEN HERE"
    var uatToken: String = "INSERT YOUR UAT TOKEN HERE"
    var preprodToken: String = "INSERT YOUR PREPROD TOKEN HERE"
    var prodToken: String = "INSERT YOUR PROD TOKEN HERE"
    var sessionId: String = "7e4f518d-f4ae-447a-a6e4-d4143135e7b1"
    var resultString: String? = null
    var ekycToken: String = ""
    private val ekycUtilities = EkycUtilities()
    private val CHANNEL = "com.test.ekyc/initEkyc"

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                if (call.method.equals("initEkyc")) {
                    // รับค่า token และ env จากฝั่ง Flutter
                    val inputToken = call.argument<String>("token") ?: ""
                    val env = call.argument<String>("env") ?: "UAT"

                    // เรียกใช้ฟังก์ชัน livenessCheck
                    livenessCheck(sessionId, inputToken, env)
                    result.success("Liveness check initiated")
                } else {
                    result.notImplemented()
                }
            }
    }

    fun setSessionId() {
        this.sessionId = UUID.randomUUID().toString()
    }

    fun setToken(inputToken: String) {
        if (inputToken.isNotEmpty()) {
            token = inputToken
        }
    }

    fun livenessCheck(sessionId: String, token: String, env: String) {
        // Set token and session ID
        setToken(token)
        setSessionId()

        // Initialize eKYC
        ekycUtilities.initEkyc(
            applicationContext, // Application context
            this, // Current activity context
            sessionId, // Session ID
            token, // eKYC token
            env, // Environment (UAT, SIT, etc.)
            null, // CustomizeTheme (if applicable)
            null, // Additional settings (optional)
            object : EkycUtilities.InitCallback { // ใช้ object ของ InitCallback
                fun onResult(success: Boolean, description: String, ekycToken: String?) {
                    this@MainActivity.ekycToken = ekycToken ?: ""  // Save ekycToken

                    if (success) {
                        // Call liveness check after successful init
                        ekycUtilities.livenessCheck(
                            this@MainActivity,  // Pass the current Activity context
                            object : EkycUtilities.LivenessCheckCallback { // ใช้ object ของ LivenessCheckCallback
                                override fun onResult(livenessSuccess: Boolean, livenessDescription: String) {
                                    // Handle the result of the liveness check
                                    saveOtherResultString(livenessSuccess, livenessDescription)
                                }
                            }
                        )
                    } else {
                        // Handle failure of initEkyc
                        saveOtherResultString(success, description)
                    }
                }

                override fun onSuccess(success: Boolean, description: String, ekycToken: String?) {
                    TODO("Not yet implemented")
                }
            }
        )
    }


    fun saveOtherResultString(success: Boolean, description: String) {
        // Handle or log the result string
        println("Success: $success, Description: $description")
    }
}
