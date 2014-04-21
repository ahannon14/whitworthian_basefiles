This contains the necessary files to make the Whitworthian App run.  Note: package name must be "mayhem.whitworthian_v2.app"

Notes:

Min SDK version: 14

Target SDK version: 19


HOW TO PUT ONTO SCHOOL COMPUTER:


1. New Project

____________________________
2. App Name: whitworthian_v2
3. Module Name: app
4. Package Name: mayhem.whitworthian_v2.app
5. Min API: 14
6. Compile with: 19
7. Language level: 6.0
8. Theme: Holo Light with Dark Action Bar
9. Leave check boxes alone (yes - create custom launcher icon, create activity.  no - mark this project as a library)
10. Click next until project creation complete

_______________________________________________________
1.Create the following activities:

ArticleListActivity
ArticleViewActivity
GenreListActivity


2.Create the following java classes:

article_Selection
article_Selection_Adapter


3.Create the following layouts:

article_list_item_row

_____________________________________________________________________________________
1. git init just inside of whitworthian_v2 folder(where you see app, build.gradle, etc.)
2. git remote add origin https://github.com/Flauschig/whitworthian_basefiles.git
3. git fetch --all
4. git reset --hard origin/master

_______________________________________________
1. Make sure you have android support repository installed in sdk manager (using C:\Android-SDK-Eclipse\SDK Manager)
2. file-> project structure -> Android SDK in platform settings -> android sdk location: C:\Android-SDK-Eclipse\sdk
3. Ensure the following is in your dependencies block (at the bottom of build.gradle)

dependencies {
    compile 'com.android.support:support-v4:+'
    compile 'com.android.support:appcompat-v7:+'
}

4. Build and run!