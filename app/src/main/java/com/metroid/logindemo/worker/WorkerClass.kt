package com.metroid.logindemo.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WorkerClass(appcontext : Context, WorkerParam : WorkerParameters):Worker(appcontext,WorkerParam) {
    override fun doWork(): Result {
        Log.d("Worker","TestingWorker Done its working properly!!!!")
        return Result.success()
    }
}