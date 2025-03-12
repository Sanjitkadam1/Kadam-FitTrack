package com.example.myapplication



import android.content.Intent
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
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val serviceIntent = Intent(this, StepCounterService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)


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
//        <\set images for buttons>


    }

    fun getWeekID(dateMillis: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMillis
        val year = calendar.get(Calendar.YEAR)
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        return year * 100 + weekOfYear
    }
}

@Entity
data class HomeData1(
    @PrimaryKey val dateID: Int,
    @ColumnInfo(name = "stepData") val stepData: Int?,
    @ColumnInfo(name = "calData") val calData: Float?,
    @ColumnInfo(name = "distData") val distData: Float?
)

@Dao
interface HomeDataA {
    @Insert
    suspend fun insertDataA(vararg homeData1: HomeData1)

    @Update
    suspend fun updateDataA(vararg homeData1: HomeData1)

    @Delete
    suspend fun deleteData(vararg homeData1: HomeData1)

    @Query("SELECT * FROM homedata1 WHERE dateID = :id")
    suspend fun loadSteps(id: Int): HomeData1?


    @Query("SELECT calData FROM homeData1")
    suspend fun loadCal(id:Float): HomeData1

    @Query("SELECT distData FROM homeData1")
    suspend fun distData(id: Float): HomeData1

}

@Entity
data class HomeData2(
    @PrimaryKey val dateID: Int,
    @ColumnInfo(name = "stepAvg") val stepAvg: Int?,
    @ColumnInfo(name = "calAvg") val calAvg: Int?,
    @ColumnInfo(name = "distAvg") val distAvg: Int?
)

@Dao
interface HomeDataB {
    @Insert
    suspend fun insertDataB(vararg homeData2: HomeData2)

    @Update
    suspend fun updateDataB(vararg homeData2: HomeData2)

    @Delete
    suspend fun deleteData(vararg homeData2: HomeData2)


}

