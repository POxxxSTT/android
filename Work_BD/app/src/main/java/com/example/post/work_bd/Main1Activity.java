package com.example.post.work_bd;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.post.work_bd.DataBaseHelper.COL_FIRST;
import static com.example.post.work_bd.DataBaseHelper.COL_GROUP_ID;
import static com.example.post.work_bd.DataBaseHelper.COL_ID;
import static com.example.post.work_bd.DataBaseHelper.COL_LAST;
import static com.example.post.work_bd.DataBaseHelper.COL_MIDDLE;
import static com.example.post.work_bd.MainActivity.myDataBase;
import static com.example.post.work_bd.MainActivity.Click_ID;

public class Main1Activity extends AppCompatActivity  implements View.OnClickListener {

    public static String Click_ID_Stud;

    ArrayList<String> arr_stud = new ArrayList<>();
    ArrayList<Integer> arr_stud_id = new ArrayList<>();
    Button my_btn_up1, btnAddStud, btnDelStud;
    EditText etId, etLast, etFirst, etMiddle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);

        etId = (EditText) findViewById(R.id.etId);
        etLast = (EditText) findViewById(R.id.etLast);
        etFirst = (EditText) findViewById(R.id.etFirst);
        etMiddle = (EditText) findViewById(R.id.etMiddle);

        my_btn_up1 = (Button) findViewById(R.id.my_btn_up1);
        btnAddStud = (Button) findViewById(R.id.btnAddStud);
        btnDelStud = (Button) findViewById(R.id.btnDelStud);

        my_btn_up1.setOnClickListener(this);
        btnAddStud.setOnClickListener(this);
        btnDelStud.setOnClickListener(this);


        Cursor cursor = myDataBase.query("Student", null,"Group_id = ?", new String[] {Click_ID},null,null,null,null);
        if (cursor.moveToFirst()) {
            int collId = cursor.getColumnIndex(COL_ID) + 1;
            int colLast = cursor.getColumnIndex(COL_LAST);
            int colFirst = cursor.getColumnIndex(COL_FIRST);
            int colMiddle = cursor.getColumnIndex(COL_MIDDLE);
            int colGroupId = cursor.getColumnIndex(COL_GROUP_ID);

            do {

                arr_stud.add(cursor.getInt(collId) + "  " + cursor.getString(colLast) + "  " + cursor.getString(colFirst) + "  " + cursor.getString(colMiddle));
                arr_stud_id.add(cursor.getInt(collId));
            }while (cursor.moveToNext());

        }else
            Log.d("mLog", "БАЗА ДАННЫХ ПУСТАААААААААААААААААААААА");

        cursor.close();
        // получаем экземпляр элемента ListView
        ListView listView = (ListView)findViewById(R.id.listView1);

        // используем адаптер данных
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr_stud);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String str2 = String.valueOf(arr_stud_id.get(position));

                Click_ID_Stud = str2;


                Intent intent = new Intent(Main1Activity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    @Override
    public void onClick(View v) {
        String id = etId.getText().toString();
        String LastNane = etLast.getText().toString();
        String FirstName = etFirst.getText().toString();
        String MiddleName = etMiddle.getText().toString();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()){
            case R.id.my_btn_up1:
                Intent intent = new Intent(Main1Activity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAddStud:
                //contentValues.put("_id", id);
                contentValues.put("LastName", LastNane);
                contentValues.put("firstName", FirstName);
                contentValues.put("MiddleName", MiddleName);
                contentValues.put("Group_id", Click_ID);

                myDataBase.insert("Student", null, contentValues);
                restartActivity();
                break;
            case R.id.btnDelStud:
                if(id.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Введите ID кого хотите удолить.", Toast.LENGTH_SHORT).show();
                    break;
                }
                myDataBase.delete("Student", "_id = " + id, null);
                restartActivity();
                break;
        }



    }
}































































