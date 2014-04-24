package mayhem.whitworthian_v2.app;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

public class MainActivity extends ActionBarActivity {
    private ArrayList<article> app_Articles;
    private int num_Genres = 5;
    private URL urls[] = new URL[num_Genres];
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }


        fill_URLs();

        new FetchArticlesTask().execute(this.urls);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

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

    private class FetchArticlesTask extends AsyncTask<URL, String, ArrayList<article>> {
        @Override
        protected ArrayList<article> doInBackground(URL... urls) {
            ArrayList<article> arrays[] = new ArrayList[num_Genres];


            for (int i = 0; i < num_Genres; i++) {
                Rss_Handler handler = new Rss_Handler(i * 10000);
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser saxParser = factory.newSAXParser();
                    XMLReader xmlReader = saxParser.getXMLReader();


                    xmlReader.setContentHandler(handler);

                    InputSource my_Input = new InputSource(urls[i].openStream());
                    xmlReader.parse(my_Input);

                    arrays[i] = handler.getArticleList();

                } catch (IOException bad) {
                    bad.printStackTrace();
                    break;
                } catch (SAXException bad) {
                    bad.printStackTrace();
                    break;
                } catch (ParserConfigurationException bad) {
                    bad.printStackTrace();
                    break;
                }

            }


            return combineArrays(arrays);
        }

        //Combine an array of arraylists of articles into one arraylist of articles.
        private ArrayList<article> combineArrays (ArrayList<article>[] arrays) {
            boolean accept = true;
            ArrayList<article> all_articles = new ArrayList<article>();
            for(int i = 0; i < num_Genres; i++) {
                for(int j = 0; j < arrays[i].size(); j++) {
                    for(int k = 0; k < all_articles.size(); k++) {
                        if (all_articles.get(k).get_Title().equals(arrays[i].get(j).get_Title())){
                            accept = false;
                        }
                    }
                    if (i == 0) {
                        arrays[i].get(j).set_Article_Is_Top(true);
                    }
                    else {
                        arrays[i].get(j).set_Article_Is_Top(false);
                    }
                    if (accept) {
                        all_articles.add(arrays[i].get(j));
                    }
                    else {
                        accept = true;
                    }
                }

            }
            return all_articles;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            //Display some new text above scroll wheel
        }

        @Override
        protected void onPostExecute(ArrayList<article> result) {
            // return null;showDialog("Downloaded " + result + " bytes");

            super.onPostExecute(result);
            // After completing http call
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

/*

    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ArrayList<article> local_Articles = new ArrayList<article>();




            for (int i = 0; i < num_Genres; i++) {

            //app_Articles = get_Articles();
        }



            return null;
        }

        private ArrayList<article> get_Articles()
        {
            int num_Genres = 5;
            InputStream in = null;
            String rss_Feed = null;
            URL urls[] = new URL[num_Genres];
            HttpURLConnection conns[] = new HttpURLConnection[num_Genres];
            ArrayList<article> local_Articles = new ArrayList<article>();


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

            for (int i = 0; i < num_Genres; i++) {
                try{
                    conns[i] = (HttpURLConnection) urls[i].openConnection();

                    in = conns[i].getInputStream();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    for (int count; (count = in.read(buffer)) != -1; ) {
                        out.write(buffer, 0, count);
                    }
                    byte[] response = out.toByteArray();
                    rss_Feed = new String(response, "UTF-8");
                    if (in != null) {
                        in.close();
                    }
                }
                catch(IOException bad1) {
                    bad1.printStackTrace();
                }
                catch(NetworkOnMainThreadException bad2) {
                    bad2.printStackTrace();
                }
            }

            //ID, title, body, genre, top, has image
            local_Articles.add(new article(1, "News Item 1", "This is the body of the article: News 1", "News", true, false));
            local_Articles.add(new article(2, "News Item 2", "This is the body of the article: News 2", "News", false, false));
            local_Articles.add(new article(3, "News Item 3", "This is the body of the article: News 3", "News", true, false));
            local_Articles.add(new article(4, "Sports Item 1", "This is the body of the article: Sports 1", "Sports", false, false));
            local_Articles.add(new article(5, "Sports Item 2", "This is the body of the article: Sports 2", "Sports", true, false));
            local_Articles.add(new article(6, "Sports Item 3", "This is the body of the article: Sports 3", "Sports", true, false));
            local_Articles.add(new article(7, "Opinion Item 1", "This is the body of the article: Opinion 1", "Opinion", true, false));
            local_Articles.add(new article(8, "Opinion Item 2", "This is the body of the article: Opinion 2", "Opinion", false, false));
            local_Articles.add(new article(9, "A&C Item 1", "This is the body of the article: Arts & Culture 1", "Arts & Culture", false, false));
            local_Articles.add(new article(10, "A&C Item 2", "This is the body of the article: Arts & Culture 2", "Arts & Culture", true, false));

            return local_Articles;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // After completing http call
            Intent article_List = new Intent(MainActivity.this, ArticleListActivity.class);
            article_List.putExtra("this_Genre", "Top News");
            article_List.putParcelableArrayListExtra("my_Articles", app_Articles);
            article_List.putExtra("first_Instance", true);
            startActivity(article_List);

            // close this activity
            finish();
        }

    } */



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
