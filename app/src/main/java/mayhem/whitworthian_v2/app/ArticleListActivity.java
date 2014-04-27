package mayhem.whitworthian_v2.app;

import mayhem.whitworthian_v2.app.R;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Vector;

/** This is the ArticleListActivity.
 *  Includes the following functionality:
 *  -Receives articles under-the-hood from splash page and/or genre list
 *  -Displays articles to the user
 *  -Responds to user input by either opening an article, returning to genre page, or searching.
 *
 *  Contains the following class variables:
 *      num_Articles    -the number of articles to display
 *      articles        -A string array containing the titles of articles to display
 *      descs           -A string array containing the descriptions of the articles to display
 *      article_List    -A ListView object which corresponds to the ListView in the activity
 *      my_Genre        -The genre of the articles displayed
 *      my_Image        -The image corresponding to that genre.
 *      my_Instance     -A boolean determining whether or not this is the root top news list.
 *      app_Articles    -ArrayList containing all article data
 */
public class ArticleListActivity extends ActionBarActivity {
    private int num_Articles;
    private String[] articles;
    private String[] descs;
    private int[] images;
    private int[] ids;
    private ListView article_List;
    private String my_Genre;
    private int my_Image;
    private boolean my_Instance;
    private ArrayList<article> app_Articles;


    /*When this Intent begins, OnCreate is called */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mayhem.whitworthian_v2.app.R.layout.activity_article_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mayhem.whitworthian_v2.app.R.id.container, new PlaceholderFragment()).commit();
        }

        //Sets up the action bar and the genre of the article list
        Bundle goodies = getIntent().getExtras();
        setup_ActionBar_Appearance(goodies);
        get_Article_Data(goodies);

    }

    /* After OnCreate, OnCreateOptionsMenu is called under-the-hood Here the search view
    * is initialized. */
    //TODO: Add search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(mayhem.whitworthian_v2.app.R.menu.article_list, menu);
        return true;
    }

    /* Handles user input of top action bar */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // back button pressed
                // Go back to the genre list activity on back button click.
                if(my_Instance){
                    // If this is the root Top News view, then create the genre list
                    Intent myIntent = new Intent(this, GenreListActivity.class);
                    myIntent.putParcelableArrayListExtra("my_Articles", app_Articles);
                    try {
                        startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // If this is any other list view, then return back to the genre list with
                    // an OK message.
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra("my_Articles", app_Articles);
                    setResult(RESULT_OK, data);
                    finish();
                }
                return true;
            case mayhem.whitworthian_v2.app.R.id.action_settings: // Settings button pressed
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Retrieves article data from Intent Extras.  Also determines retrieves whether or not
     * this is the root Top News instance */
    protected void get_Article_Data(Bundle goodies) {
        try{
            this.app_Articles = goodies.getParcelableArrayList("my_Articles");
        }
        catch(NullPointerException bad){
            this.app_Articles = new ArrayList<article>();
        }
        try{
            this.my_Instance = goodies.getBoolean("first_Instance");
        }
        catch(NullPointerException bad){
            this.my_Instance = false;
        }
    }

    /* Fills in class genre variable and sets up the action bar's appearance*/
    private void setup_ActionBar_Appearance(Bundle goodies){

        try{
            my_Genre = goodies.getString("this_Genre");

            //Set up action bar Title
            if (my_Genre.equals(getResources().getString(R.string.top)))
                setTitle(R.string.app_name);
            else
                setTitle(my_Genre);

            //Set up action bar image
            if (my_Genre.equals(getResources().getString(R.string.news))) {
                my_Image = R.drawable.news_box;
                getActionBar().setIcon(my_Image);
            } else if (my_Genre.equals(getResources().getString(R.string.sports))) {
                my_Image = R.drawable.sports_box;
                getActionBar().setIcon(my_Image);
            } else if (my_Genre.equals(getResources().getString(R.string.arts_culture))) {
                my_Image = R.drawable.ac_box;
                getActionBar().setIcon(my_Image);
            } else if (my_Genre.equals(getResources().getString(R.string.opinions))) {
                my_Image = R.drawable.opinions_box;
                getActionBar().setIcon(my_Image);
            } else {
                my_Image = R.drawable.ic_launcher;
                getActionBar().setIcon(my_Image);

            }
        } catch (NullPointerException bad) {
            my_Genre = getResources().getString(R.string.top);
        }

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /* Fills in local arrays: "articles" "images" and "ids"
     * Note that top news articles are tracked with the boolean is_Top() method, whereas
     * all other genres are tracked by their genre strings.
     */
    protected void fill_Article_Local_Data() {
        //Figure out how many articles there are
        if (!(my_Genre.equals(getResources().getString(R.string.top)))) { //Top News
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).get_Genre().equals(my_Genre))
                { num_Articles++; }
            }
        }
        else { //Other Genres
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).is_Top())
                { num_Articles++; }
            }
        }

        //Initialize arrays to proper article number
        articles = new String[num_Articles];
        descs = new String[num_Articles];
        images = new int[num_Articles];
        ids = new int[num_Articles];

        //Go through all articles and pick out the ones that we want to look at.
        int counter = 0;
        if (!(my_Genre.equals(getResources().getString(R.string.top)))){  //Top News
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).get_Genre().equals(my_Genre))
                {   articles[counter] = app_Articles.get(i).get_Title();
                    descs[counter] = app_Articles.get(i).get_Desc();
                    images[counter] = app_Articles.get(i).get_image_ID();
                    ids[counter] = app_Articles.get(i).get_Article_ID();
                    counter++; }
            }
        }
        else {
            for (int i = 0; i < app_Articles.size(); i++) { //Other genres
                if (app_Articles.get(i).is_Top())
                {   articles[counter] = app_Articles.get(i).get_Title();
                    descs[counter] = app_Articles.get(i).get_Desc();
                    images[counter] = app_Articles.get(i).get_image_ID();
                    ids[counter] = app_Articles.get(i).get_Article_ID();
                    counter++; }
            }
        }
    }

    /* Returns this activity's article list ID. */
    protected ListView get_Article_List(View V) {
        if (article_List == null) {
            article_List = (ListView) V.findViewById(mayhem.whitworthian_v2.app.R.id.article_List_View);
        }
        return article_List;
    }

    /* Sets this activity's article list adapter to display Title, image, and description. */
    protected void set_Article_List_Adapter(View V) {
        //Get the articles into adapter form
        article_Selection article_Data[] = new article_Selection[num_Articles];
        for (int i = 0; i < num_Articles; i++)
        { article_Data[i] = new article_Selection(images[i], articles[i], descs[i], ids[i]); }

        //Then adapt the list to the proper format with the proper data
        article_Selection_Adapter adapter = new article_Selection_Adapter(this, article_Data);
        get_Article_List(V).setAdapter(adapter);
    }

    /* On Click, loads the appropriate article */
    public void load_Article_View(View view, int position) {
        Intent article_View = new Intent(this, ArticleViewActivity.class);
        article_View.putExtra("my_Genre", my_Genre);
        article_View.putExtra("my_ID", ids[position]);
        article_View.putParcelableArrayListExtra("my_Articles", app_Articles);
        article_View.putExtra("first_Instance", this.my_Instance);
        startActivityForResult(article_View, 1);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        /* Initializes Fragment and fills the fragment's layout elements with the proper data */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(mayhem.whitworthian_v2.app.R.layout.fragment_article_list,
                    container, false);

            //Fills the article list with the appropriate articles
            fill_Article_Local_Data();
            get_Article_List(rootView);
            set_Article_List_Adapter(rootView);

            //Sets up an event handler which waits for an article to be clicked on,
            // then loads the appropriate view
            article_List.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    load_Article_View(view, position);
                }
            });
            return rootView;
        }
    }

}
