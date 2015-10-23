package group8.mealhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import group8.mealhelper.database.DbHelper;

/**
 * Created by curtis on 10/16/15.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Button cookBook = (Button) v.findViewById(R.id.home_cookBookButton);
        cookBook.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v){
        switch ((v.getId())){
            case R.id.home_cookBookButton:
                Intent i = new Intent(getActivity(), CookBookActivity.class);
                startActivity(i);
                break;
        }
    }
}
