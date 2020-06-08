package com.project;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class ViewModule extends SimpleViewManager<View>
{
    private TextView tv;
    @NonNull
    @Override
    public String getName() {
        return "ViewModule";
    }

    @NonNull
    @Override
    protected View createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new View(reactContext);
    }

    @ReactProp(name = "text")
    public void setText(View view, String text){

        tv = view.findViewById(R.id.tv);
        tv.setText(text);
    }
}
