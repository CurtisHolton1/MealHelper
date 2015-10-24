package group8.mealhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Map;

import group8.mealhelper.models.Ingredient;
import group8.mealhelper.models.Meal;

/**
 * Created by curtis on 10/16/15.
 */
public class AddMealFragment extends Fragment implements View.OnClickListener {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();
    public static int IMAGE_CAPTURED = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static String APP_PATH_SD_CARD = "/MealHelper";
    String mCurrentPhotoPath = null;
    ImageView mImageView = null;
    Spinner mUnitSpinner;
    CheckBox mCheckBox = null;
    Ingredient mTempIngredient = new Ingredient();
    Meal mTempMeal = new Meal();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_meal, container, false);
        final EditText mealNameField = (EditText) v.findViewById(R.id.addMeal_mealName_EditText);
        mealNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mealNameField.clearFocus();
                }
                return false;
            }
        });
        mealNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mTempMeal.setName(mealNameField.getText().toString());
                }
            }
        });
        Button cameraButton = (Button) v.findViewById(R.id.addMeal_cameraButton);
        cameraButton.setOnClickListener(this);
        mImageView = (ImageView) v.findViewById(R.id.addMeal_imageView);
        final EditText ingredientField = (EditText) v.findViewById(R.id.addMeal_ingredientTable_editText);
        ingredientField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ingredientField.clearFocus();
                }
                return false;
            }
        });
        ingredientField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mTempIngredient.setName(ingredientField.getText().toString());
                }
            }
        });
        final EditText ingredientAmountField = (EditText) v.findViewById(R.id.addMeal_ingredientTable_editText2);
        ingredientField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    ingredientAmountField.clearFocus();
                }
                return false;
            }
        });
        ingredientAmountField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!ingredientAmountField.getText().toString().isEmpty())
                        mTempIngredient.setAmount(Double.parseDouble(ingredientAmountField.getText().toString()));
                }
            }
        });

        mUnitSpinner = (Spinner) v.findViewById(R.id.addMeal_ingredientTable_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitSpinner.setAdapter(adapter);
        mUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTempIngredient.setUnits(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         mCheckBox = (CheckBox) v.findViewById(R.id.addMeal_ingredientTable_checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    handleIngredientCheck();
            }
        });
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMeal_cameraButton:
                handleCameraButtonClick();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent cameraIntent) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURED) {
            setPic();
        }
    }

    private void handleCameraButtonClick() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.d(TAG, "Exception onActivityResult");
        }
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File mealDir = new File(storageDir + APP_PATH_SD_CARD);
        File image = File.createTempFile(imageFileName, ".jpg", mealDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void setPic() {
        // Get the dimensions of the View
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageView.setImageBitmap(bitmap);
    }
    private void handleIngredientCheck(){
        if(mTempIngredient != null
                && mTempIngredient.getName() != null
                && !mTempIngredient.getName().isEmpty()
                && mTempIngredient.getAmount() != 0
                && mTempIngredient.getUnits() != null
                && !mTempIngredient.getUnits().isEmpty())
        {
                    Log.d("blah", "Valid Ingredient");
        }
        else{
            Log.d("blah2", "Invalid Ingredient");
            mCheckBox.toggle();
        }
    }

}