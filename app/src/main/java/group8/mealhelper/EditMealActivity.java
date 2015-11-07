package group8.mealhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import com.facebook.FacebookSdk;

import group8.mealhelper.models.Meal;

/**
 * Created by curtis on 10/16/15.
 */
public class EditMealActivity extends FragmentActivity {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Meal meal = (Meal)intent.getExtras().getSerializable("IncomingMeal");
        setContentView(R.layout.activity_edit_meal);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.editMeal_fragment_container);
        if (fragment == null) {
            fragment = new EditMealFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("IncomingMeal", meal);
            fragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.editMeal_fragment_container, fragment)
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
        Log.d(TAG, "onPause METHOD CALLED+");
    }
}
