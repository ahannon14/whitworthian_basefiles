package mayhem.whitworthian_v2.app;

/**
 * This class contains the information displayed in the Article List Activity.  Contains the
 * following elements:
 *  icon - Integer referring to the default icon of the image
 *  title - The title of the article
 *  id - The article ID
 *  desc - the Article blurb/description
 *  icon_URL - The article icon's URL, if available.
 */
public class article_Selection {
    private int icon;
    private String title;
    private int id;
    private String desc;
    private String icon_URL;

    /* Default Constructor */
    public article_Selection(){
        super();
    }

    /* Constructor for article_Selection when information is available */
    public article_Selection(int icon, String title, String desc, int id){
        super();
        this.icon = icon;
        this.title = title;
        this.desc = desc;
        this.id = id;
    }

    /* Accessors */
    public int get_Icon() {return this.icon;}
    public int get_ID() {return this.id;}
    public String get_Title() {return this.title;}
    public String get_Desc() {return this.desc;}
    public String get_icon_URL() {return this.icon_URL;}
}
