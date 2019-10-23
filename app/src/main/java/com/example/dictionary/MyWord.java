package com.example.dictionary;

import java.io.Serializable;

public class MyWord implements Serializable {
    String Id, Word, Content;
    public MyWord(String id, String word, String content){
        this.Id=id;
        this.Word=word;
        this.Content=content;
    }
    public MyWord(){}
    public MyWord(MyWord w){
        this.Id=w.Id;
        this.Word=w.Word;
        this.Content=w.Content;
    }

    public String getId() {
        return Id;
    }

    public String getWord() {
        return Word;
    }

    public String getContent() {
        return Content;
    }
}