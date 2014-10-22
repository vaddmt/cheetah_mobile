package cheetah.cheetah_MGR;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cheetah.cheetah_LGC.GameResult;

public class FileManager {
    public boolean CreateFile(Context context, String filename) {
        try {
            File file = new File(context.getFilesDir(), filename);
            if(!file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException ex) {
            Messenger.showException("EXCEPTION:", ex);
            return false;
        }
        return true;
    }

    public void WriteToFile(Context context, String filename) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write("HELLO!".getBytes());
            outputStream.close();
        }
        catch (IOException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
    }

    public void WriteResultToFile(Context context, String filename, GameResult gm) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write(((Integer.toString(gm.getQuestionCounter())) + "\n").getBytes());
            outputStream.write(((Integer.toString(gm.getCorrectAnswerCounter())) + "\n").getBytes());
            outputStream.write(((Integer.toString(gm.getPointsTotal())) + "\n").getBytes());
            outputStream.close();
        }
        catch (IOException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
    }

    public String ReadFromFile(Context context, String filename) {
        FileInputStream   inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader    bufferedReader;
        String res = null;
        try {
            inputStream       = context.openFileInput(filename);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader    = new BufferedReader(inputStreamReader);
            res = bufferedReader.readLine();
            inputStream.close();
        }
        catch (FileNotFoundException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
        catch (IOException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
        return res;
    }

    public String[] ReadResultFromFile(Context context, String filename) {
        FileInputStream   inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader    bufferedReader;
        String[] res = new String[3];
        for(int i = 0; i < res.length; i++) res[i] = null;
        try {
            inputStream       = context.openFileInput(filename);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader    = new BufferedReader(inputStreamReader);
            for(int i = 0; i < res.length; i++) {
                res[i] = bufferedReader.readLine();
            }
            inputStream.close();
        }
        catch (FileNotFoundException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
        catch (IOException ex) {
            Messenger.showException("EXCEPTION:", ex);
        }
        return res;
    }
}
