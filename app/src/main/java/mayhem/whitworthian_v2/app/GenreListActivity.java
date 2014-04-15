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

    final int numGenres = 5;
    private String[] genres = new String[numGenres];
    private ListView genre_List;
    private PlaceholderFragment myGenrefragment = new PlaceholderFragment();




    protected void fill_Genre_String() {
        genres = getResources().getStringArray(R.array.news_Genres);
    }

    protected ListView get_Genre_List(View V) {
        if (genre_List == null) {
            genre_List = (ListView) V.findViewById(R.id.genre_List_View);
        }
        return genre_List;
    }

    protected void set_Genre_List_Adapter(View V) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, genres);
        get_Genre_List(V).setAdapter(adapter);
    }

    public void genre_Item_Click(View view) {
        Intent article_List = new Intent(this, ArticleListActivity.class);
        startActivity(article_List);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myGenrefragment).commit();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.genre_list, menu);

        View V = myGenrefragment.rootView;

        fill_Genre_String();
        get_Genre_List(V);
        set_Genre_List_Adapter(V);

        genre_List.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                genre_Item_Click(view);
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
            rootView = inflater.inflate(R.layout.fragment_genre_list,
                    container, false);
            return rootView;
        }
    }


}
