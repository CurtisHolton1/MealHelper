package group8.mealhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.List;

import group8.mealhelper.models.Ingredient;

/**
 * Created by curtis on 10/25/15.
 */
public class IngredientListAdapter extends ArrayAdapter<Ingredient> {
    private final Context mContext;
    private final List<Ingredient> mIngredients;

    public IngredientListAdapter(Context context, List<Ingredient> ingredients){
        super(context,-1,ingredients);
        this.mContext = context;
        this.mIngredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.ingredient_list_row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.ingredient_list_row_text);
        textView.setText(mIngredients.get(position).getName());
        Button button = (Button) rowView.findViewById(R.id.ingredient_list_row_button);
        return rowView;
    }
}
