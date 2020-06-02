package com.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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
                    activity.startActivity(intent);
                }
    }

    @ReactMethod
    public void showState() {
        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                Toast.makeText(getReactApplicationContext(), StateHolder.getInstance().getState(), Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, delay);
            }
        }, delay);
        //Log.d("test", StateHolder.getInstance().getState().toString());
    }
}
