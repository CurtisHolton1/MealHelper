package group8.mealhelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;
import group8.mealhelper.database.DbSchema.MealTable;
import group8.mealhelper.models.Ingredient;
import group8.mealhelper.models.Meal;

/**
 * Created by curtis on 10/16/15.
 * Handles the editing and adding new meals
 */

public class EditMealFragment extends Fragment implements View.OnClickListener {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();
    public static int IMAGE_CAPTURED = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static String APP_PATH_SD_CARD = "/MealHelper";
    View mView;
    IngredientListAdapter mListAdapter;
    List<Ingredient> mIngredientList = new ArrayList<>();
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
        Bundle bundle = this.getArguments();
        mView = inflater.inflate(R.layout.fragment_edit_meal, container, false);
        mTempMeal = (Meal) bundle.getSerializable("IncomingMeal");
        if(mTempMeal.getIngredientList()!=null) {
            mIngredientList = mTempMeal.getIngredientList();
            EditText mealNameField = (EditText) mView.findViewById(R.id.editMeal_mealName_EditText);
            mealNameField.setText(mTempMeal.getName());
            EditText recipeField = (EditText) mView.findViewById(R.id.editMeal_recipeText);
            recipeField.setText(mTempMeal.getRecipe());
        }

        Button cameraButton = (Button) mView.findViewById(R.id.editMeal_cameraButton);
        cameraButton.setOnClickListener(this);
        ImageView imageView = (ImageView) mView.findViewById(R.id.editMeal_imageView);
        new setPicTask(imageView).execute();
        mUnitSpinner = (Spinner) mView.findViewById(R.id.editMeal_ingredientTable_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitSpinner.setAdapter(adapter);
        mCheckBox = (CheckBox) mView.findViewById(R.id.editMeal_ingredientTable_checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ingredientCheck();
            }
        });
        ListView ingredientListView = (ListView) mView.findViewById(R.id.editMeal_ingredientList);
        mListAdapter = new IngredientListAdapter(getContext(), mIngredientList);
        ingredientListView.setAdapter(mListAdapter);
        ingredientListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        Button submit = (Button) mView.findViewById(R.id.editMeal_submitButton);
        submit.setOnClickListener(this);
        /*
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();
                */

//        Bitmap image = BitmapFactory.decodeFile(mTempMeal.getPathToPic());
//        SharePhoto photo = new SharePhoto.Builder()
//                .setBitmap(image)
//                .build();
//        SharePhotoContent content = new SharePhotoContent.Builder()
//                .addPhoto(photo)
//                .build();
//        ShareButton shareButton = (ShareButton) mView.findViewById(R.id.editMeal_share_btn);
//        shareButton.setShareContent(content);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editMeal_cameraButton:
                cameraButtonClick();
                break;
            case R.id.editMeal_submitButton:
                submitButtonClick();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent cameraIntent) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURED) {
            ImageView imageView = (ImageView) mView.findViewById(R.id.editMeal_imageView);
            new setPicTask(imageView).execute();
        }
    }

    private void cameraButtonClick() {
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
        if (!mealDir.exists()) {
            mealDir.mkdir();
        }
        File image = File.createTempFile(imageFileName, ".jpg", mealDir);
        mTempMeal.setPathToPic(image.getAbsolutePath());
        return image;
    }

/*
validates ingredient before saving to DB
 */
    private void ingredientCheck() {
        EditText ingredientField = (EditText) mView.findViewById(R.id.editMeal_ingredientTable_editText);
        mTempIngredient.setName(ingredientField.getText().toString());
        mTempIngredient.setUnits(mUnitSpinner.getSelectedItem().toString());
        EditText ingredientAmountField = (EditText) mView.findViewById(R.id.editMeal_ingredientTable_editText2);
        if (!ingredientAmountField.getText().toString().isEmpty())
            mTempIngredient.setAmount(Double.parseDouble(ingredientAmountField.getText().toString()));
        if (mTempIngredient.isValid()) {
            if (!mIngredientList.contains(mTempIngredient)) {
                mIngredientList.add(mTempIngredient);
                mTempMeal.setIngredientList(mIngredientList);
                mListAdapter.notifyDataSetChanged();
            } else {
                Context context = getContext();
                CharSequence text = "Ingredient already added";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            mTempIngredient = new Ingredient();
            mTempIngredient.setUnits(mUnitSpinner.getSelectedItem().toString());
            clearIngredientFields();
        } else {
            Context context = getContext();
            CharSequence text = "Invalid ingredient";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            mCheckBox.toggle();
        }
    }

    private void clearIngredientFields() {
        mTempIngredient = new Ingredient();
        mTempIngredient.setUnits(mUnitSpinner.getSelectedItem().toString());
        EditText e = (EditText) mView.findViewById(R.id.editMeal_ingredientTable_editText);
        e.setText("");
        e = (EditText) mView.findViewById(R.id.editMeal_ingredientTable_editText2);
        e.setText("");
        CheckBox c = (CheckBox) mView.findViewById(R.id.editMeal_ingredientTable_checkBox);
        if (c.isChecked())
            c.toggle();
    }


    private void submitButtonClick() {
        //if valid save to DB
        SQLiteDatabase database = new DbHelper(getContext()).getWritableDatabase();
        EditText mealNameField = (EditText) mView.findViewById(R.id.editMeal_mealName_EditText);
        mTempMeal.setName(mealNameField.getText().toString());
        EditText recipeField = (EditText) mView.findViewById(R.id.editMeal_recipeText);
        mTempMeal.setRecipe(recipeField.getText().toString());
        if (mTempMeal.isValid()){
            Context context = getContext();
            CharSequence text = "Adding Meal";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            ContentValues values = mTempMeal.getContentValues();
            if(mTempMeal.getId()!= null){
                database.update(MealTable.NAME, values,MealTable.Cols.MEAL_ID + " = " + mTempMeal.getId(),null);
            }else {
                long mealId = database.insert(MealTable.NAME, null, values);
                mTempMeal.setId(Long.toString(mealId));
            }
            //insert ingredients
            for (int j =0; j< mTempMeal.getIngredientList().size(); j++) {
               Ingredient i = mTempMeal.getIngredientList().get(j);
                i.setMealId(mTempMeal.getId());
                ContentValues ingredientValues = i.getContentValues();
                if(i.getIngredientId()!=null){
                    database.update(DbSchema.IngredientTable.NAME,ingredientValues, DbSchema.IngredientTable.Cols.INGREDIENT_ID  + " = " + i.getIngredientId(),null);
                }
                else{
                    database.insert(DbSchema.IngredientTable.NAME, null, ingredientValues);
                }
            }
            database.close();
            this.getActivity().finish();
        }
        else {
            Context context = getContext();
            CharSequence text = "Invalid Meal";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
    /*
    Async task to get local image file is not done on the main thread
     */
    private class setPicTask extends AsyncTask<String,Void,Bitmap> {
        ImageView mImageView;
        public setPicTask(ImageView imageView){
            this.mImageView = imageView;
        }
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Bitmap doInBackground(String... params){
            if(mTempMeal.getPathToPic()!=null) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 16;
                Bitmap bitmap = BitmapFactory.decodeFile(mTempMeal.getPathToPic(), bmOptions);
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(result)
                    .build();
            SharePhotoContent content = new SharePhotoContent.Builder()
                    .addPhoto(photo)
                    .build();
            ShareButton shareButton = (ShareButton) mView.findViewById(R.id.editMeal_share_btn);
            shareButton.setShareContent(content);
            mImageView.setImageBitmap(result);
        }
    }



}