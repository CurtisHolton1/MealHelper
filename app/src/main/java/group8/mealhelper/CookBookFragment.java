package group8.mealhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import group8.mealhelper.models.CookBook;
import group8.mealhelper.models.Meal;


/**
 * Created by curtis on 10/16/15.
 */
public class CookBookFragment extends Fragment implements View.OnClickListener {
    CookBook mCookBook = null;
    CookBookListAdapter mListAdapter;
    ListView mMealListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume(){
        super.onResume();
        mCookBook = CookBook.get(getContext());
        mListAdapter = new CookBookListAdapter(getContext(), mCookBook.getAllMeals());
        mMealListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cook_book, container, false);
        mMealListView = (ListView) v.findViewById(R.id.cookBook_listView);
        mMealListView.setAdapter(mListAdapter);
        Button addNew = (Button) v.findViewById(R.id.cookBook_addNewButton);
        addNew.setOnClickListener(this);
       mMealListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(),EditMealActivity.class);
               intent.putExtra("IncomingMeal", mListAdapter.getItem(position));
               startActivity(intent);

           }
       });
        return v;
    }


    @Override
    public void onClick(View v) {
        switch ((v.getId())) {
            case R.id.cookBook_addNewButton:
                Intent i = new Intent(getActivity(), EditMealActivity.class);
                i.putExtra("IncomingMeal", new Meal());
                startActivity(i);
                break;
        }
    }
}
