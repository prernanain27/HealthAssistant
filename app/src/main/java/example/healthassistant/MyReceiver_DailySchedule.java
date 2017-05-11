package example.healthassistant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class MyReceiver_DailySchedule extends BroadcastReceiver {
    public MyReceiver_DailySchedule() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent service1 = new Intent(context, Daily_ScheduleNotification.class);
        service1.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        context.startService(service1);
    }
}
