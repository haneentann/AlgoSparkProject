package com.example.algosparkproject.mobile.utils;

public interface GeminiCallback {
    void onSuccess(String result);
    void onError(Throwable error);
}