package com.workmanager.work;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class MyWorkSecond extends Worker {

    private static final String TAB = MyWorkSecond.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {

        Log.e(TAB,"From My MyWorkSecond");

        return Result.SUCCESS;
    }
}
