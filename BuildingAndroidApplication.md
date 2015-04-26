# Be together. Not the same. #

![http://i.imgur.com/3DyzdDY.jpg](http://i.imgur.com/3DyzdDY.jpg)

## Android Operating System ##

Android is the most widely used and the highest-selling mobile operating system (OS) based on the Linux kernel. Android is primarily lead by Google and designed mainly for touchscreen mobile devices such as smartphones and tablet computers, with specialized user interfaces for televisions (Android TV), cars (Android Auto), and wrist watches (Android Wear).

The Android system supports background processing, provides a rich user interface library, supports 2-D and 3-D graphics using the OpenGL-ES (shortly OpenGL or Open Graphics Library) standard and grants access to the file system as well as an embedded SQLite database.

![http://www.vogella.com/tutorials/Android/images/xandroidsoftwarelayer10.png.pagespeed.ic.cqHvO0-Uh9.png](http://www.vogella.com/tutorials/Android/images/xandroidsoftwarelayer10.png.pagespeed.ic.cqHvO0-Uh9.png)






## Introducing Development Tools ##

Android Software Development Kit (SDK): Android SDK includes development tools to create, compile Android applications. Applications are written based on the Java programming language and run on Dalvik, a virtual machine runs on top of a Linux Kernel.

Android debug bridge (ADB): Development tool which provides connection to a virtual or real Android device, for managing the device or debugging your application.

Android Run Time (ART): Android runtime is the managed runtime used by applications and some system services on Android. ART and Dalvik are compatible runtimes running Dex bytecode, so apps developed for Dalvik should work when running with ART.

Android Developer Tools and Android Studio: ADT and Android Studio both have all the functionalities to create, compile, debug and deploy Android applications. Android Studio is the official IDE for Android application development, based on IntelliJ IDEA. ADT which is based on eclipse IDE is also widely used.






## Developing Android Applications ##

<a href='http://www.youtube.com/watch?feature=player_embedded&v=Q8TXgCzxEnw' target='_blank'><img src='http://img.youtube.com/vi/Q8TXgCzxEnw/0.jpg' width='425' height=344 /></a>

Android applications are primarily written in the Java programming language and there are two primary integrated development environments (IDE) for Android. An IDE is the main program where you'll write code and put your app together. It can help you organize and edit the various files in your app, manage the packages and supporting libraries your app will need, and test it out on real devices or emulators.

The default IDE for Android is Eclipse. Eclipse allows you to modify Java and XML files and organize the various pieces of your application, among many other tasks. The version you get from Google also includes a package manager that allows you to update to the latest version of Android tools as soon as Google releases them.

The main alternative is Android Studio, which is currently being made directly by Google. Like many Google projects, Android Studio is part of a prolonged beta. The long-term intention is for Android Studio to replace Eclipse as the primary IDE for Android development. That doesn't necessarily mean it's for everyone. For example, if you need to make use of the Native Development Kit for apps like games (hint: if you need it, you probably already know you need it), Eclipse is mandatory. However, Android Studio is a good option if you want to get a jump start on the future, and you're willing to tolerate some possible bugs.


## Sample Program ##

Plain Android application has several important folders and files, this tutorial will outline these to create a hello world application. This one will be design in Android Studio and use gradle build system although it will not be that important for this project.

When Android Studios application creation wizard followed to create blank application with one activity, this will be the resulting project file structure. Most important files that will be shown in here are indicated in it. Red dots are important folders and black ones will be shown below...

http://i.imgur.com/HrndSWh.png?3

**AndroidManifest.xml** contains, names of activities, services, permissions therefore it could be considered as header of the application.

```xml

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
package="com.temren.kerata.swe" >

<application
android:allowBackup="true"
android:icon="@mipmap/ic_launcher"
android:label="@string/app_name"
android:theme="@style/AppTheme" >
<activity
android:name=".MainActivity"
android:label="@string/app_name" >
<intent-filter>

<action android:name="android.intent.action.MAIN" />
<category android:name="android.intent.category.LAUNCHER" />


Unknown end tag for &lt;/intent-filter&gt;





Unknown end tag for &lt;/activity&gt;





Unknown end tag for &lt;/application&gt;





Unknown end tag for &lt;/manifest&gt;


```

**activity\_main.xml** is an example xml file for layout design of our hello world program which only displays a string "Hello World!"

```xml

<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools" android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".MainActivity">

<TextView android:text="@string/hello_world" android:layout_width="wrap_content"
android:layout_height="wrap_content" />



Unknown end tag for &lt;/RelativeLayout&gt;


```

**menu\_main.xml** is the xml file which defines actionbar layout.

```xml

<menu xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools" tools:context=".MainActivity">

<item android:id="@+id/action_settings" android:title="@string/action_settings"
android:orderInCategory="100" app:showAsAction="never" />



Unknown end tag for &lt;/menu&gt;


```

**MainActivity.java** will be called onCreate of the application as indicated in AndroidManifest.xml

```java
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
getMenuInflater().inflate(R.menu.menu_main, menu);
return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
int id = item.getItemId();

//noinspection SimplifiableIfStatement
if (id == R.id.action_settings) {
return true;
}

return super.onOptionsItemSelected(item);
}
}
```

**string.xml** contains string values which has been referenced throughout the previous files.

```xml

<resources>

<string name="app_name">SWE 

Unknown end tag for &lt;/string&gt;



<string name="hello_world">Hello world! 

Unknown end tag for &lt;/string&gt;



<string name="action_settings">Settings 

Unknown end tag for &lt;/string&gt;





Unknown end tag for &lt;/resources&gt;


```

# Some Useful Links #

  * http://en.wikipedia.org/wiki/Android_(operating_system)
  * http://en.wikipedia.org/wiki/Android_software_development
  * http://source.android.com/
  * http://developer.android.com/
  * http://lifehacker.com/i-want-to-write-android-apps-where-do-i-start-1643818268

[![](http://cyrilmottier.com/images/logo@1x.png)](http://cyrilmottier.com/)
[![](http://www.vogella.com/img/logo/xindex_logo.png.pagespeed.ic.W70tcDQqll.png)](http://www.vogella.com/tutorials/Android/article.html)