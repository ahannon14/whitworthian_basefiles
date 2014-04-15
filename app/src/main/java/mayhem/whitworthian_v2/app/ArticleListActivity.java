package mayhem.whitworthian_v2.app;

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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticleListActivity extends ActionBarActivity {
    final int numGenres = 10;
    private String[] articles = new String[numGenres];
    private ListView article_List;
    private PlaceholderFragment myfragment = new PlaceholderFragment();
    private ActionBar actionBar;




    protected void fill_Article_String() {
        articles = getResources().getStringArray(R.array.article_Titles);
    }

    protected ListView get_Article_List(View V) {
        if (article_List == null) {
            int a = R.id.article_List_View;
            article_List = (ListView) V.findViewById(a);
        }
        return article_List;
    }

    protected void set_Article_List_Adapter(View V) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articles);
        get_Article_List(V).setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, myfragment).commit();
        }

        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

//        SpannableString s = new SpannableString("Articles");
//        s.setSpan(new TypefaceSpan(this, "courier"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        // Update the action bar title with the TypefaceSpan instance
//        actionBar.setTitle(s);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_list, menu);

        View V = myfragment.rootView;

        fill_Article_String();
        get_Article_List(V);
        set_Article_List_Adapter(V);



        article_List.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                load_Article_View(view);
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
                // app icon in action bar clicked; go home
                Intent myIntent = new Intent(this, GenreListActivity.class);
                try {
                    startActivity(myIntent);
                } catch  ( ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void load_Article_View(View view) {
        Intent article_View = new Intent(this, ArticleViewActivity.class);
        startActivity(article_View);
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
            rootView = inflater.inflate(R.layout.fragment_article_list,
                    container, false);
            return rootView;
        }
    }

}
