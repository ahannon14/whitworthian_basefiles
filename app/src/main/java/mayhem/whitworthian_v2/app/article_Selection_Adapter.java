package mayhem.whitworthian_v2.app;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * This class adapts an article_Selection to the ListView in fragment_article_list by using
 * the article_list_item_row layout.
 *
 * contains these elements:
 *  context - the current app context
 *  layoutResourceID - the ID of article_list_item_row
 *
 */

public class article_Selection_Adapter extends ArrayAdapter<article_Selection> {
    private Context context;
    private int layout_Resource_ID;
    private article_Selection data[] = null;


    /* Constructor */
    public article_Selection_Adapter(Context context, article_Selection[] data) {
        super(context, R.layout.article_list_item_row, data);
        this.layout_Resource_ID = R.layout.article_list_item_row;
        this.context = context;
        this.data = data;
    }

    /* Fills article data into the appropriate ListView */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Inflates the list
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(this.layout_Resource_ID, parent, false);


        //Fills the list with data
        article_Selection_Holder holder = new article_Selection_Holder();
        try{
            holder.img_Icon = (ImageView) rowView.findViewById(R.id.article_Img_Icon);
            holder.txt_Title = (TextView) rowView.findViewById(R.id.article_Title);
            holder.txt_Desc = (TextView) rowView.findViewById(R.id.article_Desc);
            holder.txt_Title.setText(data[position].get_Title());
            holder.img_Icon.setImageResource(data[position].get_Icon());
            holder.txt_Desc.setText(trim_Desc(data[position].get_Desc()));
        }
        catch(NullPointerException bad) {
            bad.printStackTrace();
        }

        return rowView;
    }

    /* Clean ellipse and dash tags in description */
    private String trim_Desc(String desc) {
        desc = desc.replace("&#8211;", "-");
        return desc.replace(" [&#038;hellip", "...");
    }

    /* A data structure which holds the layout resources being filled. */
    static class article_Selection_Holder {
        private ImageView img_Icon = null;
        private TextView txt_Title = null;
        private TextView txt_Desc = null;
    }
}