package z90201.direct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class RB_Extras {
    final private String _value_key = "z90201.direct.extra._value";
    final private String _in_animation_key = "z90201.direct.extra._in_animation";
    final private String _out_animation_key = "z90201.direct.extra._out_animation";

    private int _value = -1;
    private int _in_animation = -1;
    private int _out_animation = -1;
    RB_Extras(Intent intent, int value, int in_animation, int out_animation){

        intent.putExtra(_value_key, value);
        intent.putExtra(_in_animation_key, in_animation);
        intent.putExtra(_out_animation_key, out_animation);
    }
    RB_Extras(Activity activity){
        Bundle extras = activity.getIntent().getExtras();
        _value = extras!=null?extras.getInt(_value_key):-1;
        _in_animation = extras!=null?extras.getInt(_in_animation_key):-1;
        _out_animation = extras!=null?extras.getInt(_out_animation_key):-1;
    }
    public int get_value(){
        return _value;
    }

    public int get_in_animation() {
        return _in_animation;
    }
    public int get_out_animation() {
        return _out_animation;
    }
}
