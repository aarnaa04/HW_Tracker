package com.example.android.homeworktracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.homeworktracker.data.DatabaseHelper;
import com.example.android.homeworktracker.data.HomeWork_Contract;
import com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry;


public class UserActivity extends AppCompatActivity {
private DatabaseHelper mdbhelper;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mdbhelper=new DatabaseHelper(this);
        //SQLiteDatabase db=mdbhelper.getReadableDatabase();
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this,Main_HomeWork.class));
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_tracker,menu);
        return  true;
    }
    private void displayDatabaseInfo(){
        SQLiteDatabase db=mdbhelper.getReadableDatabase();
        String [] projection={
         homeWorkEntry._ID,
                homeWorkEntry.COLUMN_USER_NAME,
                homeWorkEntry.COLUMN_SUBJECT_NAME,
                homeWorkEntry.COLUMN_DUE_DATE,
                homeWorkEntry.COLUMN_QUESTIONS,
                homeWorkEntry.COLUMN_TARGET_DATE,
                homeWorkEntry.COLUMN_STATUS
        };
        // Perform a query on the HomeWork  table
        /*Cursor cursor=db.query(
                homeWorkEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );*/
        Cursor cursor = db.query(
                homeWorkEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayview=(TextView)findViewById(R.id.text_view_homework);
        try {
            displayview.setText("The Homework table contains"+cursor.getCount()+"entries\n\n" );
            displayview.append(homeWorkEntry._ID+"-"+
            homeWorkEntry.COLUMN_USER_NAME+"-"+
            homeWorkEntry.COLUMN_SUBJECT_NAME+"-"+
            homeWorkEntry.COLUMN_DUE_DATE+"-"+
            homeWorkEntry.COLUMN_QUESTIONS+"-"+
            homeWorkEntry.COLUMN_TARGET_DATE+"-"+
            homeWorkEntry.COLUMN_STATUS+"-"+"\n");

            //matching the column index of each column

            int idColumnIndex=cursor.getColumnIndex(homeWorkEntry._ID);
            int nameColumnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_USER_NAME);
            int subjectColumnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_SUBJECT_NAME);
            int duedateColumnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_DUE_DATE);
            int questionsColimnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_QUESTIONS);
            int targetdateColumnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_TARGET_DATE);
            int statuscolumnIndex=cursor.getColumnIndex(homeWorkEntry.COLUMN_STATUS);
            while(cursor.moveToNext()){

                int currentID=cursor.getInt(idColumnIndex);
                String currentName=cursor.getString(nameColumnIndex);
                String  currentSubject=cursor.getString(subjectColumnIndex);
                String  currentDuedate=cursor.getString(duedateColumnIndex);
                String  currentQuestions=cursor.getString(questionsColimnIndex);
                String  currentTargetdate=cursor.getString(targetdateColumnIndex);
                String currentStatus=cursor.getString(statuscolumnIndex);

                displayview.append(("\n"+currentID+"-"+
                currentName+"-"+
                currentSubject+"-"+
                currentDuedate+"-"+
                currentQuestions+"-"+
                currentTargetdate+"-"+
                 currentStatus
                ));


            }



        }
        finally {
            cursor.close();
        }

    }
   private void insertHomework(){
       //String nameString = mstudentName.getText().toString().trim();
       //String questionsString =mquestions.getText().toString().trim();

       DatabaseHelper mDatabaseHelper=new DatabaseHelper(this);
       SQLiteDatabase db=mDatabaseHelper.getWritableDatabase();
       ContentValues values=new ContentValues();
       /* values.put(homeWorkEntry.COLUMN_USER_NAME,nameString);
        values.put(homeWorkEntry.COLUMN_SUBJECT_NAME, String.valueOf(mlist_subject_spinner));
        values.put(homeWorkEntry.COLUMN_QUESTIONS,questionsString);
        values.put(homeWorkEntry.COLUMN_DUE_DATE, String.valueOf(mdueDate));*/
       values.put(homeWorkEntry.COLUMN_USER_NAME,"Aarnaa");
       values.put(homeWorkEntry.COLUMN_SUBJECT_NAME, "Reading");
       values.put(homeWorkEntry.COLUMN_QUESTIONS,"draw a picture of statue of liberty ");
       values.put(homeWorkEntry.COLUMN_DUE_DATE, "02/10/2017");

       values.put(homeWorkEntry.COLUMN_STATUS,HomeWork_Contract.homeWorkEntry.STATUS_COMPLETED);

       //long newRowid=db.insert(HomeWork_Contract.homeWorkEntry.TABLE_NAME,null,values);

   }
    public  boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_add:
                startActivity(new Intent(this, Main_HomeWork.class));

                //Toast.makeText(getApplicationContext(),"Add menu clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_display:
                Toast.makeText(getApplicationContext(),"Display button clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_on_due:
                Toast.makeText(getApplicationContext(),"on due clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_set_alerts:
                Toast.makeText(getApplicationContext(),"alerts clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_set_target:
                Toast.makeText(getApplicationContext(),"target clicked",Toast.LENGTH_SHORT).show();
                return  true;
            default:
                return   super.onOptionsItemSelected(item);
        }
    }


}
