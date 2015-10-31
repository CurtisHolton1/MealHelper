package group8.mealhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import group8.mealhelper.models.Meal;

/**
 * Created by curtis on 10/30/15.
 */
    public class CookBookListAdapter extends ArrayAdapter<Meal> {
        private final Context mContext;
        private final List<Meal> mMeals;
        ImageView mImageView;


        public CookBookListAdapter(Context context, List<Meal> meals) {
            super(context, -1, meals);
            this.mContext = context;
            this.mMeals = meals;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.cookbook_list_row, parent, false);
            ImageView mImageView = (ImageView) rowView.findViewById(R.id.cookBook_list_row_image);
            setThumbNail(position);
            TextView textView = (TextView) rowView.findViewById(R.id.cookBook_list_row_textView);
            textView.setText(mMeals.get(position).getName());
            return  rowView;
        }

        private void setThumbNail(int position){
            // Get the dimensions of the View
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();
            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mMeals.get(position).getPathToPic(), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;
            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(mMeals.get(position).getPathToPic(), bmOptions);
            mImageView.setImageBitmap(bitmap);
        }
    }

