package edu.whitworth.app;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class ArticleList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent sectionInt = this.getIntent();
        final int sectionNum = sectionInt.getIntExtra("section number",0);

        // Set the article titles
        switch(sectionNum){
            case 0:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.top_news_titles, R.layout.title_list));
                break;
            case 1:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.news_titles, R.layout.title_list));
                break;
            case 2:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.arts_and_culture_titles, R.layout.title_list));
                break;
            case 3:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.opinions_titles, R.layout.title_list));
                break;
            case 4:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.sports_titles, R.layout.title_list));
                break;
            default:
                setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.article_titles, R.layout.title_list));
                break;
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent showContent = new Intent(getApplicationContext(),
                        ViewArticle.class); // this is where we go to the ArticleView activity
                showContent.putExtra("article number, section", new int[]{i, sectionNum});
                startActivity(showContent);
            }
        });

    }

    // The functions below I am not using yet!

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.articles, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_article_list, container, false);
            return rootView;
        }
    }

}
