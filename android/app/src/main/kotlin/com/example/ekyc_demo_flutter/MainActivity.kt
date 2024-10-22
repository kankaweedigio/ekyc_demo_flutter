package com.example.ekyc_demo_flutter

import android.content.Context
import android.os.Bundle
import com.scb.techx.ekycframework.BuildConfig
import com.scb.techx.ekycframework.util.EkycUtilities
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import com.scb.techx.ekycframework.domain.ocrliveness.model.response.UserConfirmedValue;
import com.scb.techx.ekycframework.ui.reviewconfirm.model.DopaResult;
import com.scb.techx.ekycframework.ui.theme.CustomizeTheme;
import com.scb.techx.ekycframework.ui.theme.Image;
import com.scb.techx.ekycframework.ui.model.ocrprefill.OcrPrefill;
import java.util.UUID

class MainActivity: FlutterActivity(){
    var token = ""
    var devToken:kotlin.String? = "INSERT YOUR DEV TOKEN HERE"
    var sitToken: String = "INSERT YOUR SIT TOKEN HERE"
    var uatToken: String = "INSERT YOUR UAT TOKEN HERE"
    var preprodToken: String = "INSERT YOUR PREPROD TOKEN HERE"
    var prodToken: String = "INSERT YOUR PROD TOKEN HERE"
    var sessionId: String = "7e4f518d-f4ae-447a-a6e4-d4143135e7b1"
    var resultString: String? = null
    private val mContext: Context? = null
    var ekycToken: String = ""
    private val ekycUtilities = EkycUtilities()
    private val CHANNEL = "com.test.ekyc/initEkyc"
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ekycUtilities.initEkyc(
//            applicationContext,
//            this,
//            "",
//            "",
//            "",
//            null,
//            null,
//
//
//        }
//        )

    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger,CHANNEL)
            .setMethodCallHandler{
                call,result ->
                    if(call.method.equals("initEkyc")){
                        print("Method : "+call.method)
                    }
            }
    }

    fun setSessionId(){
        this.sessionId = UUID.randomUUID().toString()
    }

//    fun setToken(inputToken : String){
//        if (inputToken.length != 0){
//            token = inputToken
//        }else{
//            if (BuildConfig.BUILD_TYPE.toUpperCase() == "SIT") {
//                token = sitToken
//            }else if (BuildConfig.BUILD_TYPE.toUpperCase() == "UAT"){
//                token = uatToken
//            }else if (BuildConfig.BUILD_TYPE.toUpperCase())
//        }
//    }

//    fun initEkyc(sessionId:String,token:String,env:String){
//        ekycUtilities.initEkyc(
//            applicationContext,
//            context,
//            sessionId,
//            token,
//            env,
//            null,
//            null,
//            (success,description,ekycToken) -> {
//
//        }
//
//        )
//    }



}
