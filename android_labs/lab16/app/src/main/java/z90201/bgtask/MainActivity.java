package z90201.bgtask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int STATUS_FINISH = 0;
    public final static int STATUS_RUNNING = 1;
    private final static int BGTASK_CODE = 1;
    public final static String PARAM_PINTENT = "pendingIntent";
    public final static String PARAM_RESULT = "result";

    int percentage = 0;

    TextView percentage_tv;
    Button start_btn;
    ProgressBar PB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PB1 = findViewById(R.id.progressBar1);
        percentage_tv = findViewById(R.id.percentage_tv);
        start_btn = findViewById(R.id.start_btn);
        start_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        percentage=0;
        update_percentage();
        start_btn.setEnabled(false);
        PB1.setVisibility(View.VISIBLE);
        PendingIntent pi;
        Intent intent;
        pi = createPendingResult(BGTASK_CODE, new Intent(), 0);
        intent = new Intent(this, BGService.class).putExtra(PARAM_PINTENT, pi);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Ловим сообщения о старте задач
        if (resultCode == STATUS_FINISH) {
            PB1.setVisibility(View.INVISIBLE);
            start_btn.setEnabled(true);
            percentage = 0;
        }
        percentage = data.getIntExtra(PARAM_RESULT, 0);;
        update_percentage();
    }


        private void update_percentage(){
        percentage_tv.setText(percentage+"%");
        PB1.setProgress(percentage);

    }



    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putInt("PERCENTAGE",percentage);
        saveInstanceState.putInt("PB_VISIBILITY",PB1.getVisibility());
        saveInstanceState.putBoolean("BTN_STATUS",start_btn.isEnabled());
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        percentage= saveInstanceState.getInt("PERCENTAGE");
        start_btn.setEnabled(saveInstanceState.getBoolean("BTN_STATUS"));
        PB1.setVisibility(saveInstanceState.getInt("PB_VISIBILITY"));
        update_percentage();
    }
}