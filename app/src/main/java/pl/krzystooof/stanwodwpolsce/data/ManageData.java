package pl.krzystooof.stanwodwpolsce.data;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ManageData {
    public ArrayList<DataFromSource> download(String jsonUrl) throws IOException {
        //connect
        URL dataUrl = new URL(jsonUrl);
        InputStream inputStream = dataUrl.openStream();

        //cast data from connection to String
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String jsonString = s.hasNext() ? s.next() : "";

        //cast string to objects
        DataFromSource[] data = new Gson().fromJson(jsonString, DataFromSource[].class);
        return new ArrayList<>(Arrays.asList(data));
    }
    public void filer(String keyword, ArrayList<DataFromSource> dataArray){
        String regex = keyword + "(.)*";
        for (DataFromSource dataObject : dataArray){

            if (dataObject.stationName.matches(regex) || dataObject.riverName.matches(regex)){
                //object matches regex
            }
            else {
                dataArray.remove(dataObject);
            }
        }
    }
}
