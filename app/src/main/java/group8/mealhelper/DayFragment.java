package group8.mealhelper;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;
import group8.mealhelper.models.Day;
import group8.mealhelper.models.Meal;


/**
 * Created by curtis on 11/7/15.
 */
public class DayFragment extends Fragment implements View.OnClickListener{
    Day mDay;
    SingleMeal mBreakfastMealView;
    SingleMeal mLunchMealView;
    SingleMeal mDinnerMealView;
    private final int RESULT_BREAKFAST_PICKED = 1;
    private final int RESULT_LUNCH_PICKED = 2;
    private final int RESULT_DINNER_PICKED = 3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day, container, false);
        TextView title = (TextView) v.findViewById(R.id.day_title_textView);
        Bundle bundle = this.getArguments();
        mDay = (Day) bundle.getSerializable("Day");
        title.setText(mDay.getName());
        //if meal already exists in DB
        Button breakfastButton = (Button) v.findViewById(R.id.day_breakfast_button);
        breakfastButton.setOnClickListener(this);
        Button lunchButton = (Button) v.findViewById(R.id.day_lunch_button);
        lunchButton.setOnClickListener(this);
        Button dinnerButton = (Button) v.findViewById(R.id.day_dinner_button);
        dinnerButton.setOnClickListener(this);
        mBreakfastMealView = (SingleMeal)v.findViewById(R.id.day_breakfast_meal);
        mLunchMealView = (SingleMeal)v.findViewById(R.id.day_lunch_meal);
        mDinnerMealView = (SingleMeal)v.findViewById(R.id.day_dinner_meal);
        if(mDay.getId() !=null){
            if(mDay.getBreakfast()!=null){
                setBreakfastView(mDay.getBreakfast());
            }
            if(mDay.getLunch()!=null){
                setLunchView(mDay.getLunch());
            }
            if(mDay.getDinner()!=null){
                setDinnerView(mDay.getDinner());
            }
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.day_breakfast_button:
                Intent breakfastIntent = new Intent(getActivity(),CookBookActivity.class);
                breakfastIntent.putExtra("Mode","PickBreak");
                startActivityForResult(breakfastIntent,RESULT_BREAKFAST_PICKED);
                break;
            case R.id.day_lunch_button:
                Intent lunchIntent = new Intent(getActivity(),CookBookActivity.class);
                lunchIntent.putExtra("Mode", "PickBreak");
                startActivityForResult(lunchIntent,RESULT_LUNCH_PICKED);
                break;
            case R.id.day_dinner_button:
                Intent dinnerIntent = new Intent(getActivity(),CookBookActivity.class);
                dinnerIntent.putExtra("Mode", "PickBreak");
                startActivityForResult(dinnerIntent,RESULT_DINNER_PICKED);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        DbHelper dbHelper= new DbHelper(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        if(resultCode == Activity.RESULT_OK && requestCode == RESULT_BREAKFAST_PICKED){
            mDay.setBreakfast((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            setBreakfastView(mDay.getBreakfast());
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == RESULT_LUNCH_PICKED){
            mDay.setLunch((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            setLunchView(mDay.getLunch());
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == RESULT_DINNER_PICKED){
            mDay.setDinner((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            setDinnerView(mDay.getDinner());
        }
        ContentValues values = mDay.getContentValues();
        if(mDay.getId()!=null){
            database.update(DbSchema.MenuTable.NAME,values, DbSchema.MenuTable.Cols.Menu_ID + " = " + mDay.getId(),null);
        }
        else {
            long generatedId = database.insert(DbSchema.MenuTable.NAME, null, values);
            mDay.setId(Long.toString(generatedId));
        }
        database.close();
    }

    private void setBreakfastView(Meal breakfast){
        TextView textView = (TextView)mBreakfastMealView.findViewById(R.id.single_meal_textView);
        textView.setText(breakfast.getName());
        ImageView imageView = (ImageView) mBreakfastMealView.findViewById(R.id.single_meal_image);
        String[] path = {breakfast.getPathToPic()};
        new SetPicTask(imageView).execute(path);
        mBreakfastMealView.setVisibility(View.VISIBLE);
    }

    private void setLunchView(Meal lunch){
        TextView textView = (TextView)mLunchMealView.findViewById(R.id.single_meal_textView);
        textView.setText(lunch.getName());
        ImageView imageView = (ImageView) mLunchMealView.findViewById(R.id.single_meal_image);
        String[] path = {lunch.getPathToPic()};
        new SetPicTask(imageView).execute(path);
        mLunchMealView.setVisibility(View.VISIBLE);
    }

    private void setDinnerView(Meal dinner){
        TextView textView = (TextView)mDinnerMealView.findViewById(R.id.single_meal_textView);
        textView.setText(dinner.getName());
        ImageView imageView = (ImageView) mDinnerMealView.findViewById(R.id.single_meal_image);
        String[] path = {dinner.getPathToPic()};
        new SetPicTask(imageView).execute(path);
        mDinnerMealView.setVisibility(View.VISIBLE);
    }

    private class SetPicTask extends AsyncTask<String,Void,Bitmap> {
        ImageView mImageView;
        public SetPicTask(ImageView imageView){
            this.mImageView = imageView;
        }
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Bitmap doInBackground(String... params){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 32;
            Bitmap bitmap = BitmapFactory.decodeFile(params[0], options);
            return  bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            mImageView.setImageBitmap(result);
        }
    }

}
