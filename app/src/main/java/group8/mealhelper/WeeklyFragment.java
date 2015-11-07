package group8.mealhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group8.mealhelper.models.Day;

/**
 * Created by curtis on 11/6/15.
 */
public class WeeklyFragment extends Fragment implements View.OnClickListener {
    private List<Day> mDayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weekly, container, false);
        mDayList = new ArrayList<Day>();
        final DayListAdapter listAdapter = new DayListAdapter(getContext(), mDayList);
        ListView listView = (ListView) v.findViewById(R.id.weekly_listView);
        listView.setAdapter(listAdapter);
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            Day day = new Day();
            day.setName(df.format(cal.getTime()));
            mDayList.add(day);
            cal.add(Calendar.DATE, 1);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("Day", mDayList.get(position));
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {


        }
    }
}