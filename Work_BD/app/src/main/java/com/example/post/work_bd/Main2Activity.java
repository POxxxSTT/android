package com.example.post.work_bd;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.post.work_bd.DataBaseHelper.COL_ID;
import static com.example.post.work_bd.Main1Activity.Click_ID_Stud;
import static com.example.post.work_bd.MainActivity.myDbHelper;
import static com.example.post.work_bd.MainActivity.myDataBase;
import static com.example.post.work_bd.MainActivity.Click_ID;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> GroupSubMar = new ArrayList<>();
    Button my_btn_up, btnAddSub, btnDelSub;
    EditText etId, etNameSub, etMark;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        etId = (EditText) findViewById(R.id.etId);
        etNameSub = (EditText) findViewById(R.id.etNameSub);
        etMark = (EditText) findViewById(R.id.etMark);

        my_btn_up = (Button) findViewById(R.id.my_btn_up2);
        btnAddSub = (Button) findViewById(R.id.btnAddSub);
        btnDelSub = (Button) findViewById(R.id.btnDelSub);

        my_btn_up.setOnClickListener(this);
        btnAddSub.setOnClickListener(this);
        btnDelSub.setOnClickListener(this);

        Cursor cursor = myDataBase.query("MyAllStud", null,"_id = ?", new String[] {Click_ID_Stud},null,null,null,null);

        if (cursor.moveToFirst()){

            int colName = cursor.getColumnIndex("Name_sub");
            int colMark = cursor.getColumnIndex("Mark");

            do {
                GroupSubMar.add(cursor.getString(colName) + "  " + cursor.getString(colMark));
            }while (cursor.moveToNext());
        }else
            Log.d("mLog", "БАЗА ДАННЫХ ПУСТАААААААААААААААААААААА");

        cursor.close();


        // получаем экземпляр элемента ListView
        ListView listView = (ListView)findViewById(R.id.listView2);

        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_list_item_1, GroupSubMar);

        listView.setAdapter(adapter);

    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    @Override
    public void onClick(View v) {
        String id = etId.getText().toString();
        String NameSub = etNameSub.getText().toString();
        String Marks = etMark.getText().toString();

        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();


        switch (v.getId()){
            case R.id.my_btn_up2:
                Intent intent = new Intent(Main2Activity.this, Main1Activity.class);
                startActivity(intent);
                break;
            case R.id.btnAddSub:

                contentValues1.put("_id", id);
                contentValues1.put("Name_sub", NameSub);
                contentValues2.put("_id_Student", Click_ID_Stud);
                contentValues2.put("_id_Subject", id);
                contentValues2.put("Mark", Marks);
                myDataBase.insert("Subject", null, contentValues1);
                myDataBase.insert("Marks", null, contentValues2);
                restartActivity();
                break;
            case R.id.btnDelSub:
                if(id.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите ID кого хотите удолить.", Toast.LENGTH_SHORT).show();
                    break;
                }
                myDataBase.delete("Subject", "_id = " + id, null);
                restartActivity();
                break;
        }
    }
}








/*
String strI = String.valueOf(id);
Log.d("mLog", String.valueOf(GroupSubMar));

Toast.makeText(getApplicationContext(), Click_ID, Toast.LENGTH_SHORT).show();

"_id = ?", new String[] {Click_ID}



*/








































































