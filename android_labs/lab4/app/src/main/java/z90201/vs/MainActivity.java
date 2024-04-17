package z90201.vs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_countdown_timer(findViewById(R.id.value_next_birthday));

    }
    private void start_countdown_timer(final TextView tv)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        int year = Year.now().getValue();
        LocalDate birthday = LocalDate.of(year, Month.DECEMBER, 20);
        LocalDate today = LocalDate.now();
        long d = Duration.between(today.atStartOfDay(), birthday.atStartOfDay()).toDays();
        if(d==0){
            tv.setText("HAPPY BIRTHDAY!");
            return;
        }
        if(d<0){
            birthday = LocalDate.of(year+1, Month.DECEMBER, 20);
        }


        String endTime = birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        long milliseconds=0;

        final CountDownTimer mCountDownTimer;

        Date endDate;
        try {
            endDate = formatter.parse(endTime);
            milliseconds = endDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final long[] startTime = {System.currentTimeMillis()};


        mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                startTime[0] = startTime[0] -1;
                Long serverUptimeSeconds =
                        (millisUntilFinished - startTime[0]) / 1000;

                String res = String.format("%d", serverUptimeSeconds / 86400)+"D ";
                res += String.format("%d", (serverUptimeSeconds % 86400) / 3600)+"H ";
                res += String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60)+"M ";
                res += String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60)+"S";
                tv.setText(res);

            }

            @Override
            public void onFinish() {

            }
        }.start();


    }
}