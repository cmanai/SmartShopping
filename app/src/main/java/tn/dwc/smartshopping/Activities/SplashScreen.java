package tn.dwc.smartshopping.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import tn.dwc.smartshopping.Entities.Maps_Product;
import tn.dwc.smartshopping.Entities.Maps_Shelve;
import tn.dwc.smartshopping.Entities.Products;
import tn.dwc.smartshopping.Entities.Promotions;
import tn.dwc.smartshopping.Entities.User;

import tn.dwc.smartshopping.SQLite.Maps_ProductBDD;
import tn.dwc.smartshopping.SQLite.Maps_ShelveBDD;
import tn.dwc.smartshopping.SQLite.ProductsBDD;
import tn.dwc.smartshopping.SQLite.PromotionsBDD;
import tn.dwc.smartshopping.R;
import tn.dwc.smartshopping.SQLite.UserBDD;
import tn.dwc.smartshopping.Util.Utils;


public class SplashScreen extends Activity {
    private static final String TAG ="" ;
    ImageView logo;
    ImageView fb;
    ParseObject prod;
    Animation anim1;
    ParseUser user1;
    Bitmap bitmap;
    String firstname,lastname,birthday,email,picture;
    Animation anim2;
    Animation anim3;
    List<Promotions> l2 ;
    SharedPreferences sharedpreferences;
    Animation  slide;
    List<Maps_Shelve> l;
    private SimpleFacebook mSimpleFacebook;
    ScaleAnimation makeBigger;

    List<ParseObject> parseobj1;
    List<ParseObject> parseobj2;
    List<ParseObject> parseobj3;
    List<ParseObject> parseobj4;
    List<User> userlist;
    ProductsBDD productBDD;
    PromotionsBDD promotionBDD;
    Maps_ShelveBDD maps_shelveBDD;
    Maps_ProductBDD maps_productBDD;
    UserBDD userBDD;
    String Product_id;
    String Product_sheve_id;
    String Product_name;
    String Product_category;
    String Product_sub_category;
    String Product_price;
    String Product_created_at;

    String Promotion_id;
    String Promotion_sheve_id;
    String Promotion_name;
    String Promotion_category;
    String Promotion_sub_category;
    String Promotion_old_price;
    String Promotion_new_price;
    String Promotion_pourcentage;
    private BeaconManager beaconManager;
    private Region region;
    String Map_id;

    ProgressWheel progress;
    private static final ScheduledExecutorService worker =
            Executors.newSingleThreadScheduledExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        Parse.initialize(this, "reh7swdsWSGlxqjQhfB7KpRy3KbjhnltVE4dMmUS", "PgeJTNeLuZ3o0ULTVhCOu9SGUNCQrKonHuUCESO1");
         sharedpreferences = getSharedPreferences("SmartShopping", Context.MODE_PRIVATE);

        mSimpleFacebook = SimpleFacebook.getInstance();

        setContentView(R.layout.splash_screen_layout);
       // ParseObject.registerSubclass(Product.class);
       // Parse.initialize(this, "reh7swdsWSGlxqjQhfB7KpRy3KbjhnltVE4dMmUS", "PgeJTNeLuZ3o0ULTVhCOu9SGUNCQrKonHuUCESO1");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
        try {

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Promos");
            parseobj1 = query.find();
            query.getInBackground("oNe0n3wi9o", new GetCallback<ParseObject>() {

                @Override
                public void done(final ParseObject object, com.parse.ParseException e) {
                    if (e == null) {
                        for (final ParseObject prod : parseobj1){
                            Log.e("name ", (String) prod.get("Product_Name"));
                            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                            beaconManager = new BeaconManager(getApplicationContext());
                            beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                                @Override
                                public void onEnteredRegion(Region region, List<Beacon> list) {
                                    Beacon b = list.get(0);
                                    double x = calculateDistance(60,b.getRssi());
                                    //if((prod.getBoolean("Promotion") == true)){
                                    if(x<20) {
                                        showNotification(
                                                prod.get("Product_Name").toString(),
                                                "il est a " + (double)((int)(x*100))/100 + " meters.."
                                                        + " a "
                                                        + prod.get("PromosReduction").toString()+"%");
                                    }else{
                                        System.out.println("B3iiiiddddddd !!!!" + x);
                                    }
                                    // }

                                }
                                @Override
                                public void onExitedRegion(Region region) {
                                    // could add an "exit" notification too if you want (-:
                                }
                            });
                            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                                @Override
                                public void onServiceReady() {
                                    beaconManager.startMonitoring(new Region(
                                            "monitored region",
                                            UUID.fromString("b9407f37-f5f8-466e-aff9-25556b57fe6d"),
                                            1111, 193 ));
                                }
                            });

                            prod.getParseFile("Product_Image").getDataInBackground(new GetDataCallback() {

                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if (object == null) {
                                        Log.d("test", "The object was not found...");
                                    } else {
                                        Log.d("test", "Retrieved the object.");
                                        ParseFile fileObject = (ParseFile)object.get("Product_Image");
                                        fileObject.getDataInBackground(new GetDataCallback() {
                                            public void done(byte[] data, ParseException e) {
                                                if (e == null) {
                                                    Log.e("test", "We've got data in data.");
                                                    // use data for something
                                                    Context context = null;
//                                                    Drawable image = new BitmapDrawable(context.getResources(),BitmapFactory.decodeByteArray(data, 0, data.length));

                                                } else {
                                                    Log.e("test", "There was a problem downloading the data.");
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            region = new Region("ranged region", UUID.fromString("b9407f37-f5f8-466e-Aff9-25556b57fe6d"), null, null);


                        }
                    } else {
                        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+ e.getMessage());
                    }
                }
            });

        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "tn.dwc.smartshopping",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("KeyDir:", info.installLocation+" "+info.configPreferences);
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }


        logo = (ImageView) findViewById(R.id.icon_splash);
        fb=(ImageView)findViewById(R.id.fb);
        progress =(ProgressWheel)findViewById(R.id.progress_wheel);
        // start = (ImageView) findViewById(R.id.imageView1);

        productBDD = new ProductsBDD(SplashScreen.this);
        productBDD.open();
        productBDD.removeAllArticles();
        productBDD.close();

        promotionBDD = new PromotionsBDD(SplashScreen.this);
        promotionBDD.open();
        promotionBDD.removeAllArticles();
        promotionBDD.close();
        maps_shelveBDD = new Maps_ShelveBDD(SplashScreen.this);
        maps_shelveBDD.open();
        maps_shelveBDD.removeAllArticles();
        maps_shelveBDD.close();
        maps_productBDD = new Maps_ProductBDD(SplashScreen.this);
        maps_productBDD.open();
        maps_productBDD.removeAllArticles();
        maps_productBDD.close();
        userlist=new ArrayList<User>();
        userBDD = new UserBDD(SplashScreen.this);
        userBDD.open();
        userlist=userBDD.selectAll();
        userBDD.close();


        anim1 = AnimationUtils.loadAnimation(this, R.anim.animation1);
        anim1.reset();
        anim2 = AnimationUtils.loadAnimation(this, R.anim.animation2);
        anim2.reset();
        anim3 = AnimationUtils.loadAnimation(this, R.anim.animation3);
        anim3.reset();
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                progress.setVisibility(View.VISIBLE);
                progress.spin();
                fb.setVisibility(View.INVISIBLE);
                findViewById(R.id.textView).setVisibility(View.INVISIBLE);
                mSimpleFacebook.login( new OnLoginListener() {

                    @Override
                    public void onFail(String reason) {

                    }

                    @Override
                    public void onException(Throwable throwable) {

                    }

                    @Override
                    public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                        // change the state of the button or do whatever you want

                      //  loggedInUIState();


                        PictureAttributes pictureAttributes = Attributes.createPictureAttributes();
                        pictureAttributes.setHeight(500);
                        pictureAttributes.setWidth(500);
                        pictureAttributes.setType(PictureAttributes.PictureType.SQUARE);
                        Profile.Properties properties = new Profile.Properties.Builder()
                                .add(Profile.Properties.PICTURE, pictureAttributes)
                                .add(Profile.Properties.FIRST_NAME)
                                .add(Profile.Properties.LAST_NAME)
                                .add(Profile.Properties.EMAIL)
                                .add(Profile.Properties.BIRTHDAY)

                                .build();

                        // SimpleFacebook.getInstance().getProfile(new OnProfileListener() {
                        SimpleFacebook.getInstance().getProfile(properties, new OnProfileListener() {

                            @Override
                            public void onThinking() {

                                      /*  new MaterialDialog.Builder(SplashScreen.this)
                                                .title("Loading")
                                                .content("Please wait")
                                                .progress(true, 0)

                                                .theme(Theme.LIGHT)

                                                .progressIndeterminateStyle(false)
                                                .show();

*/

                            }

                            @Override
                            public void onException(Throwable throwable) {

                            }

                            @Override
                            public void onFail(String reason) {

                            }

                            @Override
                            public void onComplete(Profile response) {

                                String str = Utils.toHtml(response);
                                Log.e("Facebook connexion","done");


                                firstname=response.getFirstName();
                                lastname=response.getLastName();
                                email=response.getEmail();
                                birthday=response.getBirthday();
                                picture=response.getPicture();
// other fields can be set just like with ParseObject

                                ParseUser.logInInBackground(email,"facebookuser", new LogInCallback() {
                                    public void done(ParseUser user, ParseException e) {
                                        if (user != null) {

                                            new LoadImage().execute(picture);




                                            Log.e("Parse Login","done");
                                        } else {
                                            Log.e("Parse Login"," not done");
                                            user1 = new ParseUser();
                                            user1.setUsername(firstname+" "+lastname);
                                            user1.put("Birthday", "");
                                            user1.setEmail(email);
                                            user1.setPassword("facebookuser");
                                            user1.put("picture", picture);
                                            user1.signUpInBackground(new SignUpCallback() {
                                                public void done(ParseException e) {
                                                    if (e == null) {

                                                        new LoadImage().execute(picture);
                                                        Log.e("Parse Signup", "done");


                                                    } else {
                                                        Log.e("Parse Signup"," not done");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });


                            }
                        });

                    }

                    @Override
                    public void onCancel() {

                    }

                });
            }
        });

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                            logo.setVisibility(View.VISIBLE);
                                findViewById(R.id.name_app).setVisibility(View.VISIBLE);
                                logo.startAnimation(anim1);

                                YoYo.with(Techniques.FadeIn).duration(1500).playOn(findViewById(R.id.name_app));
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {



if(userlist.isEmpty()){

    findViewById(R.id.fb).setVisibility(View.VISIBLE);

    YoYo.with(Techniques.FadeIn).duration(1000).playOn(findViewById(R.id.fb));
    new Handler().postDelayed(new Runnable() {

        @Override
        public void run() {

            findViewById(R.id.textView).setVisibility(View.VISIBLE);
            YoYo.with(Techniques.FadeIn).duration(1000).playOn(findViewById(R.id.textView));


        }
    }, 500);






}else{
    progress.setVisibility(View.VISIBLE);
    progress.spin();
    l2 = new ArrayList<Promotions>();
    new RemoteDataTask().execute();





}


                                    }

                                }, 1500);
                            }
                        }, 1000);








    }



    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mSimpleFacebook.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setLogin() {
        // Login listener

    }
    private void setLogout() {



    }

    private void setUIState() {
        if (mSimpleFacebook.isLogin()) {
            loggedInUIState();
        } else {
            loggedOutUIState();
        }
    }

    private void loggedInUIState() {

    }

    private void loggedOutUIState() {

    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }




    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
              //  profile_pic = new BitmapDrawable(getResources(), image);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                User pr = new User(firstname+" "+lastname,email,birthday,b);

                userBDD = new UserBDD(SplashScreen.this);
                userBDD.open();
                // nBDD.removeAllArticles();
                userBDD.insertTop(pr);
                userBDD.close();
                l2 = new ArrayList<Promotions>();
                new RemoteDataTask().execute();

            }else{



            }
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {


            ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>(
                    "Product");
            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>(
                    "Promos");
           ParseQuery<ParseObject> query3 = new ParseQuery<ParseObject>(
                  "Maps_Shelve");
            ParseQuery<ParseObject> query4 = new ParseQuery<ParseObject>(
                    "Maps_Product");
            query1.orderByDescending("createdAt");

            try {


                parseobj1 = query1.find();
                parseobj2 = query2.find();
                parseobj3 = query3.find();
                parseobj4 = query4.find();

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try{
                for (final ParseObject prod : parseobj1) {

                    prod.getParseFile("Product_Image").getDataInBackground(new GetDataCallback() {

                        @Override
                        public void done(byte[] arg0, com.parse.ParseException arg1) {
                            // TODO Auto-generated method stub
                            // Bitmap bi = BitmapFactory.decodeByteArray(arg0, 0,
                            // arg0.length);
                            Products pr = new Products(prod.get("Product_Id").toString(), (String) prod.get("Shelve_Id")
                                    , (String) prod.get("Product_Name"),(String) prod.get("Product_Desc"), arg0, (String) prod.get("Product_Category"),
                                    (String) prod.get("Product_Type"), (String) prod.get("Product_Price"), (String) prod.get("createdAt"));

                            productBDD = new ProductsBDD(SplashScreen.this);
                            productBDD.open();
                            // nBDD.removeAllArticles();
                            productBDD.insertTop(pr);
                            productBDD.close();
                            Log.e("name ", (String) prod.get("Product_Name"));

                        }
                    });


                }
                for (final ParseObject prod2 : parseobj2) {


                    prod2.getParseFile("Product_Image").getDataInBackground(new GetDataCallback() {

                        @Override
                        public void done(byte[] arg0, com.parse.ParseException arg1) {
                            // TODO Auto-generated method stub
                            // Bitmap bi = BitmapFactory.decodeByteArray(arg0, 0,
                            // arg0.length);

                            Promotions pr = new Promotions((String) prod2.get("Product_Id"), (String) prod2.get("Shelve_Id"), (String) prod2.get("Product_Name")
                                   ,(String) prod2.get("Product_Desc") , arg0, (String) prod2.get("Product_Category"),
                                    (String) prod2.get("Product_Type"), (String) prod2.get("Product_Price"), (String) prod2.get("Product_Promo"),
                                    (String) prod2.get("PromosReduction"));

                            promotionBDD = new PromotionsBDD(SplashScreen.this);
                            promotionBDD.open();
                            // nBDD.removeAllArticles();
                            promotionBDD.insertTop(pr);
                            promotionBDD.close();

                            l2.add(pr);
                            Log.e("name promotion", (String) prod2.get("Product_Name"));

                        }
                    });


                }
                for (final ParseObject prod3 : parseobj3) {


                    prod3.getParseFile("Map_Sh").getDataInBackground(new GetDataCallback() {

                        @Override
                        public void done(byte[] arg0, com.parse.ParseException arg1) {
                            // TODO Auto-generated method stub
                            // Bitmap bi = BitmapFactory.decodeByteArray(arg0, 0,
                            // arg0.length);
                            Maps_Shelve pr = new Maps_Shelve((String) prod3.get("Shelve_Id"), arg0,(String) prod3.get("Qr_ID"),(String) prod3.get("H_B"));

                            maps_shelveBDD = new Maps_ShelveBDD(SplashScreen.this);
                            maps_shelveBDD.open();
                            // nBDD.removeAllArticles();
                            maps_shelveBDD.insertTop(pr);
                            maps_shelveBDD.close();
                            Log.e("map id", (String) prod3.get("Shelve_Id"));


                        }
                    });


                }
                for (final ParseObject prod4 : parseobj4) {

                    prod4.getParseFile("Map_P").getDataInBackground(new GetDataCallback() {

                        @Override
                        public void done(byte[] arg0, com.parse.ParseException arg1) {
                            // TODO Auto-generated method stub
                            // Bitmap bi = BitmapFactory.decodeByteArray(arg0, 0,
                            // arg0.length);
                            if(arg0!=null) {
                                Maps_Product pr = new Maps_Product(prod4.get("Shelve_Id").toString(), arg0, (String) prod4.get("Product_Id"),
                                        (String) prod4.get("H_B"));

                                maps_productBDD = new Maps_ProductBDD(SplashScreen.this);
                                maps_productBDD.open();
                                // nBDD.removeAllArticles();
                                maps_productBDD.insertTop(pr);
                                maps_productBDD.close();
                                Log.e("id map product ", (String) prod4.get("Product_Id"));
                                Log.e("id map product ", (String) prod4.get("Shelve_Id"));
                            }
                            else{
                                Log.e("id map product ", "chayyyyyyyyyyyyyy");
                            }
                            }

                    });


                }

            }catch (Exception e ){


            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    progress.setVisibility(View.INVISIBLE);

                    Intent i = new Intent(SplashScreen.this, Container.class);
                    //  i.putExtra("profile_pic",b);
                    startActivity(i);
                    finish();
                }

            }, 23000);



        }


    }
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, SplashScreen.class);

        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);

    }


    protected final static double calculateDistance(int txPower, int rssi) {
        if (rssi == 0) {
            return -1.0;
        }

        double ratio = rssi*1.0/txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio,10);
        }
        else {
            double accuracy =  (0.89976)*Math.pow(ratio,7.7095) + 0.111;
            return accuracy;
        }
    }

}
