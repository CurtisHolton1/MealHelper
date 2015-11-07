package group8.mealhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.jar.Attributes;

import group8.mealhelper.models.Day;

/**
 * Created by curtis on 11/7/15.
 */
public class DayFragment extends Fragment implements View.OnClickListener{
    Day mDay;

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
        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.day_breakfast_button:
                Intent intent = new Intent(getActivity(),CookBookActivity.class);
                intent.putExtra("Mode","PickMeal");
                startActivity(intent);
                break;
        }
    }
}
