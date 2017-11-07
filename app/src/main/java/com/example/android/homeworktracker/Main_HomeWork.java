package com.example.android.homeworktracker;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.homeworktracker.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry;
/*import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.COLUMN_QUESTIONS;
import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.COLUMN_STATUS;
import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.COLUMN_SUBJECT_NAME;
import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.COLUMN_USER_NAME;
import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.STATUS_COMPLETED;
import static com.example.android.homeworktracker.data.HomeWork_Contract.homeWorkEntry.TABLE_NAME;*/


public class Main_HomeWork extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    ImageButton speaker_imageButton;
    ImageButton save_imageButton;
    private EditText medtVoiceToText;
    private  Spinner mlist_subject_spinner;
    private EditText mstudentName;
    //private  Spinner msubjectName;
    private  EditText mdueDate;
    private  EditText mquestions;
    FloatingActionButton fab;
    SimpleDateFormat sdf;

    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_homework);
        //initialising the edit text
        medtVoiceToText=(EditText)findViewById(R.id.edit_add_sentence);
        mstudentName=(EditText)findViewById(R.id.addName);
        mdueDate=(EditText)findViewById(R.id.edt_DueDate) ;
        mquestions=(EditText)findViewById(R.id.edit_add_sentence);
        //Spinner element
        mlist_subject_spinner=(Spinner)findViewById(R.id.spinner);
        setUpSpinner();




        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mdueDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Main_HomeWork.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
       sdf = new SimpleDateFormat(myFormat, Locale.US);

        mdueDate.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_homework,menu);
        return  true;
    }
    public  boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_save:
                insertHomework();
                finish();
                Toast.makeText(getApplicationContext(),"save clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_calender:
                Toast.makeText(getApplicationContext(),"calender clicked",Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_microphone:
                startVoiceInput();
                Toast.makeText(getApplicationContext(),"microphoneclicked",Toast.LENGTH_SHORT).show();
                return  true;

            default:
                return   super.onOptionsItemSelected(item);
        }
    }


    private  void setUpSpinner(){
        //Spinner spinner=(Spinner)findViewById(spinner);
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter subjectSpinnerAdapter=ArrayAdapter.createFromResource(this,R.array.spinner_subjects,
                android.R.layout.simple_dropdown_item_1line);
        // Specify dropdown layout style - simple list view with 1 item per line
        subjectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mlist_subject_spinner.setAdapter(subjectSpinnerAdapter);
        addListenerOnButton();
    }
    public  void addListenerOnButton(){
        speaker_imageButton=(ImageButton)findViewById(R.id.speaker);
        speaker_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startVoiceInput();
            }
        });
        save_imageButton=(ImageButton)findViewById(R.id.addSubject_save);
        save_imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
insertHomework();
                finish();



            }
        });
    }
    private void startVoiceInput(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,2000);
        try{
            startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        }catch(ActivityNotFoundException a){

        }

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    medtVoiceToText.setText(result.get(0));
                }
                break;
            }

        }
    }
    private void insertHomework(){
        String nameString = mstudentName.getText().toString().trim();
        String questionsString =mquestions.getText().toString().trim();
        String subjectString=mlist_subject_spinner.getSelectedItem().toString().trim();

        String duedateString=mdueDate.getText().toString().trim();




        //DatabaseHelper mDatabaseHelper;
        DatabaseHelper mDbHelper = new DatabaseHelper(this);
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(homeWorkEntry.COLUMN_USER_NAME, nameString);
        values.put(homeWorkEntry.COLUMN_SUBJECT_NAME, subjectString);
        values.put(homeWorkEntry.COLUMN_QUESTIONS, questionsString);

         values.put(homeWorkEntry.COLUMN_DUE_DATE, duedateString);
        values.put(homeWorkEntry.COLUMN_STATUS, homeWorkEntry.STATUS_COMPLETED);

long newRowid=db.insert(homeWorkEntry.TABLE_NAME,null,values);
if(newRowid == -1){
    Toast.makeText(this,"Error with saving pet",Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(this,"Success data saved",Toast.LENGTH_SHORT).show();

}
    }
    //Date Picker






}
