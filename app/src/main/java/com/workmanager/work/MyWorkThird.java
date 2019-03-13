package com.workmanager.work;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class MyWorkThird extends Worker {

    private static final String TAB = MyWorkThird.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {

        Log.e(TAB,"From My WorkThird");

        return Result.SUCCESS;
    }
}
