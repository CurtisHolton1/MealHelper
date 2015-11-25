package group8.mealhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.models.Ingredient;

/**
 * Created by curtis on 10/16/15.
 * Home fragment handles button presses to new screens as well as facebook login
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CallbackManager callbackManager;
    ProfilePictureView mProfilePictureView;
    ProfileTracker profileTracker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        Button cookBook = (Button) v.findViewById(R.id.home_cookBookButton);
        cookBook.setOnClickListener(this);
        Button weeklyButton = (Button) v.findViewById(R.id.home_weeklyMenuButton);
        weeklyButton.setOnClickListener(this);
        Button shoppingButton = (Button) v.findViewById(R.id.home_shoppingListButton);
        shoppingButton.setOnClickListener(this);
        LoginButton loginButton = (LoginButton) v.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
        loginButton.setFragment(this);

        mProfilePictureView = (ProfilePictureView) v.findViewById(R.id.profile_picture);

        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App Code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_LONG)
                        .show();
            }

        });

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {

                if (currentProfile == null){
                    mProfilePictureView.setProfileId(null);
                } else {
                    CharSequence text = "Hello " + Profile.getCurrentProfile().getFirstName();
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT)
                            .show();
                    mProfilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                }
            }
        };

        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.home_cookBookButton:
                startActivity(new Intent(getActivity(), CookBookActivity.class));
                break;
            case R.id.home_weeklyMenuButton:
                startActivity(new Intent(getActivity(),WeeklyActivity.class));
                break;
            case R.id.home_shoppingListButton:
                startActivity(new Intent(getActivity(), ShoppingListActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }

}
