package com.example.android.homeworktracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry;

/**
 * Created by Aarnaa on 10/18/2017.
 */


public class DatabaseHelper extends SQLiteOpenHelper{
   public  static final  String LOG_TAG=DatabaseHelper.class.getSimpleName();
    private  static  final String DATABASE_NAME="tracker.db";
    private  static final int DATABASE_VERSION=1;


      public DatabaseHelper(Context context){
          super(context,DATABASE_NAME,null,DATABASE_VERSION);
      }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the table  with name homework_table

        String  SQL_CREATE_TABLE=("CREATE TABLE "+ homeWorkEntry.TABLE_NAME  +   "("
                +homeWorkEntry.ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +homeWorkEntry.COLUMN_USER_NAME +" TEXT NOT NULL,"
                +homeWorkEntry.COLUMN_SUBJECT_NAME+" TEXT NOT NULL,"
                +homeWorkEntry.COLUMN_QUESTIONS+" TEXT NOT NULL,"
                +homeWorkEntry.COLUMN_DUE_DATE+" DATE,"
                +homeWorkEntry.COLUMN_TARGET_DATE+" DATE,"
                +homeWorkEntry.COLUMN_STATUS+" TEXT ); ");
        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



}
