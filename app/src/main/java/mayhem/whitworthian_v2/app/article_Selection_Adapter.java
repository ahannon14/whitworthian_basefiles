package mayhem.whitworthian_v2.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by Evan Anders on 4/16/14.
 */



public class article_Selection_Adapter extends ArrayAdapter<article_Selection>{
        Context context;
        int layoutResourceId;
        article_Selection data[] = null;


        public article_Selection_Adapter(Context context, article_Selection[] data) {
            super(context, R.layout.article_list_item_row, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.article_list_item_row, parent, false);

            article_Selection_Holder holder = new article_Selection_Holder();
            holder.imgIcon = (ImageView)rowView.findViewById(R.id.articleImgIcon);
            holder.txtTitle = (TextView)rowView.findViewById(R.id.articleTitle);

            holder.txtTitle.setText(data[position].title);
            holder.imgIcon.setImageResource(data[position].icon);

            return rowView;
        }

static class article_Selection_Holder
{
    ImageView imgIcon;
    TextView txtTitle;
}
}