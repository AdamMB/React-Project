package com.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.HashMap;

public class ActivityModule extends ReactContextBaseJavaModule{

    ActivityModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName(){
        return "ActivityStart";
    }

    @ReactMethod
    public void displayActivity(){
        Activity activity = getCurrentActivity();
                if (activity != null) {
                    Intent intent = new Intent(activity, AugmentedFacesActivity.class);
                    intent.putExtra("STATE", StateHolder.getInstance().getState());
                    activity.startActivity(intent);
                }
    }

    @ReactMethod
    public void showState() {
        Handler handler = new Handler();
        int delay = 500; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                Toast.makeText(getReactApplicationContext(), StateHolder.getInstance().getState(), Toast.LENGTH_SHORT).show();
                if(StateHolder.getInstance().getState() != null){
                    Log.d("state", StateHolder.getInstance().getState());
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @ReactMethod
    public void getState(){
        Handler handler = new Handler();
        int delay = 500; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if(StateHolder.getInstance().getState() != null){
                    WritableMap payload = Arguments.createMap();
                    payload.putString("state", StateHolder.getInstance().getState());

                    getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("StatusEvent", payload);
                }
                handler.postDelayed(this, delay);
            }
        }, delay);
    }
}
