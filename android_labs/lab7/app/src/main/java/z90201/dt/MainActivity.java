package z90201.dt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final byte MENU_GROUP_TIME = 1;
        final byte MENU_ITEM_24H = 0;
        final byte MENU_ITEM_12H = 1;

    final byte MENU_GROUP_DATE = 2;
        final byte MENU_ITEM_DATE_ON = 0;
        final byte MENU_ITEM_DATE_OFF = 1;

    private byte OPTION_TIME_FORMAT = MENU_ITEM_12H;
    private byte OPTION_DATE_STATE = MENU_ITEM_DATE_ON;


    final Handler handler=new Handler();
    private boolean toasted = false;
    private String datetime_format;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.postDelayed(updateTime,0);
        handler.postDelayed(show_toast,0);

        ConstraintLayout parentLayout = findViewById(R.id.rootLayout);
        registerForContextMenu(parentLayout);
        updateSettings();
    }

    private void updateSettings() {
        String TIME_FORMAT="", DATE_STATE="";
        switch (OPTION_TIME_FORMAT){
            case MENU_ITEM_24H: TIME_FORMAT="HH:MM"; break;
            case MENU_ITEM_12H: TIME_FORMAT="hh:MM aa"; break;
        }
        switch (OPTION_DATE_STATE){
            case MENU_ITEM_DATE_ON: DATE_STATE=" dd/MM/YYYY"; break;
            case MENU_ITEM_DATE_OFF: DATE_STATE=""; break;
        }
        datetime_format = TIME_FORMAT+DATE_STATE;
        set_current_time();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem saved_item;
        SubMenu subMenuTime = menu.addSubMenu("Настройки времени");

            subMenuTime.add(MENU_GROUP_TIME, MENU_ITEM_24H, Menu.NONE, "24ч формат");
            subMenuTime.add(MENU_GROUP_TIME, MENU_ITEM_12H, Menu.NONE, "12ч формат");
            subMenuTime.setGroupCheckable(MENU_GROUP_TIME, true, true);

            saved_item = subMenuTime.getItem(OPTION_TIME_FORMAT);
            saved_item.setChecked(true);

        SubMenu subMenuDate = menu.addSubMenu("Настройки даты");
            subMenuDate.add(MENU_GROUP_DATE, MENU_ITEM_DATE_ON, Menu.NONE, "Вкл");
            subMenuDate.add(MENU_GROUP_DATE, MENU_ITEM_DATE_OFF, Menu.NONE, "Выкл");
            subMenuDate.setGroupCheckable(MENU_GROUP_DATE, true, true);

            saved_item = subMenuDate.getItem(OPTION_DATE_STATE);
            saved_item.setChecked(true);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getGroupId()!=Menu.NONE)
        switch (item.getGroupId()){
            case MENU_GROUP_TIME:
                OPTION_TIME_FORMAT= (byte) item.getItemId();
                break;
            case MENU_GROUP_DATE:
                OPTION_DATE_STATE = (byte) item.getItemId();
                break;
        }
        item.setChecked(true);
        updateSettings();
        return true;
    }
    final Runnable show_toast=new Runnable() {
        @Override
        public void run() {
        if(toasted)
            return;

        toasted = true;
        Toast.makeText(MainActivity.this, R.string.Toast,
                Toast.LENGTH_LONG).show();
        }
    };
    private void set_current_time() {
        DateFormat df = new SimpleDateFormat(datetime_format);
        TextView tv = findViewById(R.id.DateTime);
        tv.setText(df.format(Calendar.getInstance().getTime()));
    }
    final Runnable updateTime=new Runnable() {
        @Override
        public void run() {
            set_current_time();
            handler.postDelayed(this,1000);
        }
    };
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean("toasted", toasted);
        savedInstanceState.putByte("OPTION_TIME_FORMAT", OPTION_TIME_FORMAT);
        savedInstanceState.putByte("OPTION_DATE_STATE", OPTION_DATE_STATE);
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        toasted = savedInstanceState.getBoolean("toasted");
        OPTION_TIME_FORMAT  = savedInstanceState.getByte("OPTION_TIME_FORMAT");
        OPTION_DATE_STATE   = savedInstanceState.getByte("OPTION_DATE_STATE");
        updateSettings();
    }


}