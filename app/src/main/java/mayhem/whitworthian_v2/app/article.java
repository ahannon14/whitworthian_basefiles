import mayhem.whitworthian_v2.app.R;

/**
 * Created by Evan Anders on 4/21/14.
 */
public class article {
    private String title;
    private String body;
    private String genre;
    private Boolean is_Top;
    private Boolean has_Image;
    private int image_ID;
    private String image_URL;

    public article(String title, String body, String genre, Boolean is_Top, Boolean has_Image,
                   String image_URL){
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
        else if (this.genre.equals("Opinion")){
            this.image_ID = R.drawable.opinions_box;
        }
        else{
            this.image_ID = R.drawable.whitworthian_w;
        }
    }

    /* Accessors */
    public String get_Title(){
        return this.title;
    }
    public String get_Body(){
        return this.body;
    }
    public String get_Genre(){
        return this.genre;
    }
    public Boolean is_Top(){
        return is_Top;
    }
    public Boolean get_Has_Image(){
        return has_Image;
    }
    public int get_image_ID(){
        return image_ID;
    }
    public String get_image_URL(){
        return image_URL;
    }



}
