package pl.krzystooof.stanwodwpolsce.data;

import com.google.gson.Gson;



import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Download {
    public ArrayList<mData> run(String jsonUrl) throws IOException {
        //connect
        URL dataUrl = new URL(jsonUrl);
        InputStream inputStream = dataUrl.openStream();

        //cast data from connection to String
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String jsonString = s.hasNext() ? s.next() : "";

        //cast string to objects
        mData[] data = new Gson().fromJson(jsonString, mData[].class);
        return new ArrayList<>(Arrays.asList(data));
    }
}
