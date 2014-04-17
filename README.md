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


_____________________________________________________________________________________
14. git init just inside of whitworthian_v2 folder(where you see app, build.gradle, etc.)
15. git remote add origin https://github.com/Flauschig/whitworthian_basefiles.git
16. git fetch --all
17. git reset --hard origin/master

_______________________________________________
18. Open android studio and import project folder.
19. Make sure you have android support repository installed in sdk manager (using C:\Android-SDK-Eclipse\SDK Manager)
20. file-> project structure -> Android SDK in platform settings -> android sdk location: C:\Android-SDK-Eclipse\sdk
