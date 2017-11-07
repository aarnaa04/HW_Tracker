package com.example.android.homeworktracker.data;

import android.provider.BaseColumns;

/**
 * Created by Aarnaa on 10/19/2017.
 */

public class HomeWork_Contract  {
    private  HomeWork_Contract(){};
    public static final class homeWorkEntry implements BaseColumns{
        public   static  final String ID =BaseColumns._ID;
        public static  final String TABLE_NAME= "HomeWork";

        public  static  final  String COLUMN_USER_NAME= "user_name";
        public  static  final  String COLUMN_SUBJECT_NAME="subject_name";
        public  static  final  String COLUMN_QUESTIONS="questions";
        public  static final String COLUMN_DUE_DATE="due_date";
        public  static  final  String COLUMN_TARGET_DATE="target_date";
        public  static  final  String COLUMN_STATUS="status";

        public  static  final  int STATUS_UNTOUCHED=0;
        public static  final  int STATUS_STARTED=1;
        public  static  final  int STATUS_COMPLETED=2;
    }
}
