package com.kristenmabry.ledmatrix.classes;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class FileUtils {

    public static boolean writeToFile(Context context, String filename, String data) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(data.getBytes());
        } catch (Exception e) {
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String[] readFiles(Context context) {
        String[] files = context.getFilesDir().list();
        StringBuffer[] fileContents = new StringBuffer[files.length];
        String[] fileStrings = new String[files.length];
        for (int i = 0; i < files.length; ++i) {
            FileInputStream fis = null;
            try {
                fis = context.openFileInput(files[i]);
                int read;
                fileContents[i] = new StringBuffer();
                while((read = fis.read()) != -1) {
                    fileContents[i].append((char) read);
                }
                fileStrings[i] = fileContents[i].toString();
            } catch (Exception e) {
                return null;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return fileStrings;
    }

    public static boolean deleteFile(Context context, String filename) {
        File dir = context.getFilesDir();
        File fileToRemove = new File(dir, filename);
        return fileToRemove.delete();
    }
}
