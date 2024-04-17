package z90201.dt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
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
    final int MENU_GROUP_TIME = 1;
        final int MENU_ITEM_24H = 0;
        final int MENU_ITEM_12H = 1;

    final int MENU_GROUP_DATE = 2;
        final int MENU_ITEM_ON = 0;
        final int MENU_ITEM_OFF = 1;

    final int MENU_GROUP_TOAST = 3;

    private int OPTION_TIME_FORMAT = MENU_ITEM_12H;
    final String TIME_FORMAT_PREF = "OPTION_TIME_FORMAT";
    private int OPTION_DATE_STATE = MENU_ITEM_ON;
    final String DATE_STATE_PREF = "OPTION_DATE_STATE";
    private int OPTION_TOAST = MENU_ITEM_ON;
    final String TOAST_PREF = "OPTION_TOAST";

    SharedPreferences sPref;
    final Handler handler=new Handler();
    private boolean toasted = false;
    private String datetime_format;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPreferences();

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
            case MENU_ITEM_ON: DATE_STATE=" dd/MM/YYYY"; break;
            case MENU_ITEM_OFF: DATE_STATE=""; break;
        }
        datetime_format = TIME_FORMAT+DATE_STATE;
        set_current_time();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem saved_item;
        SubMenu subMenuTime = menu.addSubMenu(R.string.subMenuTime);

            subMenuTime.add(MENU_GROUP_TIME, MENU_ITEM_24H, Menu.NONE, R.string.MENU_ITEM_24H);
            subMenuTime.add(MENU_GROUP_TIME, MENU_ITEM_12H, Menu.NONE, R.string.MENU_ITEM_12H);
            subMenuTime.setGroupCheckable(MENU_GROUP_TIME, true, true);

            saved_item = subMenuTime.getItem(OPTION_TIME_FORMAT);
            saved_item.setChecked(true);

        SubMenu subMenuDate = menu.addSubMenu(R.string.subMenuDate);
            subMenuDate.add(MENU_GROUP_DATE, MENU_ITEM_ON, Menu.NONE, R.string.MENU_ITEM_ON);
            subMenuDate.add(MENU_GROUP_DATE, MENU_ITEM_OFF, Menu.NONE, R.string.MENU_ITEM_OFF);
            subMenuDate.setGroupCheckable(MENU_GROUP_DATE, true, true);

            saved_item = subMenuDate.getItem(OPTION_DATE_STATE);
            saved_item.setChecked(true);

        SubMenu subMenuToast = menu.addSubMenu(R.string.subMenuToast);
            subMenuToast.add(MENU_GROUP_TOAST, MENU_ITEM_ON, Menu.NONE, R.string.MENU_ITEM_ON);
            subMenuToast.add(MENU_GROUP_TOAST, MENU_ITEM_OFF, Menu.NONE, R.string.MENU_ITEM_OFF);
            subMenuToast.setGroupCheckable(MENU_GROUP_TOAST, true, true);

            saved_item = subMenuToast.getItem(OPTION_TOAST);
            saved_item.setChecked(true);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        if(item.getGroupId()!=Menu.NONE)
            switch (item.getGroupId()){
                case MENU_GROUP_TIME:
                    OPTION_TIME_FORMAT= item.getItemId();
                    break;
                case MENU_GROUP_DATE:
                    OPTION_DATE_STATE = item.getItemId();
                    break;
                case MENU_GROUP_TOAST:
                    OPTION_TOAST = item.getItemId();
                    break;
            }
        item.setChecked(true);
        updateSettings();
        return true;
    }
    final Runnable show_toast=new Runnable() {
        @Override
        public void run() {
            if(OPTION_TOAST==MENU_ITEM_OFF){
                toasted=true;
                return;
            }
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

    //Remembering Prefs
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean("toasted", toasted);
        super.onSaveInstanceState(savedInstanceState);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        toasted = savedInstanceState.getBoolean("toasted");
        updateSettings();
    }

    //Saving Prefs
    void savePreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor sPref_editor = sPref.edit();
        sPref_editor.putInt(TIME_FORMAT_PREF, OPTION_TIME_FORMAT);
        sPref_editor.putInt(DATE_STATE_PREF, OPTION_DATE_STATE);
        sPref_editor.putInt(TOAST_PREF, OPTION_TOAST);
        sPref_editor.apply();
    }

    void loadPreferences() {
        sPref = getPreferences(MODE_PRIVATE);
        OPTION_TIME_FORMAT = sPref.getInt(TIME_FORMAT_PREF, MENU_ITEM_12H);
        OPTION_DATE_STATE= sPref.getInt(DATE_STATE_PREF, MENU_ITEM_ON);
        OPTION_TOAST = sPref.getInt(TOAST_PREF, MENU_ITEM_ON);
        updateSettings();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }
    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }


}