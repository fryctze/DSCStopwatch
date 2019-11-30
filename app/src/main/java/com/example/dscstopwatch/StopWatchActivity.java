package com.example.dscstopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

public class StopWatchActivity extends AppCompatActivity {

    Button btnStart, btnStop;
    ImageView icanchor;
    Chronometer timerHere;
    Animation roundingalone;

    private static final String CHANNEL_ID = "simplified_coding";
    private static final String CHANNEL_NAME = "Simplified Coding";
    private static final String CHANNEL_DESC = "Angelika Justine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        btnStart = findViewById(R.id.btnstart);
        btnStop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MMedium.ttf");

        btnStop.setTypeface(MMedium);
        btnStart.setTypeface(MMedium);
        timerHere.setTypeface(MMedium);

        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);

        btnStop.setAlpha(0);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icanchor.startAnimation(roundingalone);
                btnStop.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnStart.animate().alpha(0).setDuration(300).start();
                timerHere.setBase(SystemClock.elapsedRealtime());
                timerHere.start();
                displayNotification();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icanchor.clearAnimation();
                btnStart.animate().alpha(1).setDuration(300).start();
                btnStop.animate().alpha(0).translationY(-80).setDuration(300).start();
                timerHere.stop();
            }
        });


    }

    private void displayNotification(){
        Intent intent1 = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,
                CHANNEL_ID)
                .setSmallIcon(R.drawable.icget)
                .setContentIntent(pendingIntent)
                .setContentTitle("Stopwatch has started")
                .setContentText("Daripada workout, mending makan")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, nBuilder.build());
    }
}
