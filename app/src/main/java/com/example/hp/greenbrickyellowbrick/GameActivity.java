package moh.theamazingappco.bricks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements RewardedVideoAdListener {

    TextView lblCounter,lblAnswer;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAd1;
    private RewardedVideoAd mRewardedVideoAd;
    EditText txtWord;
    Button btnSend,btnPlayAgain,btnExtra;
    int counter=9;
    String originalWord;

    boolean doubleBackToExitPressedOnce = false;

   // Dialog customDialog = new Dialog(GameActivity.this);

    ArrayList<String> wordsList = new ArrayList<String>(Arrays.asList("able","acid","aged","also","area","army","away","baby","back","ball",
            "band","bank","base","bath","bear","beat","been","beer","bell","belt","best","bill","bird","blow","blue","boat","body",
            "bomb","bond","bone","book","boom","born","boss","both","bowl","bulk","burn","bush","busy","call","calm","came","camp",
            "card","care","case","cash","cast","cell","chat","chip","city","club","coal","coat","code","cold","come","cook","cool",
            "cope","copy","core","cost","crew","crop","dark","data","date","dawn","days","dead","deal","dean","dear","debt","deep",
            "deny","desk","dial","dick","diet","disc","disk","does","done","door","dose","down","draw","drew","drop","drug","dual",
            "duke","dust","duty","each","earn","ease","east","easy","edge","else","even","ever","evil","exit","face","fact","fail",
            "fair","fall","farm","fast","fate","fear","feed","feel","feet","fell","felt","file","fill","film","find","fine","fire",
            "firm","fish","five","flat","flow","food","foot","ford","form","fort","four","free","from","fuel","full","fund","gain",
            "game","gate","gave","gear","gene","gift","girl","give","glad","goal","goes","gold","Golf","gone","good","gray","grew",
            "grey","grow","gulf","hair","half","hall","hand","hang","hard","harm","hate","have","head","hear","heat","held","hell",
            "help","here","hero","high","hill","hire","hold","hole","holy","home","hope","host","hour","huge","hung","hunt","hurt",
            "idea","inch","into","iron","item","jury","jump","jean","jean","join","jump","jury","just","keen","keep","kent","kept",
            "kick","kill","kind","king","knee","knew","know","lack","lady","laid","lake","land","lane","last","late","lead","left",
            "less","life","lift","like","line","link","list","live","load","loan","lock","logo","long","look","lord","lose","loss",
            "lost","love","luck","made","mail","main","make","male","many","Mark","mass","matt","meal","mean","meat","meet","menu",
            "mere","mike","mile","milk","mill","mind","mine","miss","mode","mood","moon","more","most","move","much","must","name",
            "navy","near","neck","need","news","next","nice","nick","nine","none","nose","note","okay","once","only","onto","open",
            "oral","over","pace","pack","page","paid","pain","pair","palm","park","part","pass","past","path","peak","pick","pink",
            "pipe","plan","play","plot","plug","plus","poll","pool","poor","port","post","pull","pure","push","race","rail","rain",
            "rank","rare","rate","read","real","rear","rely","rent","rest","rice","rich","ride","ring","rise","risk","road","rock",
            "role","roll","roof","room","root","rose","rule","rush","ruth","safe","said","sake","sale","salt","same","sand","save",
            "seat","seed","seek","seem","seen","self","sell","send","sent","sept","ship","shop","shot","show","shut","sick","side",
            "sign","site","size","skin","slip","slow","snow","soft","soil","sold","sole","some","song","soon","sort","soul","spot",
            "star","stay","step","stop","such","suit","sure","take","tale","talk","tall","tank","tape","task","team","tech","tell",
            "tend","term","test","text","than","that","them","then","they","thin","this","thus","till","time","tiny","told","toll",
            "tone","tony","took","tool","tour","town","tree","trip","true","tune","turn","twin","type","unit","upon","used","user",
            "vary","vast","very","vice","view","vote","wage","wait","wake","walk","wall","want","ward","warm","wash","wave","ways",
            "weak","wear","week","well","went","were","west","what","when","whom","wide","wife","wild","will","wind","wine","wing",
            "wire","wise","wish","with","wood","word","wore","work","yard","yeah","year","your","zero","zone","week"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setTitle("Bricks Easy Mode");

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();

        adapter = new UserTextAdapter(data);
        recyclerView.setAdapter(adapter);

        lblAnswer = (TextView) findViewById(R.id.lblAnswer);
        lblCounter = (TextView) findViewById(R.id.lblCounter);
        txtWord = (EditText) findViewById(R.id.txtText);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        btnExtra = (Button) findViewById(R.id.extraLife);
      //  btnAlpha = (Button) findViewById(R.id.Alpha);
        btnPlayAgain.setVisibility(View.INVISIBLE);

        Random r = new Random();
        int check=1;
        while(check==1) {
            originalWord = wordsList.get(r.nextInt(500)).toLowerCase();
            if(isUnique(originalWord))
                check=0;
            else
                check=1;
            Log.d("Word",originalWord);
        }

        lblAnswer.setVisibility(View.INVISIBLE);
        lblAnswer.setText("The Word was: "+originalWord);

//        btnAlpha.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd1 = new InterstitialAd(this);
        loadInterstitialAd("ca-app-pub-6189499490928275/1988796256");
        initializeInterstitialAd("ca-app-pub-6189499490928275~1410551068");
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        btnExtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }
        });

        mInterstitialAd1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                mInterstitialAd.getAdListener().onAdClosed();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(i);
                    finish();
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //split the word then find number of red bricks and blue bricks and then decrement counter by 1;
                String myText = txtWord.getText().toString().toLowerCase();
                if(myText.matches("[a-zA-Z]+")) {
                    if (isUnique(myText)) {
                        if (myText.length() == 4) {
                            lblCounter.setText(Integer.toString(counter--));
                            txtWord.setText("");
                            changeColor();
                            String cards = getCards(myText, originalWord);
                            if (cards.contains("123")) {
                                btnSend.setVisibility(View.INVISIBLE);
                                data.add(new DataModel(cards));
//                                listItems.add("You have guessed the right word : " + myText + " Congratulations");
                                txtWord.setVisibility(View.INVISIBLE);
                                lblAnswer.setVisibility(View.VISIBLE);
                                lblAnswer.setTextColor(Color.GREEN);
                                lblAnswer.setText( originalWord+" is correct");
                                btnPlayAgain.setVisibility(View.VISIBLE);
                                recyclerView.setVerticalScrollBarEnabled(Boolean.TRUE);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(btnSend.getWindowToken(), 0);
                            } else {
//                                listItems.add(cards);
//                                adapter.notifyDataSetChanged();
                                  data.add(new DataModel(cards));
                                //recyclerView.scheduleLayoutAnimation();
                                adapter.notifyDataSetChanged();
                            }
                            txtWord.onEditorAction(EditorInfo.IME_ACTION_DONE);

                            if (counter == -1 && !(cards.contains("123"))) {
                                btnSend.setVisibility(View.INVISIBLE);
                                lblAnswer.setVisibility(View.VISIBLE);
                                lblAnswer.setTextColor(Color.RED);
                                btnPlayAgain.setText("You Lost, Try Again?");
                                btnPlayAgain.setVisibility(View.VISIBLE);
                                btnExtra.setVisibility(View.INVISIBLE);
                                txtWord.setVisibility(View.INVISIBLE);
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(btnSend.getWindowToken(), 0);
                            }

                        } else {
                            Toast.makeText(GameActivity.this, "Should be a valid 4 letter word",
                                       Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if(myText.equals("shreetha")){
                            getSupportActionBar().setTitle("hmm..");
                            txtWord.setText("");
                        }else {
                            Toast.makeText(GameActivity.this, "Letters cannot be repeated",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(GameActivity.this, "Should contain Valid Letters",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd1.isLoaded()) {
                    mInterstitialAd1.show();
                } else {
                    Intent i = new Intent(getApplicationContext(), GameActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });


    }

    public String getCards(String input,String Original){
        String cardString;
        char inputA[] = input.toCharArray();
        char mywordA[] = Original.toCharArray();
        int rb=0,gb=0;
        for(int i=0;i<4;i++) {

            if (mywordA[i] == inputA[i]) {
                gb++;
                continue;
            } else {
                if ((input.indexOf(mywordA[i])) >= 0) {
                    rb++;
                    continue;
                }
            }
        }
        String redString = ","+Integer.toString(rb);
        String greenString = ","+Integer.toString(gb);

        cardString = redString+greenString;

        String finalSting = input + cardString;

        if(gb==4)
            finalSting = finalSting + ",123";

        return finalSting;
    }
    public static boolean isUnique(String str) {
        if (str.length() == 0) {
            return true;
        }

        boolean[] seen = new boolean[26];
        for (int i = 0; i < str.length(); i++) {
            int index = Character.toLowerCase(str.charAt(i)) - 'a';
            if (seen[index]) {
                return false;
            }
            else {
                seen[index] = true;
            }
        }
        // invariant
        return true;
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-6189499490928275/5799972775",
                new AdRequest.Builder().build());


    }

    public void changeColor(){
        if((counter<=100) && (counter>=5) ){
            lblCounter.setTextColor(Color.GREEN);
        }
        if((counter<=4) && (counter>=3) ){
            lblCounter.setTextColor(Color.YELLOW);
        }
        if((counter<3)) {
            lblCounter.startAnimation(shakeError());
            lblCounter.setTextColor(Color.RED);
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.helpmenu, menu);
        return true;
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public void initializeInterstitialAd(String s){
        MobileAds.initialize(this, s);
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {
        Toast.makeText(this, "Watch the full ad to get extra life", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        Log.d("word","Came here counter was: "+counter);
        counter = counter+1;
        Log.d("word","Came here counter is now: "+counter);
        Log.d("word","Came here lblCounter was: "+lblCounter.getText());
        lblCounter.setText(Integer.toString(counter+1));
        Log.d("word","Came here lblCounter is now: "+lblCounter.getText());
        lblCounter.invalidate();
        changeColor();
        lblCounter.requestLayout();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {
        Toast.makeText(this, "Extra life added, please close the ad", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
    public void loadInterstitialAd(String s){
        mInterstitialAd1.setAdUnitId(s);
        mInterstitialAd1.loadAd(new AdRequest.Builder().build());
    }
}

/*
String input = "abcd";
        String myword = "abal";
        char inputA[] = input.toCharArray();
        char mywordA[] = myword.toCharArray();

        int rb=0,gb=0;

        if((isUnique(myword))){
            for(int i=0;i<4;i++){

                if(mywordA[i]==inputA[i]){
                    gb++;
                    continue;
                }
                else{
                    if((input.indexOf(mywordA[i]))>=0){
                        rb++;
                        continue;
                    }
                }
            }
        }
        else{
            System.out.println("Dont use repetative lettes");
        }

        System.out.println("Green Brick:"+gb+"\tRed Brick:"+rb);

    }

    public static boolean isUnique(String str) {
        if (str.length() == 0) {
          return true;
        }
        boolean[] seen = new boolean[25];
        for (int i = 0; i < str.length(); i++) {
          int index = Character.toLowerCase(str.charAt(i)) - 'a';
          if (seen[index]) {
            return false;
          }
          else {
            seen[index] = true;
          }
        }
        // invariant
        return true;
  }
 */


/*
SOIL
fort  1green brick
lamp 1 red brcik
 */