package com.example.simpletodo;
import android.app.Activity;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditItemActivity extends AppCompatActivity {

    String position="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent intent = getIntent();
        position = intent.getStringExtra("position");
        String data = intent.getStringExtra("data");
        EditText tvUser = (EditText) findViewById(R.id.editText);
        tvUser.setText(data);
        int textLength = tvUser.getText().length();
        tvUser.setSelection(textLength, textLength);
        // Toast the name to display temporarily on screen
        //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }
    public void save(View v){


        EditText etNewItem=(EditText)findViewById(R.id.editText);
        String itemtext=etNewItem.getText().toString();
        //   Intent i = new Intent(EditItemActivity.this, MainActivity.class);
        //i.putExtra("data",itemtext);
        //i.putExtra("position",position);
        //setResult(RESULT_OK, i); // set result code and bundle data for response
        //finish();
        Intent returnIntent = getIntent();
        //returnIntent.putExtra("result",result);
        returnIntent.putExtra("data",itemtext);
        returnIntent.putExtra("position",position);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();

    }
}
