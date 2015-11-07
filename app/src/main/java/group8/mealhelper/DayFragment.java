package group8.mealhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {

        }
    }
}
