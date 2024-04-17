package z90201.direct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RB_Extras {
    private String _key = "z90201.direct.extra.value";
    private int _value = -1;
    RB_Extras(Intent intent, int value){
        intent.putExtra(_key,value);
    }
    RB_Extras(Activity activity){
        Bundle extras = activity.getIntent().getExtras();
        _value = extras!=null?extras.getInt(_key):-1;
    }
    public int get_value(){
        return _value;
    }
}
