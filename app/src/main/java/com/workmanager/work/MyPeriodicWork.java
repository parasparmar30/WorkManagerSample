package com.workmanager.work;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class MyPeriodicWork extends Worker {

    private static final String TAB = MyPeriodicWork.class.getSimpleName();
    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAB,"From Periodic Work");
        return Result.SUCCESS;
    }
}
