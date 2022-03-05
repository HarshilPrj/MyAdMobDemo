package com.example.myadmobdemo

import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var TAG = "MainActivity"
    lateinit var mButton: Button
    lateinit var mMultipleChoiceButton: Button
    lateinit var mDatePickerDialogButton:Button
    lateinit var mtxtdisplayDate:TextView

    lateinit var mprogressbtn: Button

    private var progressStatus = 0


    private var progressBar: ProgressBar? = null
    private var i = 0
    private var txtView: TextView? = null
    private val handler = Handler()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mButton = findViewById(R.id.button)
        mMultipleChoiceButton = findViewById(R.id.MutliChoice)
        mDatePickerDialogButton = findViewById(R.id.Datepic)
        mtxtdisplayDate = findViewById(R.id.txtdisplayDate)

        mprogressbtn=findViewById(R.id.Progressbtn)

        progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar

        // finding textview by its id
        txtView = findViewById<TextView>(R.id.text_view)
    }

    override fun onStart() {
        super.onStart()
        mButton.setOnClickListener {
            var dialog = AlertDialog.Builder(this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you wnat to delete this item?")
                .setPositiveButton("Delete",object : DialogInterface.OnClickListener{
                    override fun onClick(dialogInterface : DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity,"Item Deleted Successfully",Toast.LENGTH_LONG).show()
                    }
                })
                .setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Toast.makeText(this@MainActivity,"Cancel the delete operation",Toast.LENGTH_LONG).show()
                    }
                })
                .create()
            dialog.show()
        }

        var colorsItem:Array<CharSequence> = arrayOf("Red","Orange","Blue","Black")
        var icount = BooleanArray(colorsItem.size)
        var selectedColors = ArrayList<Int>()

        var value:String = ""

        mMultipleChoiceButton.setOnClickListener {

            var dialog = AlertDialog.Builder(this)
                .setTitle("Choose Colors")
                .setMultiChoiceItems(colorsItem,icount,object :DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) {
                        if (p2){
                            selectedColors.add(p1)
                        }else{
                            selectedColors.remove(p1)
                        }
                    }
                })
                .setCancelable(false)
                .setPositiveButton("Done",object :DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        for (i in 0..selectedColors.size-1){
                            value = value + "\n" + colorsItem[selectedColors.get(i)]
                        }
                    }
                })
                .create()
            dialog.show()
        }

        mprogressbtn.setOnClickListener {
            progressBar?.visibility = View.VISIBLE

            i = progressBar!!.progress

            Thread(Runnable {
                // this loop will run until the value of i becomes 99
                while (i < 100) {
                    i += 1
                    // Update the progress bar and display the current value
                    handler.post(Runnable {
                        progressBar!!.progress = i
                        // setting current progress to the textview
                        txtView!!.text = i.toString() + "/" + progressBar!!.max
                    })
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }


            }).start()
        }

        mDatePickerDialogButton.setOnClickListener {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view,year,month,dayofMonth ->
                // display value of textview
                mtxtdisplayDate.setText("You Have Selected:$dayofMonth-"+(month+1)+"-$year")
            },year,month,day)
            dialog.show()
        }
    }
}