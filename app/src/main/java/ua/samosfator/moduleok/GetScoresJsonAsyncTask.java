package ua.samosfator.moduleok;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import ua.samosfator.moduleok.student_bean.EmptyStudent;
import ua.samosfator.moduleok.student_bean.Student;
import ua.samosfator.moduleok.utils.Preferences;

public class GetScoresJsonAsyncTask extends AsyncTask<Void, Void, Student> {

    private final String apiUrl = "https://moduleok.appspot.com/api/getScores?login=" +
            Preferences.read("login", "") + "&password=" +
            Preferences.read("password", "");
    private final String customApiUrl = "http://moduleok.hol.es/mock.json";

    @Override
    protected Student doInBackground(Void... params) {
        try {
            InputStream inputStream = new URL(apiUrl).openConnection().getInputStream();
            String readJson = fromStream(inputStream);

            Preferences.save("json", readJson);

            Student student = new Gson().fromJson(readJson, new TypeToken<Student>() {
            }.getType());
            Log.d("ASYNC_TASK", "student == null: " + String.valueOf(student == null));
            return student;
        } catch (IOException e) {
            e.printStackTrace();
            return new EmptyStudent();
        }
    }

    public static String fromStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }
}
