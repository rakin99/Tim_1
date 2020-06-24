package com.example.mojprojekat.sync;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.mojprojekat.R;
import com.example.mojprojekat.aktivnosti.EmailsActivity;
import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.Data;

public class SyncReceiver extends BroadcastReceiver {

    private static int notificationID = 1;
    private static String channelId = "My_Chan_Id";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(EmailsActivity.SYNC_DATA)) {
            for (Message m : Data.newMessages
            ) {
                System.out.println("Tu sam....<<<<<<<<<-------------!!!!!!!");
                Intent intent1 = new Intent(context, EmailsActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent1, 0);
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "Moje ime";// The user-visible name of the channel.
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);
                    Notification notification =
                            new Notification.Builder(context)
                                    .setSmallIcon(R.drawable.email)
                                    .setContentTitle("Dobili ste novu poruku!")
                                    .setContentText(Data.newMessages.size() + " " + vratiTekst(String.valueOf(Data.newMessages.size())))
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .setChannelId(channelId).build();
                    System.out.println("Sistem je veci od O<<<<----------------------------\n\n");
                    mNotificationManager.createNotificationChannel(mChannel);
                    mNotificationManager.notify(notificationID, notification);
                } else {
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);
                    mBuilder.setContentTitle("Dobili ste novu poruku!");
                    mBuilder.setContentText(Data.newMessages.size() + " " + vratiTekst(String.valueOf(Data.newMessages.size())));
                    mBuilder.setSmallIcon(R.drawable.email);
                    mBuilder.setChannelId(channelId);
                    mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    mBuilder.setContentIntent(pendingIntent);
                    mBuilder.setAutoCancel(true);
                    mNotificationManager.notify(notificationID, mBuilder.build());
                }
            }
        }

    }

    private static String vratiTekst(String br){
        String poruka="";
        int duzina=br.length();
        String poslednjiBr=br.substring(duzina-1);
        if(Integer.parseInt(br)>9 && Integer.parseInt(br)<101){
            poruka = "nepročitanih poruka.";
        }
        else if(poslednjiBr.equals("1")){
            poruka = "nepročitana poruka.";
        }else if(poslednjiBr.equals("2") || poslednjiBr.equals("3") || poslednjiBr.equals("4")){
            poruka = "nepročitane poruke.";
        }else if(poslednjiBr.equals("5") || poslednjiBr.equals("6") || poslednjiBr.equals("7") || poslednjiBr.equals("8") || poslednjiBr.equals("9")){
            poruka = "nepročitanih poruka.";
        }
        return poruka;
    }
}
