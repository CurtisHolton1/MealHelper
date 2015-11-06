package group8.mealhelper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by curtis on 11/6/15.
 */
public class WeeklyFragment extends Fragment implements View.OnClickListener {
    private List<String> mDayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weekly, container, false);
        mDayList = new ArrayList<String>();
        DayListAdapter listAdapter = new DayListAdapter(getContext(), mDayList);
        ListView listView = (ListView) v.findViewById(R.id.weekly_listView);
        listView.setAdapter(listAdapter);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK,Calendar.DATE + 1);
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            mDayList.add(df.format(cal.getTime()));
            cal.add(Calendar.DATE, 1);
        }






        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {


        }
    }
}