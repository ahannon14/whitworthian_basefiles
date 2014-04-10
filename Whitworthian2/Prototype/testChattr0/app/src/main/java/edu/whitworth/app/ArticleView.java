package edu.whitworth.app;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ArticleView extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        Intent articleContent = this.getIntent();
        int articleNum[] = articleContent.getIntArrayExtra("article number");

        // Set the article title
        String[] section;
        TextView title = (TextView)findViewById(R.id.article_title);
        switch (articleNum[1]){
            case 0:
                section = getResources().getStringArray(R.array.top_news_titles);
            case 1:
                section = getResources().getStringArray(R.array.news_titles);
            case 2:
                section = getResources().getStringArray(R.array.arts_and_culture_titles);
            case 3:
                section = getResources().getStringArray(R.array.opinions_titles);
            default:
                section = getResources().getStringArray(R.array.sports_titles);
        }
        title.setText(section[articleNum[0]]);

        // Set the article content
        TextView content = (TextView)findViewById(R.id.article_content);

        // Set the article image (if any)
        ImageView image = (ImageView)findViewById(R.id.article_image);

        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/

        /*
        setListAdapter(ArrayAdapter.createFromResource(getApplicationContext(), R.array.tut_links, R.layout.article_list));

        final String[] links = getResources().getStringArray(R.array.tut_links);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //String content = links[i];
                //Intent showContent = new Intent(getApplicationContext(),
                        //ArticleView.class); // this is where we go to the CHAT activity
                //showContent.setData(Uri.parse(content));
                //startActivity(showContent);
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_article_view, container, false);
            return rootView;
        }
    }

}
