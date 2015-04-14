package ua.samosfator.moduleok;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Scanner;

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
            Reader inputStreamReader = new InputStreamReader(new URL(apiUrl).openStream(), "UTF-8");
            Scanner scanner = new Scanner(inputStreamReader).useDelimiter("\\A");
            String readJson = "";
            if (scanner.hasNext()) {
                readJson = scanner.next();
                Preferences.save("json", readJson);
            }
            Student student = new Gson().fromJson(readJson, new TypeToken<Student>() { }.getType());
            Log.d("ASYNC_TASK", "student == null: " + String.valueOf(student == null));
            return student;
        } catch (IOException e) {
            e.printStackTrace();
            return new EmptyStudent();
        }
    }
}
