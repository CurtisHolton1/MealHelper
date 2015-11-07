package group8.mealhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import group8.mealhelper.models.CookBook;
import group8.mealhelper.models.Meal;

/**
 * Created by curtis on 10/30/15.
 */
    public class CookBookListAdapter extends ArrayAdapter<Meal> {
    private final Context mContext;
    private final List<Meal> mMeals;


    public CookBookListAdapter(Context context, List<Meal> meals) {
        super(context, -1, meals);
        this.mContext = context;
        this.mMeals = meals;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.cookbook_list_row, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.cookBook_list_row_image);
        if(imageView.getDrawable() == null) {
            String[] path = {mMeals.get(position).getPathToPic()};
            new LocalPicTask(imageView).execute(path);
        }
        TextView textView = (TextView) rowView.findViewById(R.id.cookBook_list_row_textView);
        textView.setText(mMeals.get(position).getName());
        Button button = (Button) rowView.findViewById(R.id.cookBook_list_row_deleteButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] params = {mMeals.get(position).getId()};
                new deleteMealTask().execute(params);
                mMeals.remove(position);
                notifyDataSetChanged();

            }
        });
        return rowView;
    }

    private class LocalPicTask extends AsyncTask<String,Void,Bitmap> {
        ImageView mImageView;
        public LocalPicTask(ImageView imageView){
            this.mImageView = imageView;
        }
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Bitmap doInBackground(String... params){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 30;
            Bitmap bitmap = BitmapFactory.decodeFile(params[0], options);
            return  bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            super.onPostExecute(result);
            mImageView.setImageBitmap(result);
        }
    }

    private class deleteMealTask extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Void doInBackground(String... params){
            CookBook.get(getContext()).deleteMeal(params[0]);
            return null;
        }


    }
}


