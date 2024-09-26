package com.app_test_sdk

import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import vn.vnpd.demosdk.MainActivity
import vn.vnpd.demosdk.api.ButtonClickListener
import vn.vnpd.demosdk.api.EventManager

class CalendarViewManager(reactContext: ReactApplicationContext) :
    ReactContextBaseJavaModule(reactContext) {
    @Composable
    @RequiresApi(Build.VERSION_CODES.O)
    @ReactMethod
    fun openMyComposeView(token: String) {
        val intent = Intent(reactApplicationContext, MainActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("BASE_URL", "htpps://www.google.com");
        intent.putExtra("TOKEN", token);

        reactApplicationContext.startActivity(intent)
    }

    override fun getName(): String {
        return "ComposeModule"
    }

    @ReactMethod
    override fun initialize() {
        super.initialize()
        EventManager.buttonClickListener = object : ButtonClickListener {
            override fun onButtonClick(data: String) {
                sendEvent(reactApplicationContext,"ButtonClick", data)
            }
        }
        Log.d("CalendarModule", "Initialized CalendarViewManager")
    }

     fun sendEvent(reactContext: ReactContext, eventName: String, data: String) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, data)
    }
}

