package org.game.base;

import java.util.HashMap;

/**
 * @author gaoyu
 * @date 2021/10/26
 */
public class NativeJsCorrespondent {

    private HashMap<String, INativeMethod> nativeMethodMap;

    private IJSMethod jsMethod;

    private static NativeJsCorrespondent instance;

    private NativeJsCorrespondent() {
        nativeMethodMap = new HashMap<>();
    }

    public static NativeJsCorrespondent getInstance() {
        if(instance == null) {
            instance = new NativeJsCorrespondent();
        }
        return instance;
    }

    public void setJSMethod(IJSMethod ijsMethod) {
        this.jsMethod = ijsMethod;
    }

    public void registerNativeMethod(String key, INativeMethod iNativeMethod) {
        nativeMethodMap.put(key, iNativeMethod);
    }

    public String callNative(String key, String value) {
        INativeMethod nativeMethod = nativeMethodMap.get(key);
        if(nativeMethod != null) {
            return nativeMethod.invoke(value);
        }
        return null;
    }

    public boolean hasMethod(String key) {
        return nativeMethodMap.containsKey(key);
    }

    public void callJS(String key, String value) {
        if(jsMethod != null) {
            jsMethod.invoke(key, value);
        }
    }

}
