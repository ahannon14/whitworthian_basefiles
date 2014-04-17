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

public class ArticleListActivity extends ActionBarActivity {
    final int numArticles = 10;
    private String[] articles = new String[numArticles];
    private ListView article_List;
    private PlaceholderFragment myfragment = new PlaceholderFragment();
    private ActionBar actionBar;




    protected void fill_Article_String() {

        articles = getResources().getStringArray(mayhem.whitworthian_v2.app.R.array.article_Titles);
    }

    protected ListView get_Article_List(View V) {
        if (article_List == null) {
            article_List = (ListView) V.findViewById(mayhem.whitworthian_v2.app.R.id.article_List_View);
        }
        return article_List;
    }

    protected void set_Article_List_Adapter(View V) {
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, articles);
        article_Selection article_Data[] = new article_Selection[numArticles];
        for (int i = 0; i < numArticles; i++)
        {
            article_Data[i] = new article_Selection(mayhem.whitworthian_v2.app.R.drawable.ac_box, articles[i]);
        }
        get_Article_List(V);

        article_Selection_Adapter adapter = new article_Selection_Adapter(this, article_Data);
        get_Article_List(V).setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mayhem.whitworthian_v2.app.R.layout.activity_article_list);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(mayhem.whitworthian_v2.app.R.id.container, myfragment).commit();
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
        getMenuInflater().inflate(mayhem.whitworthian_v2.app.R.menu.article_list, menu);

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
            case mayhem.whitworthian_v2.app.R.id.action_settings:
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
            rootView = inflater.inflate(mayhem.whitworthian_v2.app.R.layout.fragment_article_list,
                    container, false);
            return rootView;
        }
    }

}
