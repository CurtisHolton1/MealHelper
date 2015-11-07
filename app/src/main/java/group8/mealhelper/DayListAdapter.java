package group8.mealhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import group8.mealhelper.models.Day;
import group8.mealhelper.models.Ingredient;

/**
 * Created by curtis on 11/6/15.
 */
public class DayListAdapter extends ArrayAdapter {

    private final Context mContext;
    private final List<Day> mDays;

    public DayListAdapter(Context context, List<Day> days) {
        super(context, -1, days);
        this.mContext = context;
        this.mDays = days;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.weekly_list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.weekly_textView);
        textView.setText(mDays.get(position).getName());
        return rowView;
    }

}
