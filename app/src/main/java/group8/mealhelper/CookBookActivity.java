package group8.mealhelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CookBookActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.cookBook_fragment_container);

        if (fragment == null) {
            fragment = new CookBookFragment();
            fm.beginTransaction()
                    .add(R.id.cookBook_fragment_container, fragment)
                    .commit();
        }
    }

}
