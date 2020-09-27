package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    private MediaPlayer.OnCompletionListener mCompletionListner = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrases);

        final ArrayList<word> Phrases=new ArrayList<word>();
        Phrases.add(new word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        Phrases.add(new word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        Phrases.add(new word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        Phrases.add(new word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        Phrases.add(new word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        Phrases.add(new word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        Phrases.add(new word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        Phrases.add(new word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        Phrases.add(new word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        Phrases.add(new word("Come here.","әnni'nem",R.raw.phrase_come_here));

        PhrasesAdapter adapter = new PhrasesAdapter(this,Phrases,R.color.category_phrases);
        ListView listView = findViewById(R.id.list2);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word Word = Phrases.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mAudioManager.registerMediaButtonEventReciever(RemoteControlReceiver);

                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, R.raw.number_one);
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListner); }

                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, Word.getAudio() );
                mMediaPlayer.start();
            }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer!=null){
            mMediaPlayer.release();
            mMediaPlayer=null;
            mAudioManager.abandonAudioFocusRequest(mOnAudioFocusChangeListener);
        }
    }
}