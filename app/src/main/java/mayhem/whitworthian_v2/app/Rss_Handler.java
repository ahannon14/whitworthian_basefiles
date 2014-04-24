package mayhem.whitworthian_v2.app;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Rss_Handler extends DefaultHandler {

    // Feed and Article objects to use for temporary storage
    private article this_Article = new article();
    private ArrayList<article> articleList = new ArrayList<article>();
    private int counter= 0;

    // Number of articles added so far
    private int articlesAdded = 0;

    // Number of articles to download
    private static final int ARTICLES_LIMIT = 20;

    //Current characters being accumulated
    StringBuffer chars = new StringBuffer();

    public Rss_Handler(int id_base){

        counter = id_base;
    }


    public ArrayList<article> getArticleList() {
        return articleList;
    }

    /*
     * This method is called everytime a start element is found (an opening XML marker)
     * here we always reset the characters StringBuffer as we are only currently interested
     * in the the text values stored at leaf nodes
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        chars = new StringBuffer();
    }

    /*
     * This method is called everytime an end element is found (a closing XML marker)
     * here we check what element is being closed, if it is a relevant leaf node that we are
     * checking, such as Title, then we get the characters we have accumulated in the StringBuffer
     * and set the current Article's title to the value
     *
     * If this is closing the "entry", it means it is the end of the article, so we add that to the list
     * and then reset our Article object for the next one on the stream
     *
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String genre = null;
        if (localName.equalsIgnoreCase("title")){
            this_Article.set_Article_Title(chars.toString());
        } else if (localName.equalsIgnoreCase("encoded")){
            this_Article.set_Article_Body(chars.toString());
        } else if (localName.equalsIgnoreCase("category")) {
            String local = chars.toString();
            if (local.contains("Sports")) {
                genre = "Sports";
                this_Article.set_Article_Genre(genre);
            } else if (local.contains("News")) {
                genre = "News";
                this_Article.set_Article_Genre(genre);
            } else if (local.contains("Opinions")) {
                genre = "Opinions";
                this_Article.set_Article_Genre(genre);
            } else if (local.contains("Arts & Culture")) {
                genre = "Arts & Culture";
                this_Article.set_Article_Genre(genre);
            }


        }




        // Check if looking for article, and if article is complete
        if (localName.equalsIgnoreCase("item") && !(this_Article.get_Title().equals("The Whitworthian"))) {
            this_Article.set_Article_ID(counter + 1);
            this_Article.set_Article_Has_Image(false);
            counter++;

            articleList.add(this_Article);
            this_Article = new article();
            // Lets check if we've hit our limit on number of articles
            articlesAdded++;
            if (articlesAdded >= ARTICLES_LIMIT)
            {
                throw new SAXException();
            }
        }
    }


    /*
     * This method is called when characters are found in between XML markers, however, there is no
     * guarante that this will be called at the end of the node, or that it will be called only once
     * , so we just accumulate these and then deal with them in endElement() to be sure we have all the
     * text
     *
     * (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
     */
    public void characters(char ch[], int start, int length) {
        chars.append(new String(ch, start, length));
    }
}