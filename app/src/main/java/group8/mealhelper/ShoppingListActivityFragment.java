package group8.mealhelper;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import group8.mealhelper.models.Ingredient;
import group8.mealhelper.models.IngredientItem;
import group8.mealhelper.models.Ingredients;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingListActivityFragment extends Fragment {

    private RecyclerView mIngredientRecyclerView;
    private IngredientAdapter mAdapter;

    private class IngredientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitleTextView;
        private CheckBox mboughtCheckBox;
        private IngredientItem mIngredient;

        public IngredientHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_shopping_item_ingredient);
            mboughtCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_shopping_item_checkbox);
            mboughtCheckBox.setOnClickListener(this);
        }

        public void bindIngredientItem(IngredientItem i) {
            mIngredient = i;
            mTitleTextView.setText(i.getAmount() + " " + i.getUnits() + " " + i.getName());
            mboughtCheckBox.setChecked(i.isBought());
        }

        @Override
        public void onClick(View v) {
            switch ((v.getId())) {
                case R.id.list_shopping_item_checkbox:
                    mIngredient.toggleBought(getActivity());
                    break;
            }
        }
    }

    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {

        private List<IngredientItem> mIngredients;

        public IngredientAdapter(List<IngredientItem> ingredients) {
            mIngredients = ingredients;
        }

        @Override
        public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_shoping_item, parent, false);
            return new IngredientHolder(view);
        }

        @Override
        public void onBindViewHolder(IngredientHolder holder, int position) {
            IngredientItem i = mIngredients.get(position);
            //holder.mTitleTextView.setText(i.getAmount() + " " + i.getUnits() + " " + i.getName());
            holder.bindIngredientItem(i);
        }

        @Override
        public int getItemCount() {
            return mIngredients.size();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_list, container, false);

        mIngredientRecyclerView = (RecyclerView) v
                .findViewById(R.id.list_recycler_view);
        mIngredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {
        Ingredients ingredients = new Ingredients(getActivity());
        List<IngredientItem> ingredientList = ingredients.getIngredients();

        mAdapter = new IngredientAdapter(ingredientList);
        mIngredientRecyclerView.setAdapter(mAdapter);
    }
}
