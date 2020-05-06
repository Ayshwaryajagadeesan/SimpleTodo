package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends Activity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    // Attaches a long click listener to the listview
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ADD HERE
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<String>();
        readItems(); // <---- Add this line
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
        setupListViewListener1();
    }
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        // Remove the item within array at position
                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        writeItems();
                        return true;
                    }

                });
    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
    private void setupListViewListener1() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                String value = Integer.toString(position);
                String selectedFromList = (items.get(position));
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", value);
                i.putExtra("data", selectedFromList);// pass arbitrary data to launched activity
                //Intent i = new Intent(this, SecondActivity.class);
                startActivityForResult(i, 1);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String position1 = data.getStringExtra("position");
                String data1 = data.getStringExtra("data");

                //System.out.println(position1);
                //System.out.println(data1);
                int currentIndex = new Integer(position1) ;
                items.set(currentIndex,data1);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
            // Extract name value from result extras
            // Intent intent = getIntent();
            //  String data1 = data111111111.getExtras().getString("data");
            //String position = data.getExtras().getString("position");

            //int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        }
    }
}
