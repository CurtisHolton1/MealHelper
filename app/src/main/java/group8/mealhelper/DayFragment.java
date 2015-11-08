package group8.mealhelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

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
        Button breakfastButton = (Button) v.findViewById(R.id.day_breakfast_button);
        breakfastButton.setOnClickListener(this);
        Button lunchButton = (Button) v.findViewById(R.id.day_lunch_button);
        lunchButton.setOnClickListener(this);
        Button dinnerButton = (Button) v.findViewById(R.id.day_dinner_button);
        dinnerButton.setOnClickListener(this);
        mBreakfastMealView = (SingleMeal)v.findViewById(R.id.day_breakfast_meal);
        mLunchMealView = (SingleMeal)v.findViewById(R.id.day_lunch_meal);
        mDinnerMealView = (SingleMeal)v.findViewById(R.id.day_dinner_meal);
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
        if(resultCode == Activity.RESULT_OK && requestCode == RESULT_BREAKFAST_PICKED){
            TextView textView = (TextView)mBreakfastMealView.findViewById(R.id.single_meal_textView);
            mDay.setBreakfast((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            textView.setText(mDay.getBreakfast().getName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            Bitmap bitmap = BitmapFactory.decodeFile(mDay.getBreakfast().getPathToPic(), options);
            ImageView imageView = (ImageView) mBreakfastMealView.findViewById(R.id.single_meal_image);
            imageView.setImageBitmap(bitmap);
            mBreakfastMealView.setVisibility(View.VISIBLE);
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == RESULT_LUNCH_PICKED){
            TextView textView = (TextView)mLunchMealView.findViewById(R.id.single_meal_textView);
            mDay.setLunch((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            textView.setText(mDay.getLunch().getName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            Bitmap bitmap = BitmapFactory.decodeFile(mDay.getLunch().getPathToPic(), options);
            ImageView imageView = (ImageView) mLunchMealView.findViewById(R.id.single_meal_image);
            imageView.setImageBitmap(bitmap);
            mLunchMealView.setVisibility(View.VISIBLE);
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == RESULT_DINNER_PICKED){
            TextView t = (TextView)mDinnerMealView.findViewById(R.id.single_meal_textView);
            mDay.setDinner((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            t.setText(mDay.getDinner().getName());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            Bitmap bitmap = BitmapFactory.decodeFile(mDay.getDinner().getPathToPic(), options);
            ImageView imageView = (ImageView) mDinnerMealView.findViewById(R.id.single_meal_image);
            imageView.setImageBitmap(bitmap);
            mDinnerMealView.setVisibility(View.VISIBLE);
        }
    }

}
