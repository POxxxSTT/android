package com.example.post.work_bd;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.post.work_bd.DataBaseHelper.COL_FIRST;
import static com.example.post.work_bd.DataBaseHelper.COL_GROUP_ID;
import static com.example.post.work_bd.DataBaseHelper.COL_ID;
import static com.example.post.work_bd.DataBaseHelper.COL_LAST;
import static com.example.post.work_bd.DataBaseHelper.COL_MIDDLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String Click_ID;

    public static DataBaseHelper myDbHelper;
    public static SQLiteDatabase myDataBase;
    ArrayList<String> group = new ArrayList<>();

    Button btnAddGroup, btnDelGroup;

    EditText etId, etGroup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Toast.makeText(getApplicationContext(), " Здесь должен быть текст", Toast.LENGTH_SHORT).show();
            }
        });

        etId = (EditText) findViewById(R.id.etId);
        etGroup = (EditText) findViewById(R.id.etGroup);

        btnAddGroup = (Button) findViewById(R.id.btnAddGroup);
        btnDelGroup = (Button) findViewById(R.id.btnDelGroup);

        btnAddGroup.setOnClickListener(this);
        btnDelGroup.setOnClickListener(this);

        myDbHelper = new DataBaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Не удалось создать базу данных");
        }

        try {
            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        myDataBase = myDbHelper.getWritableDatabase();
        Cursor cursor = myDataBase.query("\"Group\"", null,null,null,null,null,null);
        if (cursor.moveToFirst()) {
            int collId = cursor.getColumnIndex(COL_ID) + 1;
            int colName = cursor.getColumnIndex("Name_gr");

            do {

                group.add("Group№  " + cursor.getString(colName));
            }while (cursor.moveToNext());

        }else
            Log.d("mLog", "БАЗА ДАННЫХ ПУСТАААААААААААААААААААААА");

        cursor.close();
        // получаем экземпляр элемента ListView
        ListView listView = (ListView)findViewById(R.id.listView);

        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, group);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String strI = String.valueOf(id+1);
                Click_ID = strI;

                Intent intent = new Intent(MainActivity.this, Main1Activity.class);
                startActivity(intent);
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    @Override
    public void onClick(View v) {

        String id = etId.getText().toString();
        String Group = etGroup.getText().toString();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()){
            case R.id.btnAddGroup:
                contentValues.put("_id", id);
                contentValues.put("Name_gr", Group);
                myDataBase.insert("\"Group\"", null, contentValues);
                restartActivity();
                break;

            case R.id.btnDelGroup:
                if(id.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите ID кого хотите удолить.", Toast.LENGTH_SHORT).show();
                    break;
                }
                myDataBase.delete("\"Group\"", "_id = " + id, null);
                restartActivity();
                break;
        }
    }


}





/*
 String strI = String.valueOf(id);

Toast.makeText(getApplicationContext(), " Здесь должен быть текст", Toast.LENGTH_SHORT).show();



my_text.append("ID = " + cursor.getInt(collId) + ", LastName - " + cursor.getString(colLast) + ", FirstName - " + cursor.getString(colFirst) + ", MiddleName - " + cursor.getString(colMiddle) + ", GroupId - " + cursor.getString(colGroupId)+"\n");



@Override
    public void onClick(View v) {

        SQLiteDatabase myDataBase = myDbHelper.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();


        switch (v.getId()) {
            case R.id.my_btn:

                Cursor cursor = myDataBase.query("Studen", null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    int collId = cursor.getColumnIndex(COL_ID)+1;
                    int colLast = cursor.getColumnIndex(COL_LAST);
                    int colFirst = cursor.getColumnIndex(COL_FIRST);
                    int colMiddle = cursor.getColumnIndex(COL_MIDDLE);
                    int colGroupId = cursor.getColumnIndex(COL_GROUP_ID);

                    do {

                        my_text.append("ID = " + cursor.getInt(collId) + ", LastName - " + cursor.getString(colLast) + ", FirstName - " + cursor.getString(colFirst) + ", MiddleName - " + cursor.getString(colMiddle) + "\n");
                    }while (cursor.moveToNext());
                }else
                    Log.d("mLog", "БАЗА ДАННЫХ ПУСТАААААААААААААААААААААА");

                cursor.close();
                break;

        }





    }
*/

