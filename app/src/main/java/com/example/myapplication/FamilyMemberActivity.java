package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyMemberActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_family_member);

        final ArrayList<word> Family=new ArrayList<word>();
        Family.add(new word("father","әpә", R.drawable.family_father,R.raw.family_father));
        Family.add(new word("mother","әṭa", R.drawable.family_mother,R.raw.family_mother));
        Family.add(new word("son","angsi", R.drawable.family_son,R.raw.family_son));
        Family.add(new word("daughter","tune", R.drawable.family_daughter,R.raw.family_daughter));
        Family.add(new word("older brother","taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        Family.add(new word("younger brother","chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        Family.add(new word("older sister","teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        Family.add(new word("younger sister","kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        Family.add(new word("grandmother","ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        Family.add(new word("grandfather","paapa", R.drawable.family_grandfather,R.raw.family_grandfather));

        FamilyAdapter adapter = new FamilyAdapter(this,Family,R.color.category_family);
        ListView listView = findViewById(R.id.list1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                word Word = Family.get(i);

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mAudioManager.registerMediaButtonEventReciever(RemoteControlReceiver);

                    mMediaPlayer = MediaPlayer.create(FamilyMemberActivity.this, R.raw.number_one);
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListner); }

                mMediaPlayer = MediaPlayer.create(FamilyMemberActivity.this, Word.getAudio() );
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