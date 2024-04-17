package z90201.direct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.zip.Deflater;

public class MainActivity2 extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv = findViewById(R.id.tv);
        RB_Extras rbExtras = new RB_Extras(this);
        tv.setText(String.valueOf(rbExtras.get_value()));

    }

    @Override
    public void finish() {
        super.finish();
        RB_Extras rbExtras = new RB_Extras(this);
        this.overridePendingTransition(rbExtras.get_in_animation(), rbExtras.get_out_animation());

    }
}