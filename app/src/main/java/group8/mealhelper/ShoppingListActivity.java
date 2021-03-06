package group8.mealhelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ShoppingListActivity extends FragmentActivity {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.shopping_list_fragment_container);

        if (fragment == null) {
            fragment = new ShoppingListActivityFragment();
            fm.beginTransaction()
                    .add(R.id.shopping_list_fragment_container, fragment)
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
