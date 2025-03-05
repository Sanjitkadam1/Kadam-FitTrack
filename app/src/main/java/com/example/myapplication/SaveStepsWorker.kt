package com.example.myapplication

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SaveStepsWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val stepDao = StepDatabase.getInstance(applicationContext).homeDataA()
        val steps = inputData.getInt("steps", 0)
        val dateID = inputData.getInt("dateID", 0)

        return try {
            // Insert or update the step data for the day
            val existingData = stepDao.loadSteps(dateID)
            if (existingData != null) {
                // Update if the data already exists
                stepDao.updateDataA(homeData1(dateID, steps, null, null))
            } else {
                // Insert new data if not already in the database
                stepDao.insertDataA(homeData1(dateID, steps, null, null))
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}

