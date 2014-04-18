package mayhem.whitworthian_v2.app;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GenreListActivity extends ActionBarActivity {
    /* Create class variables:
        numGenres       - the total number of news genres, including top news
        genres          - string array which contains the genre values
        genre_List      - the list view object which displays the genres
        myGenreFragment - contains the view fragment which the app display occurs on.
     */
    final int numGenres = 5;
    private String[] genres = new String[numGenres];
    private ListView genre_List;
    private PlaceholderFragment myGenrefragment = new PlaceholderFragment();

    @Override  //Create the activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        //The genre list view is always titled "The Whitworthian"
        setTitle("The Whitworthian");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myGenrefragment).commit();
        }
    }

    @Override //set up the options menu and store data in the appropriate fragment
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.genre_list, menu);

        //Fills the genre view fragment
        View V = myGenrefragment.rootView;
        fill_Genre_String();
        get_Genre_List(V);
        set_Genre_List_Adapter(V);

        //Sets up the genre list to wait for user input & respond to it.
        //id & position refer to the number on the list selected
        genre_List.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_Genre = genres[position];
                genre_Item_Click(view, selected_Genre);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Fills up the genres array from data in strings.xml
    protected void fill_Genre_String() {
        genres = getResources().getStringArray(R.array.news_Genres);
    }

    //Gets the ID of the listview in which to display genres
    protected ListView get_Genre_List(View V) {
        if (genre_List == null) {
            genre_List = (ListView) V.findViewById(R.id.genre_List_View);
        }
        return genre_List;
    }

    //Sets the list adapter for the genre list.  Displays all text in genre string
    protected void set_Genre_List_Adapter(View V) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genres);
        get_Genre_List(V).setAdapter(adapter);
    }

    //Controls the behavior of the application when the genre is clicked.  Takes the name of the
    // genre, and opens a new article view of that genre
    public void genre_Item_Click(View view, String new_Genre) {
        Intent article_List = new Intent(this, ArticleListActivity.class);
        article_List.putExtra("this_Genre", new_Genre);
        startActivity(article_List);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        //made public for access in outside class.
        public View rootView;

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_genre_list,
                    container, false);
            return rootView;
        }
    }


}
