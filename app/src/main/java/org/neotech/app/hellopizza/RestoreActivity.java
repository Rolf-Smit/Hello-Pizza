package org.neotech.app.hellopizza;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Rolf Smit on 02-Jul-16.
 */
public class RestoreActivity extends LifecycleActivityCompat implements View.OnClickListener {

    private static final String TAG = "RestoreActivity";

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
        onPizzaCountChanged();

        if(savedInstanceState == null){
            /**
             * If the saved instance state of this Activity is non null this is Activity isn't
             * recreated this is the start of a new "session".
             */

            /**
             * Most core Android APIs accept both CharSequence types and resource id's, like the
             * Toast API as shown below.
             *
             * Some functions don't accept resources id's in those cases you have to get the
             * text/string resource yourself using the android.content.res.Resources class. An
             * instance of this class can be obtain by calling on getResources() on any object which
             * inherits from the Context class (like Application, Activity, Service, etc.).
             *
             * getResources().getString(R.string.message_welcome);
             */

            Toast.makeText(this, R.string.message_welcome, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /**
         * Its preferred to restore view state after the views have been created in onCreate,
         * but in theory you could also do this "restoring" in the onCreate method. In some cases
         * it might also be logical to restore non view state in the onCreate method.
         *
         * Note: be aware of the fact that this method isn't called when the Activity saved instance
         * state does not exist (is null) the onCreate method will however always be called!
         */

        pizzaCount = savedInstanceState.getInt("pizzaCount");
        onPizzaCountChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pizzaCount", pizzaCount);
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
        switch (view.getId()) {
            case R.id.less:
                if (pizzaCount > 0) {
                    pizzaCount--;
                }
                break;
            case R.id.more:
                pizzaCount++; //Who is the first person to overflow this value and bypass the negative pizza count check? :)
                break;
        }
        onPizzaCountChanged();
    }

    private void onPizzaCountChanged(){
        /**
         * You could also name this method setCurrentPizzaCount but this method doesn't actually set
         * a value hence the name onPizzaCountChanged.
         */
        vPizzaCount.setText(getResources().getQuantityString(R.plurals.pizza_count, pizzaCount, pizzaCount));
    }
}
