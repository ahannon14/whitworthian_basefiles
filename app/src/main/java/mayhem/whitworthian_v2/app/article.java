package mayhem.whitworthian_v2.app;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Evan Anders on 4/21/14.
 */
public class article implements Parcelable {
    private int article_ID;
    private String title;
    private String body;
    private String genre;
    private Boolean is_Top;
    private Boolean has_Image;
    private int image_ID;
    private String image_URL;


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(article_ID);
        out.writeString(title);
        out.writeString(body);
        out.writeString(genre);
        out.writeByte((byte) (is_Top ? 1: 0));
        out.writeByte((byte) (has_Image ? 1 : 0));
        out.writeInt(image_ID);
        out.writeString(image_URL);

    }

    public static final Parcelable.Creator<article> CREATOR
            = new Parcelable.Creator<article>() {
        public article createFromParcel(Parcel in) {
            return new article(in);
        }

        public article[] newArray(int size) {
            return new article[size];
        }
    };

    private article(Parcel in) {
        article_ID = in.readInt();
        title = in.readString();
        body = in.readString();
        genre = in.readString();
        is_Top = in.readByte() != 0;
        has_Image = in.readByte() != 0;
        image_ID = in.readInt();
        image_URL = in.readString(); 
    }

    public article(){
        int article_ID = 0;
        String title = "";
        String body = "";
        String genre = "";
        Boolean is_Top = false;
        Boolean has_Image = false;
        int image_ID = 0;
        String image_URL = null;
    }

    public article(int article_ID, String title, String body, String genre, Boolean is_Top, Boolean has_Image,
                    String image_URL){
        this.article_ID = article_ID;
        this.title = title;
        this.body = body;
        this.genre = genre;
        this.is_Top = is_Top;
        this.has_Image = has_Image;

        if (this.has_Image)
        {
            this.image_URL = image_URL;
        }
        else{
            this.image_URL = null;
        }

        //Set up default image
        if (this.genre.equals("News")){
            this.image_ID = R.drawable.news_box;
        }
        else if (this.genre.equals("Sports")){
            this.image_ID = R.drawable.sports_box;
        }
        else if (this.genre.equals("Arts & Culture")){
            this.image_ID = R.drawable.ac_box;
        }
        else if (this.genre.equals("Opinions")){
            this.image_ID = R.drawable.opinions_box;
        }
        else{
            this.image_ID = R.drawable.whitworthian_w;
        }
    }

    public article(int article_ID, String title, String body, String genre, Boolean is_Top, Boolean has_Image){
        this.article_ID = article_ID;
        this.title = title;
        this.body = body;
        this.genre = genre;
        this.is_Top = is_Top;
        this.has_Image = has_Image;

        if (this.has_Image)
        {
            this.image_URL = image_URL;
        }
        else{
            this.image_URL = null;
        }

        //Set up default image
        if (this.genre.equals("News")){
            this.image_ID = R.drawable.news_box;
        }
        else if (this.genre.equals("Sports")){
            this.image_ID = R.drawable.sports_box;
        }
        else if (this.genre.equals("Arts & Culture")){
            this.image_ID = R.drawable.ac_box;
        }
        else if (this.genre.equals("Opinions")){
            this.image_ID = R.drawable.opinions_box;
        }
        else{
            this.image_ID = R.drawable.whitworthian_w;
        }
    }


    /* Accessors */
    public int get_Article_ID() { return this.article_ID; }
    public String get_Title(){
        return this.title;
    }
    public String get_Body(){
        return this.body;
    }
    public String get_Genre(){
        return this.genre;
    }
    public Boolean is_Top(){return is_Top;}
    public Boolean get_Has_Image(){
        return has_Image;
    }
    public int get_image_ID(){
        return image_ID;
    }
    public String get_image_URL(){
        return image_URL;
    }

    /* Setters */
    public void set_Article_ID(int id) {this.article_ID = id;}
    public void set_Article_Title(String title) {this.title = title;}
    public void set_Article_Body(String body) {this.body = body;}
    public void set_Article_Genre(String genre) {
        this.genre = genre;
        //Set up default image
        if (this.genre.equals("News")){
            this.image_ID = R.drawable.news_box;
        }
        else if (this.genre.equals("Sports")){
            this.image_ID = R.drawable.sports_box;
        }
        else if (this.genre.equals("Arts & Culture")){
            this.image_ID = R.drawable.ac_box;
        }
        else if (this.genre.equals("Opinions")){
            this.image_ID = R.drawable.opinions_box;
        }
        else{
            this.image_ID = R.drawable.whitworthian_w;
        }}
    public void set_Article_Is_Top(boolean is_Top) {this.is_Top = is_Top;}
    public void set_Article_Has_Image(boolean has_Image) {this.has_Image = has_Image;}
    public void set_image_URL(String image_URL) {this.image_URL = image_URL;}



}
