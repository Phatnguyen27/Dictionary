package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class WordActivity extends AppCompatActivity {
    FloatingActionButton read_fab;
    ImageButton addFavor_Button;
    TextToSpeech textToSpeech;
    Toolbar toolbar;
    private MyWord myWord;
    private static final String STYLE ="<style>\n" +
            "            .title {\n" +
            "                color: green;\n" +
            "                font-size: 20px;\n" +
            "            }\n" +
            "            .type {\n" +
            "                color: red;\n" +
            "                font-size: 20px;\n" +
            "            }\n" +
            "            span {\n" +
            "                font-size: 18px;\n" +
            "            }\n" +
            "            li {\n" +
            "                font-size: 18px;\n" +
            "            }\n" +
            "     </style>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addFavor_Button = findViewById(R.id.favor_btn);
        Intent intent = getIntent();
        this.myWord = (MyWord) intent.getSerializableExtra("word");
        final int number = intent.getIntExtra("favourite",0);
        addFavor_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number == 0) {
                    addToFavouriteList();
                } else {
                    removeFromFavouriteList();
                }
            }
        });
        ((TextView) findViewById(R.id.title)).setText(myWord.getWord());
        ((WebView) findViewById(R.id.content)).loadData(myWord.getContent()+STYLE,
                "text/html", "UTF-8");
        read_fab = (FloatingActionButton) findViewById(R.id.read_fab);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        read_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ((TextView) findViewById(R.id.title)).getText().toString();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    public void removeFromFavouriteList() {
        addFavor_Button.setImageResource(R.drawable.ic_favor);
    }

    public void addToFavouriteList() {
        DatabaseAccess.instance.addToFavoriteDict(Integer.parseInt(this.myWord.getId()));
        addFavor_Button.setImageResource(R.drawable.ic_favor_clicked);
    }

//
//    @Override
//    public boolean onNavigateUp() {
//        finish();
//        return true;
//    }
}
