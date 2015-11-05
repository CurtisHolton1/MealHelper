package group8.mealhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by curtis on 11/4/15.
 */


public class EditMealActivity extends FragmentActivity {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.edit_meal_fragment_container);
        if (fragment == null) {
            fragment = new EditMealFragment();
            fm.beginTransaction()
                    .add(R.id.edit_meal_fragment_container, fragment)
                    .commit();
        }
        Log.d(TAG, "onCreate METHOD CALLED+++");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume METHOD CALLED++");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause METHOD CALLED-");
    }

}
