package mayhem.whitworthian_v2.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends ActionBarActivity {
    private ArrayList<article> app_Articles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(MainActivity.this, ArticleListActivity.class);
                mainIntent.putExtra("this_Genre", "Top News");
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, 500);
        */

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        new PrefetchData().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    /**
     * Async Task to make http call
     */
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {
           // Can add HTTP calls here later


            app_Articles = get_Articles();


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            Intent article_List = new Intent(MainActivity.this, ArticleListActivity.class);
            article_List.putExtra("this_Genre", "Top News");
            article_List.putParcelableArrayListExtra("my_Articles", app_Articles);
            startActivity(article_List);

            // close this activity
            finish();
        }

    }

    private ArrayList<article> get_Articles()
    {
        ArrayList<article> local_Articles = new ArrayList<article>();

        local_Articles.add(new article("News Item 1", "This is the body of this article", "News", true, false));
        local_Articles.add(new article("News Item 2", "This is the body of this article", "News", false, false));
        local_Articles.add(new article("News Item 3", "This is the body of this article", "News", true, false));
        local_Articles.add(new article("Sports Item 1", "This is the body of this article", "News", false, false));
        local_Articles.add(new article("Sports Item 2", "This is the body of this article", "News", true, false));
        local_Articles.add(new article("Sports Item 3", "This is the body of this article", "News", true, false));
        local_Articles.add(new article("Opinion Item 1", "This is the body of this article", "News", true, false));
        local_Articles.add(new article("Opinion Item 2", "This is the body of this article", "News", false, false));
        local_Articles.add(new article("A&C Item 1", "This is the body of this article", "News", false, false));
        local_Articles.add(new article("A&C Item 2", "This is the body of this article", "News", true, false));

        return local_Articles;

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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
