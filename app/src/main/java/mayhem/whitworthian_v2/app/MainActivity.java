package mayhem.whitworthian_v2.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/** This is the MainActivity.
 *  Includes the following functionality:
 *  -Retrieves article data from RSS feeds
 *  -Opens a Top News Article List
 *
 *  Contains the following class variables:
 *      app_Articles:       ArrayList containing all article data
 *      NUM_GENRES:         The total number of genres -- HARDCODED
 *      urls:               An array of URLs from which to obtain data through RSS
 *      alert:              A dialog that tells the user something went bad
 */
public class MainActivity extends ActionBarActivity {
    private ArrayList<article> app_Articles;
    private final int NUM_GENRES = 5;
    private final URL urls[] = new URL[NUM_GENRES];
    private AlertDialog.Builder alert;
    private ProgressBar my_Progress_Bar;


    /* Creates the layout, fills the urls array, and fetches all data from thewhitworthian.com */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


        fill_URLs(); // fill url array
        alert = make_Bad_Gather();

        app_Articles = null;
    }

    /* Inflates options menu without functionality */
    //TODO: Add a refresh if the program loses internet connection, or something of the sort
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    /*Fills the URL string with all appropriate feeds */
    private void fill_URLs() {
        try{
            urls[0] = new URL("http://www.thewhitworthian.com/feed/");
            urls[1] = new URL("http://www.thewhitworthian.com/category/news/feed/");
            urls[2] = new URL("http://www.thewhitworthian.com/category/sports/feed/");
            urls[3] = new URL("http://www.thewhitworthian.com/category/opinions/feed/");
            urls[4] = new URL("http://www.thewhitworthian.com/category/arts-and-culture/feed/");
        }
        catch (MalformedURLException bad1) {
            bad1.printStackTrace();
        }
    }

    /*Pops up an alert dialog saying that the connection failed Gives user the option to retry */
    private AlertDialog.Builder make_Bad_Gather() {
        return new AlertDialog.Builder(getApplicationContext())
                .setTitle("Connection failed")
                .setMessage("Fetching data from thewhitworthian.com failed.")
                .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new FetchArticlesTask().execute(urls); // fetch data
                        if(app_Articles == null) { show_Bad_Gather(); }
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Go back to home
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }

    private void show_Bad_Gather() {
            alert.show();
    }

    /*Opens up a background AsyncTask which fetches all of the data from the website */
    private class FetchArticlesTask extends AsyncTask<URL, String, ArrayList<article>> {
        /*doInBackground is where the action happens, connection is made here, and data is
         * collected.
         */
        //TODO: Fix crash on loss of internet connectivity.
        //TODO: Try to make the data collection and storing cleaner/more efficient
        @Override
        protected ArrayList<article> doInBackground(URL... urls) {
            ArrayList<article> arrays[] = new ArrayList[NUM_GENRES];

            if (is_Network_Connected()) {
                for (int i = 0; i < NUM_GENRES; i++) { // loop through all feeds
                    Rss_Handler handler = new Rss_Handler(i * 10000); //Create our custom handler
                    try {
                        //Setup for connection
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser saxParser = factory.newSAXParser();
                        XMLReader xmlReader = saxParser.getXMLReader();
                        xmlReader.setContentHandler(handler); //Tailor our parsing to our needs

                        //Set the input as the current feed's stream & parse
                        InputSource my_Input = new InputSource(urls[i].openStream());
                        xmlReader.parse(my_Input);

                        arrays[i] = handler.getArticleList(); //store the data.

                    } catch (IOException bad) {
                        bad.printStackTrace();
                        break;
                    } catch (SAXException bad) {
                        bad.printStackTrace();
                        break;
                    } catch (ParserConfigurationException bad) {
                        bad.printStackTrace();
                        break;
                    } catch(Exception e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            return null;
                        }

                    }
                }
            }
            else {
                return null;
            }
            return combineArrays(arrays); // Combines the array list
        }

        /*Check to see if connected to a network*/
        private boolean is_Network_Connected() {
            final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.getState() == NetworkInfo.State.CONNECTED;
        }



        /*Combine an array of ArrayLists of articles into one ArrayList of articles. */
        private ArrayList<article> combineArrays (ArrayList<article>[] arrays) {
            boolean accept = true; //Only accept articles that aren't in the list already
            ArrayList<article> all_articles = new ArrayList<article>();

            for(int i = 0; i < NUM_GENRES; i++) { //loop through genres
                for(int j = 0; j < arrays[i].size(); j++) { //loop through articles in this genre
                    for(int k = 0; k < all_articles.size(); k++) { //loop through stored articles
                        //Don't accept articles we already have
                        if (all_articles.get(k).get_Title().equals(arrays[i].get(j).get_Title()))
                        { accept = false;}
                    }
                    //Mark top news articles as top news
                    if (i == 0) { arrays[i].get(j).set_Article_Is_Top(true); }
                    else { arrays[i].get(j).set_Article_Is_Top(false); }

                    //Add articles we're accepting to the array
                    if (accept) { all_articles.add(arrays[i].get(j)); }
                    else { accept = true; }
                }
            }
            return all_articles;
        }

        //TODO: Add some text above load wheel so user knows stuff is happening
        @Override
        protected void onProgressUpdate(String... progress) {
            //Display some new text above scroll wheel
        }

        /* After articles are gathered, this opens up the Top News article list*/
        @Override
        protected void onPostExecute(ArrayList<article> result) {
            super.onPostExecute(result);

            if(result==null) {
                //TODO: Figure out how to make this not crash.
                runOnUiThread(new Runnable() {

                    public void run() {

                        Toast.makeText(getApplicationContext(), "Internet Connection Failed.", Toast.LENGTH_SHORT).show();
                        hide_Progress();


                    }
                });
                //show_Bad_Gather();
                return;
            }

            app_Articles = result;
            Intent article_List = new Intent(MainActivity.this, ArticleListActivity.class);
            article_List.putExtra("this_Genre", "Top News");
            article_List.putParcelableArrayListExtra("my_Articles", app_Articles);
            article_List.putExtra("first_Instance", true);
            startActivity(article_List);

            // close this activity
            finish();
        }
    }

    /*Handles item menu click */
    //TODO: Add refresh functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_refresh:
                my_Progress_Bar.setVisibility(View.VISIBLE);
                new FetchArticlesTask().execute(this.urls);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*Initialize the progress bar */
    public void init_Progress_Bar(View view) {
        my_Progress_Bar = (ProgressBar) view.findViewById(R.id.news_Load_Bar);
    }

    /*Hide progress bar */
    public void hide_Progress() {
        my_Progress_Bar.setVisibility(View.INVISIBLE);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            init_Progress_Bar(rootView); //Initialize progress bar
            new FetchArticlesTask().execute(urls); // fetch data

            return rootView;
        }
    }

}
