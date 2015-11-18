package group8.mealhelper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;
import group8.mealhelper.models.Day;
import group8.mealhelper.models.Ingredient;
import group8.mealhelper.models.IngredientItem;
import group8.mealhelper.models.Menu;

/**
 * Created by curtis on 11/6/15.
 */
public class WeeklyFragment extends Fragment implements View.OnClickListener {
    private List<Day> mDayList;
    private DayListAdapter mListAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weekly, container, false);
        mDayList = new ArrayList<Day>();
        mListAdapter = new DayListAdapter(getContext(), mDayList);
        ListView listView = (ListView) v.findViewById(R.id.weekly_listView);
        listView.setAdapter(mListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DayActivity.class);
                intent.putExtra("Day", mDayList.get(position));
                startActivity(intent);
            }
        });

        Button generate = (Button) v.findViewById(R.id.weekly_generateShoppingButton);
        generate.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.weekly_generateShoppingButton:
                fillShoppingList();
                Toast.makeText(getActivity(), "TEST", Toast.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mDayList.clear();
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE MM/dd/yyyy");
        for (int i = 0; i < 7; i++) {
            Day day = new Day();
            day.setName(df.format(cal.getTime()));
            Day dayFromDB = Menu.get(getContext()).getDay(day.getName());
            if(dayFromDB != null){
                day = dayFromDB;
            }
            mDayList.add(day);
            cal.add(Calendar.DATE, 1);
        }

    }


    private void fillShoppingList() {
        SQLiteDatabase database = new DbHelper(getActivity().getApplicationContext())
                .getWritableDatabase();
        database.execSQL("delete from " + DbSchema.ShoppingListTable.NAME);
        List<IngredientItem> ingredients = new ArrayList<IngredientItem>();
        fillMeal(ingredients, "breakfast");
        fillMeal(ingredients, "lunch");
        fillMeal(ingredients, "dinner");
        writeListToDB(ingredients);
    }

    private void fillMeal(List<IngredientItem> list, String meal) {
        SQLiteDatabase database = new DbHelper(getActivity().getApplicationContext())
                .getWritableDatabase();
        String q = "SELECT ingredients.name, ingredients.unit, ingredients.amount " +
                "FROM ingredients, meals, menu " +
                "WHERE ingredients.mealId = meals.id AND meals.id = menu."+meal+"Id";
        Cursor cursor = database.rawQuery(q, null);

        try {
            cursor.moveToFirst();
            int id = 0;
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.NAME));
                double amount = cursor.getDouble(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.AMOUNT));
                String unit = cursor.getString(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.UNIT));
                IngredientItem i = new IngredientItem(id ,name, amount, unit, false);

                int index = indexOfIngredient(list, i);
                if (index >= 0){
                    IngredientItem duplicate = list.get(index);
                    duplicate.incrementAmount(i.getAmount());
                } else {
                    list.add(i);
                    id++;
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    private static int indexOfIngredient(List<IngredientItem> l, IngredientItem i) {
        int index = -1;
        for (IngredientItem next : l) {
            if (next.getName().equals(i.getName())) {
                index = l.indexOf(next);
            }
        }
        return index;
    }

    private void writeListToDB(List<IngredientItem> list) {
        SQLiteDatabase database = new DbHelper(getActivity().getApplicationContext())
                .getWritableDatabase();
        int id = 0;
        for (IngredientItem i : list) {
            ContentValues values = getContentValues(i);
            database.insert(DbSchema.ShoppingListTable.NAME, null, values);
            id++;
        }
    }

    private static ContentValues getContentValues(IngredientItem i) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ShoppingListTable.Cols.SHOPPINGLIST_ID, i.getId());
        values.put(DbSchema.ShoppingListTable.Cols.NAME, i.getName());
        values.put(DbSchema.ShoppingListTable.Cols.AMOUNT, i.getAmount());
        values.put(DbSchema.ShoppingListTable.Cols.UNIT, i.getUnits());
        values.put(DbSchema.ShoppingListTable.Cols.BOUGHT, "FALSE");
        return values;
    }
}