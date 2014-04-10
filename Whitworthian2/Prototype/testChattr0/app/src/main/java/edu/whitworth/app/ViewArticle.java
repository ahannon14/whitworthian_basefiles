package edu.whitworth.app;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

public class ViewArticle extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_article);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_article, menu);
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

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_view_article, container, false);
            Intent i = this.getActivity().getIntent();
            int[] articleNum = i.getIntArrayExtra("article number, section");
            TextView articleTitle = (TextView)getActivity().findViewById(R.id.article_title);

            Resources res = getResources();
            String[] top_news = res.getStringArray(R.array.top_news_titles);
            //String[] news = res.getStringArray(R.array.news_titles);
            //String[] arts_culture = res.getStringArray(R.array.arts_and_culture_titles);
           // String[] opinions = res.getStringArray(R.array.opinions_titles);
            //String[] sports = res.getStringArray(R.array.sports_titles);

            // It breaks every time I use setText!

            /*switch(articleNum[1]){ // switch by section
                case 0: articleTitle.setText(top_news[articleNum[0]]); // top news
                    break;
                default: articleTitle.setText("I don't know!");
            }*/
            //articleTitle.setText("stuff");

            return rootView;
        }
    }

}
