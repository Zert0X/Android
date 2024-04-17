package z90201.vs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

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
        final Animation inAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_in_left);
        final Animation outAnimation = AnimationUtils.loadAnimation(this,
                android.R.anim.slide_out_right);

        ViewAnimator viewAnimator = findViewById(R.id.AvatarAnimator);

        viewAnimator.setInAnimation(inAnimation);
        viewAnimator.setOutAnimation(outAnimation);

        viewAnimator.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                viewAnimator.showNext();
            }
        });

        TextView email_tv = findViewById(R.id.value_email);
        email_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+email_tv.getText()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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