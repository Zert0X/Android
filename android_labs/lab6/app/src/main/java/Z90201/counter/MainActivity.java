package Z90201.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_plus = findViewById(R.id.btn_plus);
        Button btn_minus = findViewById(R.id.btn_minus);

        btn_plus.setOnClickListener(this::onClick);
        btn_minus.setOnClickListener(this::onClick);
    }
    @Override
    public void onClick(View view)
    {
        TextView tv_counter = findViewById(R.id.tv_counter);
        switch (getResources().getResourceEntryName(view.getId()))
        {
            case "btn_plus":
                counter++;
                tv_counter.setText(String.format("%d",counter));
                break;
                
            case "btn_minus":
                counter--;
                tv_counter.setText(String.format("%d",counter));
                break;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putInt("counter", counter);
        super.onSaveInstanceState(savedInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
        TextView tv_counter = findViewById(R.id.tv_counter);
        tv_counter.setText(String.format("%d",counter));
    }
}