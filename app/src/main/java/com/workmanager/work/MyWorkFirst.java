package com.workmanager.work;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

public class MyWorkFirst extends Worker {

    private static final String TAB = MyWorkFirst.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {
        Log.e(TAB,"From My WorkFirst");
        return Result.SUCCESS;
    }
}
