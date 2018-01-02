package com.herethere.www.autosearch;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.herethere.www.Entity.autosearch.Poi;
import com.herethere.www.Entity.autosearch.TMapSearchInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KJH on 2017-09-06.
 */

public class AutoCompleteParse extends AsyncTask<String, Void, ArrayList<AutoCompleteItem>>
{
    private final String TMAP_API_KEY = "7d54b976-ee11-3f11-a5d8-0846567726ef";
    private final int SEARCH_COUNT = 20;  // minimum is 20
    private ArrayList<AutoCompleteItem> mListData;
    private AutoCompleteAdapter mAdapter;

    public AutoCompleteParse(AutoCompleteAdapter adapter) {
        this.mAdapter = adapter;
        mListData = new ArrayList<AutoCompleteItem>();
    }

    @Override
    protected ArrayList<AutoCompleteItem> doInBackground(String... word) {
        return getAutoComplete(word[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<AutoCompleteItem> autoCompleteItems) {
        mAdapter.setList(autoCompleteItems);
        mAdapter.notifyDataSetChanged();
    }

    public ArrayList<AutoCompleteItem> getAutoComplete(String word){

        try{
            String encodeWord = URLEncoder.encode(word, "UTF-8");
            URL acUrl = new URL(
                    "https://apis.skplanetx.com/tmap/pois?areaLMCode=&centerLon=&centerLat=&" +
                            "count=" + SEARCH_COUNT + "&page=&reqCoordType=&" + "" +
                            "searchKeyword=" + encodeWord + "&callback=&areaLLCode=&multiPoint=&searchtypCd=&radius=&searchType=&resCoordType=WGS84GEO&version=1"
            );

            HttpURLConnection acConn = (HttpURLConnection)acUrl.openConnection();
            acConn.setRequestProperty("Accept", "application/json");
            acConn.setRequestProperty("appKey", TMAP_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    acConn.getInputStream()));

            String line = reader.readLine();
            if(line == null){
                mListData.clear();
                return mListData;
            }

            reader.close();

            mListData.clear();
            TMapSearchInfo searchPoiInfo = new Gson().fromJson(line, TMapSearchInfo.class);

            List<Poi> poi =  searchPoiInfo.getSearchPoiInfo().getPois().getPoi();
            for(int i =0; i < poi.size(); i++){
                String fullAddr = poi.get(i).getUpperAddrName() + " " + poi.get(i).getMiddleAddrName() +
                        " " + poi.get(i).getLowerAddrName() + " " + poi.get(i).getDetailAddrName();


                mListData.add(new AutoCompleteItem()
                                .setTitle(poi.get(i).getName())
                                .setAddress(fullAddr)
                                .setLatitude(Double.parseDouble(poi.get(i).getFrontLat()))
                                .setLongitude(Double.parseDouble(poi.get(i).getFrontLon()))
                                .build()
                );
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return mListData;
    }
}