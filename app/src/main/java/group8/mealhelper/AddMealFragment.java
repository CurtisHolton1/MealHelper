package group8.mealhelper;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
 */

public class AddMealFragment extends Fragment implements View.OnClickListener {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();
    public static int IMAGE_CAPTURED = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final static String APP_PATH_SD_CARD = "/MealHelper";
    RelativeLayout mLayout;
    View mView;
    String mCurrentPhotoPath = null;
    ImageView mImageView = null;
    EditText mMealNameField;
    EditText mIngredientField;
    EditText mIngredientAmountField;
    EditText mRecipeField;
    ListView mIngredientListView;
    IngredientListAdapter mListAdapter;
    List<Ingredient> mIngredientList = new ArrayList<Ingredient>();
    Spinner mUnitSpinner;
    CheckBox mCheckBox = null;
    Ingredient mTempIngredient = new Ingredient();
    Meal mTempMeal = new Meal();
    SQLiteDatabase  mDatabase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_add_meal, container, false);
        mLayout = (RelativeLayout) mView.findViewById(R.id.addMeal_Layout);
        mDatabase = new DbHelper(getContext()).getWritableDatabase();
        mMealNameField = (EditText) mView.findViewById(R.id.addMeal_mealName_EditText);
        Button cameraButton = (Button) mView.findViewById(R.id.addMeal_cameraButton);
        cameraButton.setOnClickListener(this);
        mImageView = (ImageView) mView.findViewById(R.id.addMeal_imageView);
        mIngredientField = (EditText) mView.findViewById(R.id.addMeal_ingredientTable_editText);
        mIngredientAmountField = (EditText) mView.findViewById(R.id.addMeal_ingredientTable_editText2);
        mUnitSpinner = (Spinner) mView.findViewById(R.id.addMeal_ingredientTable_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnitSpinner.setAdapter(adapter);
        mCheckBox = (CheckBox) mView.findViewById(R.id.addMeal_ingredientTable_checkBox);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ingredientCheck();
            }
        });
        mIngredientListView = (ListView) mView.findViewById(R.id.addMeal_ingredientList);
        mListAdapter = new IngredientListAdapter(getContext(), mIngredientList);
        mIngredientListView.setAdapter(mListAdapter);
        mIngredientListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
         mRecipeField = (EditText) mView.findViewById(R.id.addMeal_recipeText);
        Button submit = (Button) mView.findViewById(R.id.addMeal_submitButton);
        submit.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMeal_cameraButton:
                cameraButtonClick();
                break;
            case R.id.addMeal_submitButton:
                submitButtonClick();
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent cameraIntent) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_CAPTURED) {
            setPic();
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
        mTempMeal.setPathToPic(mCurrentPhotoPath);
    }

    private void ingredientCheck() {
        mTempIngredient.setName(mIngredientField.getText().toString());
        mTempIngredient.setUnits(mUnitSpinner.getSelectedItem().toString());
        if (!mIngredientAmountField.getText().toString().isEmpty())
            mTempIngredient.setAmount(Double.parseDouble(mIngredientAmountField.getText().toString()));
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
        EditText e = (EditText) mView.findViewById(R.id.addMeal_ingredientTable_editText);
        e.setText("");
        e = (EditText) mView.findViewById(R.id.addMeal_ingredientTable_editText2);
        e.setText("");
        CheckBox c = (CheckBox) mView.findViewById(R.id.addMeal_ingredientTable_checkBox);
        if (c.isChecked())
            c.toggle();
    }
    private void submitButtonClick(){
        //if valid save to DB
        mTempMeal.setName(mMealNameField.getText().toString());
        mTempMeal.setRecipe(mRecipeField.getText().toString());
        if (mTempMeal.isValid()){
            Context context = getContext();
            CharSequence text = "Adding Meal";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            ContentValues values = new ContentValues();
            values.put(MealTable.Cols.NAME, mTempMeal.getName());
            values.put(MealTable.Cols.PIC_PATH, mTempMeal.getPathToPic());
            values.put(MealTable.Cols.RECIPE, mTempMeal.getRecipe());
            long mealId= mDatabase.insert(MealTable.NAME, null,values);

            //insert ingredients
            values = new ContentValues();
            for (Ingredient i:mTempMeal.getIngredientList()) {
                values.put(DbSchema.IngredientTable.Cols.NAME, i.getName());
                values.put(DbSchema.IngredientTable.Cols.AMOUNT,i.getAmount());
                values.put(DbSchema.IngredientTable.Cols.UNIT,i.getUnits());
                values.put(DbSchema.IngredientTable.Cols.MEAL_ID, mealId);
                mDatabase.insert(DbSchema.IngredientTable.NAME, null, values);
            }

            //// TODO: 11/3/15 leave activity 
        }
        else {
            Context context = getContext();
            CharSequence text = "Invalid Meal";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}