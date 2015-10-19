package group8.mealhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by curtis on 10/16/15.
 */
public class AddMealFragment extends Fragment implements View.OnClickListener {
    public static int IMAGE_CAPTURED = 1;
    ImageView imageView = null;
    Bitmap imageBitmap = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_meal, container, false);
        EditText mealName = (EditText) v.findViewById(R.id.addMeal_mealName_EditText);
        mealName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //validate text
                    //ie: make sure name isnt already used
                }
            }
        });
        Button cameraButton = (Button) v.findViewById(R.id.addMeal_cameraButton);
        cameraButton.setOnClickListener(this);
        imageView = (ImageView) v.findViewById(R.id.addMeal_imageView);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMeal_cameraButton:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, IMAGE_CAPTURED);
                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent cameraIntent) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURED) {
            Bundle extras = cameraIntent.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            // imageBitmap=null;
        }
    }
}
