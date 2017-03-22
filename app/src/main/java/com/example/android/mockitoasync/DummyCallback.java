package com.example.android.mockitoasync;


import java.util.List;

public interface DummyCallback {
    public void onSuccess(List<String> result);

    public void onFail(int code);
}