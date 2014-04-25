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

public class ArticleViewActivity extends ActionBarActivity {
    /*The data of the article View, contains the following:
        my_Genre        - The genre of the article, for the purposes of action bar display
        my_Genre_Image  - The Image of the genre of the article, for action bar display
        my_Image        - The image of the article, as retrieved from thewhitworthian.com
        my_Title        - The title of the article, as retrieved from thewhitworthian.com
        my_Body         - The Body of the article, as retrieved from thewhitworthian.com
     */
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

    @Override  //Create the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, my_Fragment).commit();
        }

        //Puts article genre and genre image in action bar
        setup_ActionBar_Appearance();
        Bundle goodies = getIntent().getExtras(); //Get extras from this intent
        try{
            this.my_ID = goodies.getInt("my_ID");
        }
        catch(NullPointerException bad){
            this.my_ID = -1;
        }
        try{
            this.list_Instance = goodies.getBoolean("first_Instance");
        }
        catch(NullPointerException bad){
            this.list_Instance = false;
        }
        get_Article_Data();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_view, menu);

        View V = my_Fragment.getView();

        //Set the Title
        final TextView title_Text = (TextView) V.findViewById(R.id.article_title);
        title_Text.setText(my_Title);

        //Set the Body
        final TextView body_Text = (TextView) V.findViewById(R.id.article_content);
        body_Text.setText(my_Body);

        //Set the Image -- NOTE: THIS ISN'T WORKING

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


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    protected void get_Article_Data() {
        Bundle goodies = getIntent().getExtras();

        try{
            this.app_Articles = goodies.getParcelableArrayList("my_Articles");
        }
        catch(NullPointerException bad){
            this.app_Articles = new ArrayList<article>();
        }

        for (int i = 0; i < this.app_Articles.size(); i++) {
            if (this.app_Articles.get(i).get_Article_ID() == my_ID) {
                String article_Body = this.app_Articles.get(i).get_Body();
                my_Body = Html.fromHtml(article_Body);
                my_Title = this.app_Articles.get(i).get_Title();
                my_Image_URL = this.app_Articles.get(i).get_image_URL();
                my_Image_ID = this.app_Articles.get(i).get_image_ID();
            }
        }

    }

    //Puts article genre and genre image in action bar
    private void setup_ActionBar_Appearance(){
        Bundle goodies = getIntent().getExtras(); //Get extras from this intent

        //Try to get the genre, if all else fails, set it as top news
        try{
            my_Genre = goodies.getString("my_Genre");
        }
        catch(NullPointerException bad){
            my_Genre = "Top News";
        }

        //Set up action bar Title
        if (my_Genre.equals("Top News"))
            setTitle("The Whitworthian");
        else
            setTitle(my_Genre);

        //Set up action bar image
        if (my_Genre.equals("News")){
            my_Genre_Image = R.drawable.news_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals("Sports")){
            my_Genre_Image = R.drawable.sports_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals("Arts & Culture")){
            my_Genre_Image = R.drawable.ac_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else if (my_Genre.equals("Opinions")){
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
