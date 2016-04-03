package com.dryv.onpoint;


import android.media.AudioManager;
import android.media.ToneGenerator;

/**
 * Created by anichindaniil on 31.03.16.
 *
 * Provides common utilities for OnPoint
 */

public class Utils {

    private final int TIME_DELTA = 250;
    public final int MIN_VOLUME = 100;
    public final int MAX_VOLUME = 200;

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