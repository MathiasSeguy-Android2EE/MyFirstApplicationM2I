package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import androidx.core.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.R;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.dao.MessageDao;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.transverse.model.Message;
import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.view.main.MainActivity;

public class MySmsService extends Service {
    private static final String TAG = "MySmsService";
    private static final int UniqueNotificationId=19112014;
    private PendingIntent pdIntent;
    public MySmsService() {


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent2=new Intent(this, MainActivity.class );
        pdIntent=PendingIntent.getActivity(this,0,intent2,0);
        Log.e(TAG," Ok MySmsReceiver receive an SMS [action="+intent.getAction());
        //analyze SMS
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            //Retrieve the data store in the SMS
            Object[] pdus=(Object[])bundle.get("pdus");
                //Declare the associated SMS Messages
            SmsMessage[] smsMessages=new SmsMessage[pdus.length];
                //Rebuild your SMS Messages
            for(int i=0;i<pdus.length;i++){
                smsMessages[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
            }
                //Parse your SMS Message
            SmsMessage currentMessage;
            String body,from;
            long when;
            Message mess;
            for(int i=0;i<smsMessages.length;i++){
                currentMessage=smsMessages[i];
                body=currentMessage.getDisplayMessageBody();
                from=currentMessage.getDisplayOriginatingAddress();
                when=currentMessage.getTimestampMillis();
                //display notif
                displayNotif(from,body,when);
                //then save message
                mess=new Message(body,findName(from),from);
                MessageDao.getInstance().saveOrUpdateAsync(mess);
                Log.e(TAG,"new SMS found "+body+" by "+from);
            }
        }
        stopSelf();
        return Service.START_NOT_STICKY;
    }

    private String findName(String phoneNumber){
        ContentResolver resolver = getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME},null,null,null);
        if (cursor.moveToFirst()) {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            cursor.close();
            return name;
        } else {
            cursor.close();
            return "unknown";
        }
    }

    private void displayNotif(String from, String body,long when) {

        //display the notif
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true)
                .setContentIntent(pdIntent)
                .setContentText(body)
                .setContentTitle("New SMS de :" + from)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setLights(0x99FF0000, 0, 1000)//don't work
                .setNumber(41108)
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_notif)
                .setSubText("SubText")
                .setTicker("You received a new SMS from " + from)
                .setVibrate(new long[]{100, 200, 100, 200, 100}) //need permission
                .setWhen(when);

        NotificationManagerCompat notifManager = NotificationManagerCompat.from(this);
        notifManager.notify(UniqueNotificationId, builder.build());
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
