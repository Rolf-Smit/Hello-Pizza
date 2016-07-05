package org.neotech.app.hellopizza;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Rolf Smit on 02-Jul-16.
 */
public class LifecycleActivityCompat extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LifecycleActivity";

    private int pizzaCount = 1;
    private TextView vPizzaCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " onCreate(" + savedInstanceState + ")");
        setContentView(R.layout.activity_lifecycle);
        vPizzaCount = (TextView) findViewById(R.id.count);

        //Make the less and more buttons clickable
        ImageButton button = (ImageButton) findViewById(R.id.more);
        button.setOnClickListener(this);

        //This also works as every view can be made clickable
        findViewById(R.id.less).setOnClickListener(this);

        //Display the current pizza count on the screen
        vPizzaCount.setText(getResources().getQuantityString(R.plurals.pizza_count, pizzaCount, pizzaCount));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, " onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, " onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, " onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, " onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, " onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " onDestroy(isFinishing: " + isFinishing() + ")");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.less:
                if(pizzaCount > 0) {
                    pizzaCount--;
                }
                break;
            case R.id.more:
                pizzaCount++; //Who is the first person to overflow this value and bypass the negative pizza count check? :)
                break;
        }
        vPizzaCount.setText(getResources().getQuantityString(R.plurals.pizza_count, pizzaCount, pizzaCount));
    }
}
