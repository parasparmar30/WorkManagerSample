package com.workmanager;


import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.workmanager.work.MyPeriodicWork;
import com.workmanager.work.MyWork;
import com.workmanager.work.MyWorkFirst;
import com.workmanager.work.MyWorkThird;
import com.workmanager.work.MyWorkSecond;
import com.workmanager.work.MyWorkWithData;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkStatus;

public class MainActivity extends AppCompatActivity {
    private UUID uuid;
    private PeriodicWorkRequest mPeriodicWorkRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.OneTimeWork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                        .build();

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }

        });
        findViewById(R.id.btnPeriodicWork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPeriodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class,
                        10, TimeUnit.HOURS)
                        .addTag("pWoRequest")
                        .build();

                WorkManager.getInstance().enqueue(mPeriodicWorkRequest);
            }
        });

        findViewById(R.id.btnChainableWork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            OneTimeWorkRequest MyWorkA = new OneTimeWorkRequest.Builder(MyWorkFirst.class)
                    .build();
            OneTimeWorkRequest MyWorkB = new OneTimeWorkRequest.Builder(MyWorkThird.class)
                    .build();
            OneTimeWorkRequest MyWorkC = new OneTimeWorkRequest.Builder(MyWorkSecond.class)
                    .build();

            WorkManager.getInstance()
                    .beginWith(MyWorkA)
                    .then(MyWorkB)
                    .then(MyWorkC)
                    .enqueue();
            }
        });

        findViewById(R.id.btnParallelwork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            OneTimeWorkRequest MyWorkA = new OneTimeWorkRequest.Builder(MyWorkFirst.class)
                    .build();
            OneTimeWorkRequest MyWorkB = new OneTimeWorkRequest.Builder(MyWorkThird.class)
                    .build();
            OneTimeWorkRequest MyWorkC = new OneTimeWorkRequest.Builder(MyWorkSecond.class)
                    .build();

            WorkManager.getInstance()
                    .beginWith(MyWorkA, MyWorkB)
                    .then(MyWorkC)
                    .enqueue();


            }
        });

        findViewById(R.id.btnParallelwork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uuid = mPeriodicWorkRequest.getId();
                WorkManager.getInstance().cancelWorkById(uuid);
                Log.i("Work Canceled", "PeriodicWork Cancel By Id");
            }
        });

        findViewById(R.id.btnWorkWithConstraints).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constraints constraints = new Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build();

                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWork.class)
                        .setConstraints(constraints)
                        .build();

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);

                WorkManager.getInstance().getStatusById(oneTimeWorkRequest.getId()).observe(MainActivity.this,
                        workStatus -> {
                            if (workStatus != null) {
                                Log.i("oneTimeWorkRequest: " ,
                                        String.valueOf(workStatus.getState().name()));
                            }

                            if (workStatus != null && workStatus.getState().isFinished()) {
                                Log.i("oneTimeWorkRequest: " ,
                                        "DOne");
                            }

                        });
            }

        });


        findViewById(R.id.btnWorkWithData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = new Data.Builder()
                        .putString(MyWorkWithData.EXTRA_TITLE, "Data From Activity!")
                        .putString(MyWorkWithData.EXTRA_TEXT, "Message")
                        .build();

                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorkWithData.class)
                        .setInputData(data)
                        .build();

                WorkManager.getInstance().enqueue(oneTimeWorkRequest);

                WorkManager.getInstance().getStatusById(oneTimeWorkRequest.getId()).observe(MainActivity.this, new Observer<WorkStatus>() {
                    @Override
                    public void onChanged(@Nullable WorkStatus workStatus) {
                        if (workStatus != null && workStatus.getState().isFinished()) {
                            String message = workStatus.getOutputData().getString(MyWorkWithData.EXTRA_OUTPUT_MESSAGE);
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
