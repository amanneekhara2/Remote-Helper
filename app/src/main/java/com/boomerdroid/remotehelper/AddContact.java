package com.boomerdroid.remotehelper;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    private EditText editname;
    private EditText editPhone;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        editname=(EditText)findViewById(R.id.editText);
        editPhone=(EditText) findViewById(R.id.editContact);
        save=findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("RemoteHelper",MODE_PRIVATE,null);
                    sqLiteDatabase.execSQL("CREATE TABLE " +
                            "IF NOT EXISTS trustedContacts (id INT PRIMARY KEY ,name VARCHAR ,contactNo VARCHAR(15) UNIQUE)");

                    sqLiteDatabase.execSQL("INSERT INTO trustedContacts (name,contactNo) VALUES (?,?)",
                            new String[]{editname.getText().toString(),editPhone.getText().toString()});

                    Toast.makeText(getApplicationContext(),"Trusted Contact Saved",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Trusted Contact Not Saved",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}
