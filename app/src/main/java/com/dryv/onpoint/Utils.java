package com.dryv.onpoint;


import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by anichindaniil on 31.03.16.
 *
 * Provides common utilities for OnPoint
 */

public class Utils {

    private final int TIME_DELTA = 250;
    public final int MIN_VOLUME = 0;
    public final int MAX_VOLUME = 100;
    private Timer t;
    private int delay = 1000;
    private TimerTask task;
    private boolean beeping;
    ToneGenerator toneGen;
    Utils(){
        toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }
    public void setFreequency(int f){
        delay = 100-f;
    }
    public void startBeeping(){
        t = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Log.d("APP", "generating tone");
                toneGen.startTone(ToneGenerator.TONE_DTMF_7, 100);
                Log.d("APP", "after start tone");
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("APP", "after waiting");
            }
        };
        t.schedule(task, 0, delay*2);
    }
    public void stopBeeping(){
        t.cancel();
        t.purge();
    }
    public void op_beep(double volume_level)
    {
        int volume = MIN_VOLUME + (int)(volume_level * (MAX_VOLUME - MIN_VOLUME));
        op_beep(volume);
    }

    public void op_beep(int volume)
    {
        int rate = 1;
        op_beep(volume, rate);
    }

    public void op_beep(int volume, int rate)
    {
        ToneGenerator tone1;
        tone1 = new ToneGenerator(AudioManager.STREAM_MUSIC, volume);
        tone1.startTone(ToneGenerator.TONE_DTMF_1);

        try {
            Thread.sleep(TIME_DELTA);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tone1.stopTone();

        try {
            Thread.sleep(TIME_DELTA * rate);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}