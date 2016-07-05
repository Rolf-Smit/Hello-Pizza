package org.neotech.app.hellopizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.examples)));
        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Intent intent;
        switch(pos){
            case 0:
                intent = new Intent(this, LifecycleActivity.class);
                break;
            case 1:
                intent = new Intent(this, LifecycleActivityCompat.class);
                break;
            case 2:
                intent = new Intent(this, LeakActivity.class);
                break;
            case 3:
                intent = new Intent(this, RestoreActivity.class);
                break;
            default:
                throw new RuntimeException("Not implemented");
        }
        startActivity(intent);
    }
}
