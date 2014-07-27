package com.example.smsfilter;

import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsFilterActivity extends Activity {

	private TextView msgtext; //= (TextView) findViewById(R.id.autoCompleteTextView1);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferenceManager.setDefaultValues(this, R.xml.appprefrences, false);
		setContentView(R.layout.activity_sms_filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sms_filter, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem mit)
	{
		switch(mit.getItemId())
		{
		case	R.id.action_settings:
			startActivity(new Intent(this,AppSettingsActivity.class));
			return true;
		case	R.id.action_about:
			return true;
		case	R.id.action_exit:
			System.exit(0);
			return true;
		case	R.id.action_mail:
			//send the mail
			String[] to = {"weimenglee9learn2develop.net" , "larryakah@gmail.com" }; String[] cc = {"course9learn2develop.net" };
			sendEmail(to, cc, "Hi-Tech software" , "Welcome to Larrytech Corp" );
			return true;
		case R.id.action_sms:
			//send the sms
			return true;
			
		default:
			return  super.onOptionsItemSelected(mit);
		}
		
	}
	
	public void readSetting(View but)
	{
		SharedPreferences appSp = PreferenceManager.getDefaultSharedPreferences(this);
		
		try{
			String name = appSp.getString(MessageReceiver.contact, "");
			boolean val = appSp.getBoolean(MessageReceiver.kmsg, false);
			
			//appSp.getInt(arg0, arg1);
		Toast.makeText(this, "Contact: "+name, Toast.LENGTH_LONG).show();
		Toast.makeText(this, "Keep Message: "+val, Toast.LENGTH_SHORT).show();
		
		}
		catch(Exception ex)
		{
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	msgtext = (TextView) findViewById(R.id.autoCompleteTextView1);
	//notifyUser();
}
	
	public void notifyUser()
	{
		final NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.sms_filter)
                .setContentTitle("New Messsage")
                .setContentText("Mesasge is being sent").setAutoCancel(true);
       
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, SmsFilterActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SmsFilterActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        final NotificationManager mNotificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());

	}
	
	public void sendSmsMessage(View v)
	{
		//send the message typed by the user and get feed back from the message service provider
		String msg = msgtext.getText().toString();
		SmsManager smsObject = SmsManager.getDefault();
		try{
			//smsObject.sendTextMessage("+23797950531", null, msg, null, null);
		//notifyUser();
	//Intent smsit = new Intent(Intent.ACTION_SEND);
			String[] to = {"weimenglee9learn2develop.net" , "larryakah@gmail.com" }; String[] cc = {"course9learn2develop.net" };
			sendEmail(to, cc, "Hi-Tech software" , msg );
		//	smsit.setData(Uri.parse("smsto: +23797950531"));
			//smsit.setType(");
	//		startActivity(smsit);
		}
		catch(Exception ex)
		{
			Toast.makeText(getApplicationContext(), "Message not sent. Please try again", Toast.LENGTH_LONG).show();
		}
	}

	public void sendMails(View v)
	{
		/*
		 * Send mail to contact on the device
		 * create addresses to sendmail to as an array of addresses
		   create carbon copy addresses to send mails to. Created as string array called cc
		 */
		
		String[] to = {"weimenglee9learn2develop.net" , "larryakah@gmail.com" }; String[] cc = {"course9learn2develop.net" };
		
		sendEmail(to, cc, "Hi-Tech Softwares" , "Welcome To LarryTech Corp" );
		
	}
	
	public void sendEmails(String[] tos, String[] cc, String sbj, String msgbody)
	{
		//using a default email sending client
		Intent it = new Intent(Intent.ACTION_SEND);
		it.setType("text/plain");
		it.putExtra(Intent.EXTRA_EMAIL, tos); //receipients
		it.putExtra(Intent.EXTRA_TEXT, msgbody); //body
		try{
			startActivity(Intent.createChooser(it, "Sending mails ..."));
		}
		catch(android.content.ActivityNotFoundException aex)
		{
			Toast.makeText(getApplicationContext(), "No email clients Found or installed", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void sendEmail(String[] emailAddresses, String[] carbonCopies, String subject, String message)
	{
		//using intents  method B
	Intent emailIntent = new Intent(Intent.ACTION_SEND); emailIntent.setData(Uri.parse("mailto:"));
	String[] to = emailAddresses; String[] cc = carbonCopies;
	emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
	emailIntent.putExtra(Intent.EXTRA_CC, cc);
	emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject); 
	emailIntent.putExtra(Intent.EXTRA_TEXT, message);
	emailIntent.setType("message/rfc822"); 
	startActivity(Intent.createChooser(emailIntent, "Email"));

	}
	/*sms response notifications
	 * Intent sentit = new Intent("sent_sms_action");
	 * PendingIntent sentpi = PendingIntent.getBroadcast(getApplicationContext(), 0, sentit, 0);
	 * Intent responseit = new Intent("deliver_sms_action");
	 * PendingIntent response = PengingIntent.getBroadcast(getApplicationContext(), 0, responseit, 0);
	 * 
	 * startActivity(sentit);
	 * 
	 *  //registering the receivers
	 *  registerReceiver(new BroadcastReceiver(){
	 *  @Override
	 *  public void onReceive(Context ctx, Intent it)
	 *  {
	 *  	switch(getResultCode())
	 *  {
	 *  	case Activity.RESULT_OK:
	 *  		//message sent with success
	 *  		break;
	 *  }
	 *  }
	 *  }, new IntentFilter("sent_sms_action"));
	 *  registerReceiver(new BroadcastReceiver(){
	 *  	@Override
	 *      public void onReceive(Context ctx, Intent it)
	 *      {
	 *      	//message delivered successfully
	 *      }
	 *  }, new IntentFilter("deliver_sms_action"));
	 *  
	 *  // now we use the sms manager API to put everything together
	 *  	SmsManger mysms = SmsManager.getDefault();
	 *	    mysms.sendTextMessage("receipient", null, "Message", sentpi, responseit);
	 */
}
	
