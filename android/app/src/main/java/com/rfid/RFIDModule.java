package com.rfid;

import com.facebook.react.bridge.*;
import com.solid.ReaderException;
import com.solid.ReaderInfo;

public class RFIDModule extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;

    RFIDApplication application;

    public RFIDModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        this.application = new RFIDApplication(context);
    }

    @Override
    public String getName() {
        return "RFID";
    }

    @ReactMethod
    public void create(String uriString, Callback successCallback, Callback errorCallback) {
        try {
            application.connect(uriString);
            successCallback.invoke(true);
        } catch (ReaderException e) {
            // TODO: handle exception
            System.out.println("ReaderException:" + e.getMessage());
            errorCallback.invoke(e.getMessage());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception" + e.getMessage());
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void shutdown(Callback successCallback, Callback errorCallback) {
        try {
            application.shutdown();
            successCallback.invoke(false);
        } catch (Exception e) {
            errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void getReaderInfo(Callback successCallback, Callback errorCallback) {
        try {
            ReaderInfo readerInfo = application.getReaderInfo();;
            WritableMap writableMap = new WritableNativeMap();
            writableMap.putInt("versionH", readerInfo.getVersionH());
            writableMap.putInt("versionL", readerInfo.getVersionL());
            writableMap.putInt("type", readerInfo.Type());
            writableMap.putInt("power", readerInfo.getPower());
            writableMap.putInt("scanTime", readerInfo.getScanTime());
            writableMap.putInt("checkAnt", readerInfo.getCheckAnt());
            writableMap.putInt("ant", readerInfo.getAnt());
            writableMap.putInt("AntH", readerInfo.getAntH());
            writableMap.putInt("maxFre", readerInfo.getmaxFre());
            writableMap.putInt("minFre", readerInfo.getminFre());
            successCallback.invoke(writableMap);
        } catch (Exception e) {
            errorCallback.invoke(e.getMessage());
        }
    }

}
