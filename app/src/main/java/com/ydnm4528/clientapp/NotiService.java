package com.ydnm4528.clientapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotiService extends FirebaseMessagingService {

    static int notiId = 1;
    public NotiService() {


    }

    String title;
    String body;
    String imageURL;



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size()!= 0)
        {
            title = remoteMessage.getData().get("title");
            body = remoteMessage.getData().get("body");

                imageURL = remoteMessage.getData().get("image");


               new sendnoti().execute(title, body, imageURL);


        }
        if (remoteMessage.getNotification()!= null)
        {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();

                imageURL = remoteMessage.getNotification().getImageUrl().toString();

               new sendnoti().execute(title, body, imageURL);

        }
    }

    private class sendnoti extends AsyncTask<String, Void, Bitmap>
    {


        @Override
        protected Bitmap doInBackground(String... strings) {

            title = strings[0];
            body = strings[1];
            imageURL = strings[2];

            try {
                URL imagehttpurl = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection) imagehttpurl.openConnection();
                connection.setDoInput(true);
                InputStream inputStream = connection.getInputStream();
                Bitmap myimage = BitmapFactory.decodeStream(inputStream);
                return myimage;

            }
            catch (Exception ex)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap image) {
           Intent intent = new Intent(getApplicationContext(), MainActivity.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_ONE_SHOT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                String channelid = getString(R.string.app_name);
                String channelname = getString(R.string.app_name);
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = new NotificationChannel(channelid, channelname, importance);



                manager.createNotificationChannel(channel);

                NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext(), channelid);
                noti.setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_noti)
                        .setAutoCancel(true)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setColor(Color.MAGENTA)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                manager.notify(notiId++, noti.build());
            }

            else
            {
                NotificationCompat.Builder noti = new NotificationCompat.Builder(getApplicationContext());
                noti.setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.ic_noti)
                        .setAutoCancel(true)
                        .setLargeIcon(image)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                        .setColor(Color.MAGENTA)
                        .setContentIntent(pendingIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
                manager.notify(notiId++, noti.build());

            }
        }

    }
}
