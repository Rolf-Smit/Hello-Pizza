package org.neotech.app.hellopizza;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LeakActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LifecycleActivity";

    /**
     * Bad practice 1:
     * Making variables static in order to keep reference to the Activity's state is probably one of
     * the worst things you can do.
     *
     * In Android Activity's are design to be "reused" multiple times, and it might happen that an
     * Activity appears more than one time on the Activity stack.
     *
     * Example (Email):
     * 1. The user opens the email app (Activity A) and starts writing a new email (Activity B).
     * 2. The user presses the home button and writes a note.
     * 3. The user shares the note and decides to send the note by email to his friend.
     * 4. The system opens the "write new mail"  Activity (Activity B).
     *
     * Activity B now has to separate session instances on the same VM.
     *
     * Because static state is unique on VM level both the Activity sessions would share the same
     * state if Activity B was using static state.
     *
     * TLDR: DONT USE STATIC FIELDS IN ACTIVITY CLASSES, NEVER, JUST DON'T.
     *
     *  Note: The static properties of this field are not used/demonstrated in this example
     *  Activity.
     */
    private int pizzaCount = 1;

    /**
     * Bad practice 2:
     * Static Activity fields are forbidden (see bad practice 1). But static references to View's
     * are even worse. You should not only avoid static View references inside an Activity class
     * you should avoid them everywhere!
     *
     * View's are bound to the Context of the Activity meaning that if you keep reference to the
     * View you also keep reference to the Activity, leaking the Activity. In the worst cases this
     * leads to unrecoverable memory leaks.
     *
     * TLDR: NEVER KEEP STATIC REFERENCES TO VIEWS, NEVER NEVER EVER NEVER!!!!
     *
     * Note: The static properties of this field are not used/demonstrated in this
     * example Activity.
     */
    private TextView vPizzaCount;

    /**
     * Bad practise 3:
     * Just like Views you shouldn't keep reference to an Activity instance.
     */
    private static LeakActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Bad practise 3:
         * In this example the programmer tries to use the old Activity to restore view state.
         *
         * Observe that the old activity instance is leaked only for a short period; from the moment
         * Android discards the reference to it up to the moment the onCreate method is finished and
         * its memory stack is cleared.
         */
        LeakActivity oldActivity = activity;
        activity = this;
        if(oldActivity != null) {
            //Restore view state the bad way
            pizzaCount = oldActivity.pizzaCount;
        }

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
