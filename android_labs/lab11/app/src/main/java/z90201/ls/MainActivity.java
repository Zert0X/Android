package z90201.ls;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private String pathRoot = "/proc/self";
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        tv.setMovementMethod(new ScrollingMovementMethod());
        try {
            FetchFileList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isSymlink(File file) throws IOException {
        File canon;
        if (file.getParent() == null) {
            canon = file;
        } else {
            File canonDir = file.getParentFile().getCanonicalFile();
            canon = new File(canonDir, file.getName());
        }
        return !canon.getCanonicalFile().equals(canon.getAbsoluteFile());
    }
    public static String getSymlink(File file) throws IOException {
        File canon;
        if (file.getParent() == null) {
            canon = file;
        } else {
            File canonDir = file.getParentFile().getCanonicalFile();
            canon = new File(canonDir, file.getName());
        }
        return String.valueOf(canon.getCanonicalFile());
    }
    private void FetchFileList() throws IOException {

        String appDirectoryName = getResources().getString(R.string.app_name);
        File directory = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        directory = new File(pathRoot);
        Log.d("Files", "Path: "+String.valueOf(directory));
        File[] fList = directory.listFiles();
        Log.d("Files","Files: "+ Arrays.toString(fList));
        //get all the files from a directory
        for (File file : Objects.requireNonNull(fList)) {
            String file_str="";
            file_str+=file.canRead()?"R":"-";
            file_str+=file.canWrite()?"W":"-";
            file_str+=(file.canExecute()?"E":"-")+"|";
            file_str+=(file.isDirectory()?"<dir>":String.valueOf(file.length()))+"|";
            file_str+=file.getName();
            file_str+=(isSymlink(file)?"->"+getSymlink(file):"")+"\n";
            tv.setText(tv.getText()+file_str);
        }


}
}