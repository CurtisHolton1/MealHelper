package group8.mealhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by curtis on 10/16/15.
 */
public class AddMealActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.addMeal_fragment_container);
        if (fragment == null) {
            fragment = new AddMealFragment();
            fm.beginTransaction()
                    .add(R.id.addMeal_fragment_container, fragment)
                    .commit();
        }
    }
}
