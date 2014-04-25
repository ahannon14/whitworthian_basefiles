package mayhem.whitworthian_v2.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/** This is the ArticleViewActivity.
 *  Includes the following functionality:
 *  -Receives a single article under-the-hood
 *  -Displays article's image, title, and body to the user in a scrollable format
 *  -Responds to user input by returning to article list page or by searching.
 *
 *  Contains the following class variables:
 *  my_Genre:           The genre of the current article
 *  my_Genre_Image:     The image associated with this article, for action bar customization
 *  my_ID:              The ID of the current article
 *  my_Image_ID:        The ID of the article's genre's banner
 *  my_Image_URL:       A string containing the article's image's URL, if available.
 *  my_Title:           A string containing the title of the article
 *  my_Body:            A spanned string containing the body of the article, likely in HTML
 *  my_Fragment:        The Activity fragment in which the article view is actually shown.
 *  app_articles:       All of the articles gathered on the splash screen, so they don't disappear.
 *  list_Instance:      A boolean of whether or not the parent article list is the root Top News
 */
public class ArticleViewActivity extends ActionBarActivity {
    private String my_Genre;
    private int my_Genre_Image;
    private int my_ID;
    private int my_Image_ID;
    private String my_Image_URL;
    private String my_Title;
    private Spanned my_Body;
    private PlaceholderFragment my_Fragment = new PlaceholderFragment();
    private ArrayList<article> app_Articles;
    private boolean list_Instance;

    /* Create activity and fragment, Sets up local data */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, my_Fragment).commit();
        }

        //Set up local variables
        Bundle goodies = getIntent().getExtras();
        setup_ActionBar_Appearance(goodies);
        get_Article_Data(goodies);

    }


    /*After OnCreate, OnCreateOptionsMenu is called under-the-hood Here the search view
    * is initialized.
    * NOTE: article view fragment loads all of its data here, there must be a better place for
    * it...*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_view, menu);

        //TODO: Move this somewhere else.

        View V = my_Fragment.getView();

        //Set the Title
        final TextView title_Text = (TextView) V.findViewById(R.id.article_title);
        title_Text.setText(my_Title);

        //Set the Body
        final TextView body_Text = (TextView) V.findViewById(R.id.article_content);
        body_Text.setText(my_Body);

        //Set the Image
        set_Banner_Image(V);
        return true;
    }

    /*Handles user input of top action bar */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // when the back button is clicked, return to the article list we were just on
                Intent data = new Intent();
                data.putExtra("this_Genre", my_Genre);
                data.putParcelableArrayListExtra("my_Articles", app_Articles);
                data.putExtra("first_Instance", list_Instance);
                setResult(RESULT_OK, data);
                finish();
                return true;
            case mayhem.whitworthian_v2.app.R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*Sets the article Image using either my_Image_URL (if available) or my_Image_ID */
    private void set_Banner_Image(View V) {
        if (my_Image_URL != null) {
            ///Set the image using the url
        }
        else {
            final ImageView image_Box = (ImageView) V.findViewById(R.id.article_image);
            if (my_Image_URL == null)
            {
                if (my_Image_ID == R.drawable.news_box){
                    image_Box.setImageResource(R.drawable.news_bar);
                }
                else if (my_Image_ID == R.drawable.opinions_box) {
                    image_Box.setImageResource(R.drawable.opinions_bar);
                }
                else if (my_Image_ID == R.drawable.ac_box) {
                    image_Box.setImageResource(R.drawable.ac_bar);
                }
                else if (my_Image_ID == R.drawable.sports_box) {
                    image_Box.setImageResource(R.drawable.sports_bar);
                }
            }
        }
    }

    /* Picks out the proper article from app_Articles and fills in all appropriate data locally */
    protected void get_Article_Data(Bundle goodies) {

        //Pull out important information
        try{
            this.my_ID = goodies.getInt("my_ID");
            this.list_Instance = goodies.getBoolean("first_Instance");
            this.app_Articles = goodies.getParcelableArrayList("my_Articles");
        }
        catch(NullPointerException bad){
            bad.printStackTrace();
        }

        //Fill local variables
        for (int i = 0; i < this.app_Articles.size(); i++) {
            if (this.app_Articles.get(i).get_Article_ID() == my_ID) {
                my_Body = Html.fromHtml(this.app_Articles.get(i).get_Body());
                my_Title = this.app_Articles.get(i).get_Title();
                my_Image_URL = this.app_Articles.get(i).get_image_URL();
                my_Image_ID = this.app_Articles.get(i).get_image_ID();
                break;
            }
        }
    }

    /* Puts article genre and genre's icon on the action bar. */
    private void setup_ActionBar_Appearance(Bundle goodies){
        //Try to get the genre, if all else fails, set it as top news
        try{
            my_Genre = goodies.getString("my_Genre");
        }
        catch(NullPointerException bad){
            my_Genre = "Top News";
        }

        //Set up action bar Title
        if (my_Genre.equals(getResources().getString(R.string.top)))
            setTitle(getResources().getString(R.string.app_name));
        else
            setTitle(my_Genre);

        //Set up action bar image
        if (my_Genre.equals(getResources().getString(R.string.news))){
            my_Genre_Image = R.drawable.news_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals(getResources().getString(R.string.sports))){
            my_Genre_Image = R.drawable.sports_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals(getResources().getString(R.string.arts_culture))){
            my_Genre_Image = R.drawable.ac_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals(getResources().getString(R.string.opinions))){
            my_Genre_Image = R.drawable.opinions_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else{
            my_Genre_Image = R.drawable.ic_launcher;
            getActionBar().setIcon(my_Genre_Image);

        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        //made public element for outside access
        public View rootView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_article_view,
                    container, false);
            return rootView;
        }
    }

}
