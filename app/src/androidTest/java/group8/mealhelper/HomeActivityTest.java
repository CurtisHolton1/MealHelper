package group8.mealhelper;

import android.app.Application;
import android.content.Intent;
import android.test.ApplicationTestCase;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

public class HomeActivityTest extends ActivityInstrumentationTestCase2 <HomeActivity> {

    private HomeActivity mHomeActivity;
    private Button mShoppingButton;
    private Button mCookBookButton;
    private Button mWeeklyButton;

    public HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(true);

        mHomeActivity = getActivity();
        mShoppingButton =
                (Button) mHomeActivity
                        .findViewById(R.id.home_shoppingListButton);
        mCookBookButton =
                (Button) mHomeActivity
                        .findViewById(R.id.home_cookBookButton);
        mWeeklyButton =
                (Button) mHomeActivity
                        .findViewById(R.id.home_weeklyMenuButton);
    }

    public void testPreconditions() {
        assertNotNull("mHomeActivity_is_null", mHomeActivity);
        assertNotNull("mShoppingButton_is_null", mShoppingButton);
    }

    @MediumTest
    public void testShoppingButtonLayout() {
        final View decorView = mHomeActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mShoppingButton);

        final ViewGroup.LayoutParams layoutParams =
                mShoppingButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @MediumTest
    public void testCookBookButtonLayout() {
        final View decorView = mHomeActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mCookBookButton);

        final ViewGroup.LayoutParams layoutParams =
                mCookBookButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @MediumTest
    public void testWeeklyButtonLayout() {
        final View decorView = mHomeActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mWeeklyButton);

        final ViewGroup.LayoutParams layoutParams =
                mWeeklyButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.MATCH_PARENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    protected void tearDown() throws Exception {
        mHomeActivity.finish();
    }
}