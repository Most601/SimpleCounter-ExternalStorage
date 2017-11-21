package com.example.most601.simplecounter_externalstorage;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    public String idan;
    private Context m_context;

    public FileManager(Context context) {
        m_context = context;
    }

    /** Internal Storage Methods **/



    /** External Storage Methods **/


    public void writeInternalFile(String fileName, String content, boolean append) throws IOException {
        boolean a = isExternalStorageWritable();
        if (a){
            Log.e("11111111111111 - ", "11111111111111");
        }
        else{
            Log.e("000000000 - ", "000000000000");
        }

        writeInternalFile(fileName, content.getBytes(), append);
    }
    public void writeInternalFile(String fileName, byte[] content, boolean append) throws IOException {
        // Context.MODE_PRIVATE = 0, therefore, we don't need to explicitly specify it.
        File outputStream1 = getPublicPicturesDirectory("A");
        idan = outputStream1.getPath()+"/"+fileName;
        FileOutputStream outputStream = new FileOutputStream(idan, false );


        outputStream.write(content);
        outputStream.close();
    }

    public String readInternalFile(String fileName) throws IOException {
        String content = "";
        boolean a = isExternalStorageReadable();
        if (a){
            Log.e("1212121212 - ", "1212121212");
        }
        else{
            Log.e("00001111000000 - ", "000000011111100000");
        }

        File outputStream1 = getPublicPicturesDirectory("A");
        idan = outputStream1.getPath()+"/"+fileName;
        FileInputStream inputStream = new FileInputStream(idan);
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
            }

            // Releasing resources.
            bufferedReader.close();
            streamReader.close();
            inputStream.close();

            content = stringBuilder.toString();
        }

        return content;
    }

    public boolean deleteInternalFile(String fileName) {
        return m_context.deleteFile(fileName);
    }

    String[] getInternalFileList() {
        return m_context.fileList();
    }






    //------- We check to see if there is External Storage. ------------

    /*
       is External Storage Writable {
     */
    public static boolean isExternalStorageWritable() {
        //We question the environment of the device ( Environment.______ )
        String state = Environment.getExternalStorageState();
        //Checks whether there is External Storage and return true or false
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    /*
       is External Storage Readable {
     */
    public static boolean isExternalStorageReadable() {
        //We question the environment of the device ( Environment.______ )
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //------------------------------------------------------------------

    // Public == user-related.
    public static File getPublicPicturesDirectory(String picturesFolder) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), picturesFolder);
        if (!file.mkdirs()) {
            Log.e("SNAPGuidesError", "Directory not created");
        }
        return file;
    }
//
//    // Private == app-related.
//    public File getPrivatePicturesDirectory(String picturesFolder) {
//        File file = new File(m_context.getExternalFilesDir(
//                Environment.DIRECTORY_PICTURES), picturesFolder);
//        if (!file.mkdirs()) {
//            Log.e("SNAPGuidesError", "Directory not created");
//        }
//        return file;
//    }

}