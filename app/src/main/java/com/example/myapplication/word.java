package com.example.myapplication;

public class word {

    private String mDefaultTrans;
    private String mMiwokTrans;
    private int mImage;
    private int mAudio;
    public word(String defaultTrans,String miwokTrans,int image,int audio){
        mDefaultTrans=defaultTrans;
        mMiwokTrans=miwokTrans;
        mImage=image;
        mAudio=audio;
    }
    public word(String defaultTrans,String miwokTrans,int audio){
        mDefaultTrans=defaultTrans;
        mMiwokTrans=miwokTrans;
        mAudio=audio;
    }
    public String getDefaultTranslation(){
        return mDefaultTrans;
    }
    public String getMiwokTranslation(){
        return mMiwokTrans;
    }
    public int getImage(){ return mImage;}
    public int getAudio(){ return mAudio;}
}
