package com.workmanager.work;

import android.support.annotation.NonNull;
import android.util.Log;
import androidx.work.Worker;

public class MyWork extends Worker {

    private static final String TAB = MyWork.class.getSimpleName();

    @NonNull
    @Override
    public Result doWork() {
        Log.i(TAB,"From My Work");
        return Result.SUCCESS;
    }
}
