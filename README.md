# Social Login
Very simple implementaion for social login on android

[![](https://jitpack.io/v/RogaLabs/social-login.svg)](https://jitpack.io/#RogaLabs/social-login)
[![AndroidArsenal](https://img.shields.io/badge/Android%20Arsenal-SocialLogin-green.svg?style=true)](https://android-arsenal.com/details/1/3987)


## Install 

In `build.gradle` __root__ level add that:
```groovy
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        ...
        classpath 'com.google.gms:google-services:3.0.0'
        ...
    }
}


allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

In `build.gradle` __app__ level add that:

```groovy
apply plugin: 'com.google.gms.google-services'
...

dependencies {
    ...
    compile 'com.github.RogaLabs:social-login:[latest-version]'
    ...
}

```

## Usage

### Is required

#### In Google login 
[Register an app on Google Developers console](https://developers.google.com/mobile/add?platform=android&cntapi=signin&cnturl=https:%2F%2Fdevelopers.google.com%2Fidentity%2Fsign-in%2Fandroid%2Fsign-in%3Fconfigured%3Dtrue&cntlbl=Continue%20Adding%20Sign-In)
after that, you should ensure which the file __google-services.json__ is inside `app` module

#### In Facebook login
[Create an app on Facebook Developers](https://developers.facebook.com/docs/facebook-login/android)

after

add in your `string.xml`:
```xml
<string name="facebook_app_id">1320760547953881</string>
```

and 

```xml
<application android:label="@string/app_name" ...>
    ...
    <meta-data android:name="com.facebook.sdk.ApplicationId" android:value="@string/facebook_app_id"/>
    ...
</application>
```


Init the Social Login on your application:

```java
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LoginApplication.startSocialLogin(this);
    }
}
```


In your Login Activity you should extends one specific `class`:

```java
public class YourLoginActivity extends LoginView 

```

Login with google:

```java
loginWithGoogle(new Callback() {
                    @Override
                    public void onSuccess(SocialUser socialUser) {
                        buildProfileDialog(socialUser);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
```

Login with Facebook:
```java
loginWithFacebook(new Callback() {
                    @Override
                    public void onSuccess(SocialUser socialUser) {
                        buildProfileDialog(socialUser);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
```

## Sample

[Sample](https://github.com/RogaLabs/sample-social-login)

## License
[License](LICENSE)
