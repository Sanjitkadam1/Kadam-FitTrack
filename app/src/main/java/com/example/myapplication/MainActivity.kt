package com.example.myapplication



import android.content.Intent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import fittrack.app.R
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorEventListener: SensorEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout)

        val serviceIntent = Intent(this, StepCounterService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

        val dayButton: Button = findViewById<Button>(R.id.button)
        dayButton.setOnClickListener {
            setContentView(R.layout.weeklayout)
        }


//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        val stepCounterSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

//        <set images for buttons
        val editButton: ImageButton = findViewById(R.id.editButton)
        editButton.setImageResource(R.drawable.edit)
        val pagesButton: ImageButton = findViewById(R.id.PagesButton)
        pagesButton.setImageResource(R.drawable.pages_button)
        val stepsView: ImageView = findViewById(R.id.stepsImage)
        stepsView.setImageResource(R.drawable.shoeprints)
        val distView: ImageView = findViewById(R.id.distImage)
        distView.setImageResource(R.drawable.running)
        val calView: ImageView = findViewById(R.id.calImage)
        calView.setImageResource(R.drawable.flame_1)
//        set images for buttons>

//        if (stepCounterSensor != null) {
//            sensorEventListener = object : SensorEventListener {
//                override fun onSensorChanged(event: SensorEvent?) {
//                    event?.let {
//                        val steps = it.values[0]
//                        // Process the step count here
//                        println("Steps: $steps")
//                    }
//                }
//
//                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//                    // Handle sensor accuracy changes here if needed
//                }
//            }
//
//
//        }
    }

//    override fun onPause() {
//        super.onPause()
//        sensorManager.unregisterListener(sensorEventListener)
//    }

}

@Entity
data class homeData1(
    @PrimaryKey val dateID: Int,
    @ColumnInfo(name = "stepData") val stepData: Int?,
    @ColumnInfo(name = "calData") val calData: Int?,
    @ColumnInfo(name = "distData") val distData: Int?
)

@Dao
interface homeDataA {
    @Insert
    suspend fun insertDataA(vararg homeData1: homeData1)

    @Update
    suspend fun updateDataA(vararg homeData1: homeData1)

    @Delete
    suspend fun deleteData(vararg homeData1: homeData1)

    @Query("SELECT * FROM homedata1 WHERE dateID = :id")
    suspend fun loadSteps(id: Int): homeData1?


    @Query("SELECT calData FROM homeData1")
    suspend fun loadCal(id:Int): homeData1

    @Query("SELECT distData FROM homeData1")
    suspend fun distData(id: Int): homeData1


}


