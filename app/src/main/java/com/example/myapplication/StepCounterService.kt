package com.example.myapplication

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.Calendar
import java.util.concurrent.TimeUnit

class StepCounterService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var stepCounter: Sensor? = null
    private var dailySteps = 0f
    private var initialSteps = -1f


    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounter?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Schedule end-of-day saving
        scheduleEndOfDaySave()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val currentSteps = it.values[0]
            if (initialSteps < 0) {
                initialSteps = currentSteps // Set the initial steps
            }
            dailySteps = currentSteps - initialSteps // Calculate steps for the day
            println("Daily Steps: $dailySteps")
//            println("Steps in background: $steps")
        }
    }

    private fun scheduleEndOfDaySave() {
        val workRequest = OneTimeWorkRequestBuilder<SaveStepsWorker>()
            .setInitialDelay(calculateTimeUntilMidnight(), TimeUnit.MILLISECONDS) // Delay until midnight
            .setInputData(workDataOf("steps" to dailySteps)) // Pass daily steps to the worker
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    // Helper function to calculate the delay until midnight
    private fun calculateTimeUntilMidnight(): Long {
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            add(Calendar.DAY_OF_MONTH, 1)
        }
        return midnight.timeInMillis - now.timeInMillis
    }

    private fun getTodayDateID(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR) * 1000 + calendar.get(Calendar.DAY_OF_YEAR) // Unique ID for each day
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
