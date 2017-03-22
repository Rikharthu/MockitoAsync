package com.example.android.mockitoasync;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DummyCaller implements DummyCallback {
    public static final String LOG_TAG = DummyCaller.class.getSimpleName();

    private final DummyCollaborator dummyCollaborator;

    private List<String> result = new ArrayList<String>();

    public DummyCaller(DummyCollaborator dummyCollaborator) {
        this.dummyCollaborator = dummyCollaborator;
    }

    public void doSomethingAsynchronously() {
        dummyCollaborator.doSomethingAsynchronously(this);
    }

    public List<String> getResult() {
        return this.result;
    }

    @Override
    public void onSuccess(List<String> result) {
        this.result = result;
        Log.d(LOG_TAG, "On Success: " + result);
    }

    @Override
    public void onFail(int code) {
        Log.d(LOG_TAG, "On Fail: " + code);
    }
}
