package pl.krzystooof.stanwodwpolsce.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class mSharedPref {
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    Gson gson;
    String LogTag;
    String sharedPreferencesName;
    String favouritesName;
    String dataName;
    String adapterPositionName;

    public mSharedPref(Context context) {
        gson = new Gson();
        LogTag = "mSharedPref";
        sharedPreferencesName = "sharedPref";
        favouritesName = "favourites";
        dataName = "data";
        adapterPositionName = "adapterPositionName";
        this.sharedPref = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }


    public String dataObjectToJson(DataFromSource dataObject) {
        Log.i(LogTag, "dataObject to json called");
        return gson.toJson(dataObject);
    }

    public DataFromSource dataObjectFromJson(String jsonString) {
        Log.i(LogTag, "dataObject from json called");
        return gson.fromJson(jsonString, DataFromSource.class);
    }

    public void saveData(ArrayList<DataFromSource> data) {
        Set<String> dataSet = new HashSet<>();
        for (DataFromSource dataObject : data) {
            dataSet.add(dataObjectToJson(dataObject));
        }
        editor.putStringSet(dataName, dataSet).apply();
        Log.i(LogTag, "data saved to SharedPref");
    }

    public ArrayList<DataFromSource> readData() {
        ArrayList<DataFromSource> data = new ArrayList<>();

        Set<String> emptySet = new HashSet<>();
        Set<String> dataSet = sharedPref.getStringSet(dataName, emptySet);
        for (String jsonString : dataSet) {
            data.add(dataObjectFromJson(jsonString));
        }
        Log.i(LogTag, "data red from SharedPref");
        return data;
    }

    public void saveFavourites(ArrayList<String> favourites) {
        Set<String> favouritesSet = new HashSet<>(favourites);
        editor.putStringSet(favouritesName, favouritesSet).apply();
        Log.i(LogTag, "favourites saved to SharedPref");
    }

    public ArrayList<String> readFavourites() {
        Set<String> emptySet = new HashSet<>();
        Set<String> dataSet = sharedPref.getStringSet(favouritesName, emptySet);
        Log.i(LogTag, "favourites red from SharedPref");
        return  new ArrayList<String>(dataSet);
    }

    public boolean readPaused() {
        return sharedPref.getBoolean("paused", false);
    }

    public void savePaused(boolean read) {
        sharedPref.edit().putBoolean("paused", read).commit();
    }
    public void saveAdapterPosition(int position){
        Log.i(LogTag, "save adapter position called");
        sharedPref.edit().putInt(adapterPositionName, position).commit();
    }
    public int readAdapterPosition(){
        Log.i(LogTag, "read adapter position called");
        return sharedPref.getInt(adapterPositionName,0);
    }
}
