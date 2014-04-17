package mayhem.whitworthian_v2.app;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class ArticleViewActivity extends ActionBarActivity {
    private String my_Genre;
    private int my_Image;
    private PlaceholderFragment my_Fragment = new PlaceholderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        Bundle goodies = getIntent().getExtras();

        try{
            my_Genre = goodies.getString("my_Genre");
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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, my_Fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.article_view, menu);
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
            View rootView = inflater.inflate(R.layout.fragment_article_view,
                    container, false);
            return rootView;
        }
    }

}
