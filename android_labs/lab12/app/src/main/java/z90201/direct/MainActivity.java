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
        int checkedId = RG.getCheckedRadioButtonId();
        Log.d("SHIT", String.valueOf(RG.getCheckedRadioButtonId()));
        int checkedRB = -1;
        if(checkedId==-1){
            Toast.makeText(getApplicationContext(), "Ничего не выбрано", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity2.class);
        if(checkedId==R.id.radioButton1)         new RB_Extras(intent, 1);
        else if(checkedId==R.id.radioButton2)    new RB_Extras(intent, 2);
        else if(checkedId==R.id.radioButton3)    new RB_Extras(intent, 3);
        else if(checkedId==R.id.radioButton4)    new RB_Extras(intent, 4);
        startActivity(intent);
    }
}