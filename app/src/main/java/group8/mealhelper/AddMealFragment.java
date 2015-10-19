package group8.mealhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by curtis on 10/16/15.
 */
public class AddMealFragment extends Fragment implements View.OnClickListener {
    private final String TAG = ((Object) this)
            .getClass()
            .getSimpleName().toUpperCase();
    public static int IMAGE_CAPTURED = 1;
    public final static String APP_PATH_SD_CARD = "/MealHelper";
    ImageView imageView = null;
    Bitmap mImageBitmap = null;
    String mMealName = null;

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
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    //Clear focus here from edittext
                    mealNameField.clearFocus();
                }
                return false;
            }
        });
        mealNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    mMealName = mealNameField.getText().toString();
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
            mImageBitmap = (Bitmap) extras.get("data");
            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD;

            try {
                File dir = new File(fullPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date()).replace(" ","");
                OutputStream fOut = null;
                File file = new File(fullPath, currentDateandTime + ".png");
                file.createNewFile();
                fOut = new FileOutputStream(file);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                // 100 means no compression, the lower you go, the stronger the compression
                mImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                fOut.write(bytes.toByteArray());
                fOut.flush();
                fOut.close();
            }catch (Exception e){
                Log.d(TAG, "Exception in AddMealFragment: onActivityResult");
            }
            imageView.setImageBitmap(mImageBitmap);
            // imageBitmap=null;
        }
    }
}
