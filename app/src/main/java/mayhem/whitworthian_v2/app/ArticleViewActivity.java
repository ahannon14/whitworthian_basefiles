package mayhem.whitworthian_v2.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
    private int my_Image;
    private String my_Title;
    private String my_Body;
    private PlaceholderFragment my_Fragment = new PlaceholderFragment();
    private ArrayList<article> app_Articles;

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
        get_Article_Data();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_view, menu);
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
        else if (my_Genre.equals("Opinion")){
            my_Genre_Image = R.drawable.opinions_box;
            getActionBar().setIcon(my_Genre_Image);
        }
        else{
            my_Image = R.drawable.whitworthian_w;
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
