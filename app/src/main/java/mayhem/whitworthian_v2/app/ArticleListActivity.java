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

public class ArticleListActivity extends ActionBarActivity {
    /* Variables for ArticleListActivity
        numArticles     -the number of articles to display
        articles        -A string containing the titles of articles to display
        article_List    -A ListView object which corresponds to the ListView in the activity
        myfragment      -The ArticleList fragment where the action happens
        my_Genre        -The genre of the articles displayed
        my_Image        -The image corresponding to that genre.
     */
    private int numArticles;
    private String[] articles;
    private int[] images;
    private int[] ids;
    private ListView article_List;
    private PlaceholderFragment myfragment = new PlaceholderFragment();
    private String my_Genre;
    private int my_Image;

    private ArrayList<article> app_Articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mayhem.whitworthian_v2.app.R.layout.activity_article_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mayhem.whitworthian_v2.app.R.id.container, myfragment).commit();
        }

        //Sets up the action bar and the genre of the article list
        setup_ActionBar_Appearance();
        get_Article_Data();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(mayhem.whitworthian_v2.app.R.menu.article_list, menu);

        //Fills the article list with the appropriate articles
        View V = myfragment.rootView;
        fill_Article_String();
        get_Article_List(V);
        set_Article_List_Adapter(V);
        //Waits for an article to be clicked on, then loads the appropriate view
        article_List.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                load_Article_View(view, position);
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // Go back to the genre list activity on back button click.
                Intent myIntent = new Intent(this, GenreListActivity.class);

                myIntent.putParcelableArrayListExtra("my_Articles", app_Articles);
                try {
                    startActivity(myIntent);
                } catch  ( ActivityNotFoundException e) {
                    e.printStackTrace();
                }
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

    //Fill in string array of article titles
    protected void fill_Article_String() {
        if (!(my_Genre.equals("Top News"))){
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).get_Genre().equals(my_Genre))
                {
                    numArticles++;
                }
            }
        }
        else {
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).is_Top())
                {
                    numArticles++;
                }
            }
        }
        articles = new String[numArticles];
        images = new int[numArticles];
        ids = new int[numArticles];

        int counter = 0;
        if (!(my_Genre.equals("Top News"))){
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).get_Genre().equals(my_Genre))
                {
                    articles[counter] = app_Articles.get(i).get_Title();
                    images[counter] = app_Articles.get(i).get_image_ID();
                    ids[counter] = app_Articles.get(i).get_Article_ID();
                    counter++;
                }
            }
        }
        else {
            for (int i = 0; i < app_Articles.size(); i++) {
                if (app_Articles.get(i).is_Top())
                {
                    articles[counter] = app_Articles.get(i).get_Title();
                    images[counter] = app_Articles.get(i).get_image_ID();
                    ids[counter] = app_Articles.get(i).get_Article_ID();
                    counter++;
                }
            }
        }
    }

    //finds the article list element for the program to fill it with article titles
    protected ListView get_Article_List(View V) {
        if (article_List == null) {
            article_List = (ListView) V.findViewById(mayhem.whitworthian_v2.app.R.id.article_List_View);
        }
        return article_List;
    }

    //Create the article list adapter so that it can contain more complex elements than just the
    // title.
    protected void set_Article_List_Adapter(View V) {
        //Makes an array of article selections (which can contain title, image id, and article id.
        article_Selection article_Data[] = new article_Selection[numArticles];
        for (int i = 0; i < numArticles; i++)
        {
            //Loads article data
            article_Data[i] = new article_Selection(images[i], articles[i]);
        }
        //Puts data into the article list
        article_Selection_Adapter adapter = new article_Selection_Adapter(this, article_Data);
        get_Article_List(V).setAdapter(adapter);
    }

    //loads the appropriate article upon selection.
    public void load_Article_View(View view, int position) {
        Intent article_View = new Intent(this, ArticleViewActivity.class);
        article_View.putExtra("my_Genre", my_Genre);
        article_View.putExtra("my_ID", ids[position]);
        article_View.putParcelableArrayListExtra("my_Articles", app_Articles);
        startActivityForResult(article_View, 1);
    }

    //Sets up the action bar and the genre of the article list
    private void setup_ActionBar_Appearance(){
        Bundle goodies = getIntent().getExtras();

        try{
            ArrayList<article> local_Articles = goodies.getParcelableArrayList("my_Articles");
            my_Genre = goodies.getString("this_Genre");
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
            my_Image = R.drawable.news_box;
            getActionBar().setIcon(my_Image);
        }
        else if (my_Genre.equals("Sports")){
            my_Image = R.drawable.sports_box;
            getActionBar().setIcon(my_Image);
        }
        else if (my_Genre.equals("Arts & Culture")){
            my_Image = R.drawable.ac_box;
            getActionBar().setIcon(my_Image);
        }
        else if (my_Genre.equals("Opinion")){
            my_Image = R.drawable.opinions_box;
            getActionBar().setIcon(my_Image);
        }
        else{
            my_Image = R.drawable.whitworthian_w;
            getActionBar().setIcon(my_Image);

        }


        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public View rootView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(mayhem.whitworthian_v2.app.R.layout.fragment_article_list,
                    container, false);
            return rootView;
        }
    }

}
