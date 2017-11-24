package com.example.maruta.guessthecelebrity;

import android.graphics.Bitmap;
import android.os.PatternMatcher;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //Ui
    private Button button1, button2, button3, button4;
    private Button[] buttons;

    private ImageView celebPhoto;
    private Bitmap bitMap;


    private DataDownloader html;

    private String content;
    private String[] mainContent, celebs;
    private String[] photos, names;

    private int bound;
    private int choice;
    private int temp_celeb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUi();

        html = new DataDownloader();


        try {

            content = html.execute("http://www.icelebz.com/topcelebs1.html").get();

        }catch (InterruptedException ex){

            ex.printStackTrace();

        }catch (ExecutionException ex){

            ex.printStackTrace();
        }

        dataSplit();

        bound = photos.length;

        generateGame();


    }

    private void generateGame() {

        Random rng = new Random();

        ImageDownloader imgDownload = new ImageDownloader();

        temp_celeb = rng.nextInt(bound);

        try{

            bitMap = imgDownload.execute(photos[temp_celeb]).get();

            celebPhoto.setImageBitmap(bitMap);

        }catch (Exception ex){

            ex.printStackTrace();
        }


        choice = rng.nextInt(4) + 1;

        generateButtons(temp_celeb);

    }

    private void generateButtons(int temp){

        Random rng = new Random();
        int fix;



        for(int i = 0; i < 4; i ++){

            if((int)buttons[i].getTag() == choice){

                buttons[i].setText(names[temp]);

            } else {

                fix = getRandomWithExclusion(rng, 0, bound, temp);
                buttons[i].setText(names[fix]);
            }



        }

    }

    private void checkSolution(View event){

        if((int)event.getTag() == choice){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nu i bine", Toast.LENGTH_SHORT).show();
        }

    }


    private void initUi(){

        buttons = new Button[4];

        button1 = findViewById(R.id.button1);
        button1.setTag(1);
        button1.setOnClickListener((View event) -> {
            checkSolution(event);
            generateGame();
        });

        button2 = findViewById(R.id.button2);
        button2.setTag(2);
        button2.setOnClickListener((View event) -> {
            checkSolution(event);
            generateGame();
        });

        button3 = findViewById(R.id.button3);
        button3.setTag(3);
        button3.setOnClickListener((View event) -> {
            checkSolution(event);
            generateGame();
        });

        button4 = findViewById(R.id.button4);
        button4.setTag(4);
        button4.setOnClickListener((View event) -> {
            checkSolution(event);
            generateGame();
        });


        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;

        celebPhoto = findViewById(R.id.celebPhoto);

        photos = new String[60];
        names = new String[60];
    }

    private void dataSplit(){

        int i = 0;

        mainContent = content.split("/imgs/hr_shadow.gif");
        celebs = mainContent[1].split("#56 <a href=\"/celebs/sandra_bullock\">");

        //for the photos
        Pattern p = Pattern.compile("img src=\"(.*?)\" alt");
        Matcher m = p.matcher(celebs[0]);

        while(m.find()){

            photos[i] = ("http://www.icelebz.com/" + m.group(1));

            i++;
        }

        //for the names
        p = Pattern.compile("alt=\"(.*?)\" height=\"95\"");
        m = p.matcher(celebs[0]);

        i = 0;

        while(m.find()){

            names[i] = (m.group(1));
            i++;
        }

    }

    public int getRandomWithExclusion(Random rnd, int start, int end, int... exclude) {
        int random = start + rnd.nextInt(end - start + 1 - exclude.length);

        for (int ex : exclude) {
            if (random < ex) {
                break;
            }

            random++;
        }
        return random;
    }

}
