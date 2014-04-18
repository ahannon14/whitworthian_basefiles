package mayhem.whitworthian_v2.app;

/**
 * Created by Evan Anders on 4/16/14.
 */
public class article_Selection {
    public int icon;
    public String title;
    public int id;
    public article_Selection(){
        super();
    }

    public article_Selection(int icon, String title){
        super();
        this.icon = icon;
        this.title = title;
    }

    public article_Selection(int icon, String title, int id){
        super();
        this.icon = icon;
        this.title = title;
        this.id = id;
    }
}
