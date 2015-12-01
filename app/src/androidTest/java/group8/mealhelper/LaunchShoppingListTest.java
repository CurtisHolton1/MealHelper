package group8.mealhelper;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

/**
 * Created by thomasdail on 11/30/15.
 */
public class LaunchShoppingListTest
        extends ActivityUnitTestCase<HomeActivity> {

    private Intent mLaunchIntent;

    public LaunchShoppingListTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), HomeActivity.class);
    }

    @MediumTest
    public void testNextActivityWasLaunchedWithIntent() {
        startActivity(mLaunchIntent, null, null);
        final Button shoppingButton = (Button) getActivity().findViewById(R.id.home_shoppingListButton);
        //Because this is an isolated ActivityUnitTestCase we have to directly click the
        //button from code
        shoppingButton.performClick();

        // Get the intent for the next started activity
        final Intent launchIntent = getStartedActivityIntent();
        //Verify the intent was not null.
        assertNotNull("Intent was null", launchIntent);
        //Verify that LaunchActivity was finished after button click
        assertTrue(isFinishCalled());

    }
}
