package z90201.direct;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launch_btn = findViewById(R.id.launch_btn);
        launch_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        RadioGroup RG = findViewById(R.id.radioGroup);
        int checkedID = RG.getCheckedRadioButtonId();
        if(checkedID==-1){
            Toast.makeText(getApplicationContext(), "Ничего не выбрано", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity2.class);
        int choosenID, in_animation= R.anim.slide_in_left, out_animation = R.anim.slide_out_right;

        if(checkedID==R.id.radioButton1){
            choosenID=1;
            in_animation=R.anim.slide_in_right;
            out_animation=R.anim.slide_out_left;
            new RB_Extras(intent, choosenID, R.anim.slide_in_left, R.anim.slide_out_right);
        }
        else if(checkedID==R.id.radioButton2){
            choosenID=2;
            in_animation=R.anim.slide_in_left;
            out_animation=R.anim.slide_out_right;
            new RB_Extras(intent, choosenID, R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else if(checkedID==R.id.radioButton3){
            choosenID=3;
            in_animation=R.anim.slide_in_bottom;
            out_animation=R.anim.slide_out_top;
            new RB_Extras(intent, choosenID, R.anim.slide_in_top, R.anim.slide_out_bottom);
        }
        else if(checkedID==R.id.radioButton4){
            choosenID=4;
            in_animation=R.anim.slide_in_top;
            out_animation=R.anim.slide_out_bottom;
            new RB_Extras(intent, choosenID, R.anim.slide_in_bottom, R.anim.slide_out_top);
        }
        startActivity(intent);
        this.overridePendingTransition(in_animation, out_animation);
    }
}