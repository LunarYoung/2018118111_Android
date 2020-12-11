package com.zqg.memo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class Edit extends AppCompatActivity  {
    LinearLayout myLayout;
    EditText noteTitle, edt;
    Spinner currentFlag;
    TextView alarmView;
    RadioGroup tagRadio;
    RadioButton rdButton;
    int tag;//颜色
    String textDate;
    String textTime;
    String titleText;
    String mainText;
    String flagText = "其他";
    String[] tabsList;

    int num = 0; //for requestcode,是MainActivity中startActivityForResult(intent,position)的position，或者通俗说是该note列表位置
    int id = 0;
    String alarm = "";

    private DatePickerDialog dialogDate;
    private TimePickerDialog dialogTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit);

        Toolbar edit_toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(edit_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        Intent it = getIntent();
        getInformationFromMain(it);

        myLayout = (LinearLayout) findViewById(R.id.whole);
        noteTitle = (EditText) findViewById(R.id.edit_title);
        edt = (EditText) findViewById(R.id.editText);
        //alarmView = (TextView) findViewById(R.id.alarmView);
        currentFlag = (Spinner) findViewById(R.id.currentFlag);

        noteTitle.setText(titleText);
        edt.setText(mainText);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tabsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currentFlag.setAdapter(adapter);
        currentFlag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("选择的分组类型:",tabsList[position]);
                flagText = tabsList[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveButton:
                if (!(edt.getText().toString()).trim().equals("")) {
                    returnResult();
                }
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!(edt.getText().toString().trim()).equals("")) {
                returnResult();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void getInformationFromMain(Intent it) {
        id = it.getIntExtra("id", 0);
        num = it.getIntExtra("num", 0);
        tag = it.getIntExtra("tag", 0);
        textDate = it.getStringExtra("textDate");
        textTime = it.getStringExtra("textTime");
        alarm = it.getStringExtra("alarm");
        titleText = it.getStringExtra("noteTitle");
        mainText = it.getStringExtra("mainText");
        flagText = it.getStringExtra("flag");
        tabsList = it.getStringArrayExtra("tabsArray");
    }


    private void returnResult() {
        Intent it = new Intent();
        it.putExtra("id", id);
        it.putExtra("num", num);
        it.putExtra("tag", tag);
        it.putExtra("alarm", alarm);
        it.putExtra("noteTitle", noteTitle.getText().toString().trim());
        it.putExtra("mainText", edt.getText().toString().trim());
        it.putExtra("flag", flagText);
        setResult(RESULT_OK, it);//MainActivity中调用了startActivityForResult()
    }

}