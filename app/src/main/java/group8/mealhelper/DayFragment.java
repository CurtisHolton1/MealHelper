package group8.mealhelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        mBreakfastMealView = (SingleMeal)v.findViewById(R.id.day_breakfast_meal);
        mLunchMealView = (SingleMeal)v.findViewById(R.id.day_lunch_meal);
        mDinnerMealView = (SingleMeal)v.findViewById(R.id.day_dinner_meal);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.day_breakfast_button:
                Intent intent = new Intent(getActivity(),CookBookActivity.class);
                intent.putExtra("Mode","PickBreak");
                startActivityForResult(intent,RESULT_BREAKFAST_PICKED);
                break;
            case R.id.day_lunch_button:

                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        if(resultCode == Activity.RESULT_OK && requestCode == RESULT_BREAKFAST_PICKED){
            TextView t = (TextView)mBreakfastMealView.findViewById(R.id.single_meal_textView);
            mDay.setBreakfast((Meal) resultIntent.getExtras().getSerializable("MealResult"));
            t.setText(mDay.getBreakfast().getName());
            mBreakfastMealView.setVisibility(View.VISIBLE);
        }
    }

}
