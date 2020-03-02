package com.antika.berk.ggeasy.helper;

import android.content.Context;
import android.util.Log;

import com.antika.berk.ggeasy.R;
import com.antika.berk.ggeasy.object.ChampionMasterObject;
import com.antika.berk.ggeasy.object.ChampionObject;
import com.antika.berk.ggeasy.object.CurrentGameObject;
import com.antika.berk.ggeasy.object.Item2Object;
import com.antika.berk.ggeasy.object.ItemObject;
import com.antika.berk.ggeasy.object.LeagueObject;
import com.antika.berk.ggeasy.object.MasterObject;
import com.antika.berk.ggeasy.object.MatchHistoryObject;
import com.antika.berk.ggeasy.object.MatchIdObject;
import com.antika.berk.ggeasy.object.MissionObject;
import com.antika.berk.ggeasy.object.ParticipantObject;
import com.antika.berk.ggeasy.object.RozetObject;
import com.antika.berk.ggeasy.object.SpellObject;
import com.antika.berk.ggeasy.object.SummonerObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RiotApiHelper {
    public String apiKey   = "RGAPI-2df266e9-b08a-46d5-8367-b20243ffbf58";
    public String version  = "9.17.1";
    //Get summoner object with summoner name
    //V-3 YAPILDI
    public SummonerObject getSumonner(String summonerName, String region) {
        SummonerObject sumonner;

        JSONObject sumonnerObject;

        String JSONString = readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/summoner/v4/summoners/by-name/"+convertToUTF8(summonerName)+"?api_key="+apiKey);

        try{sumonnerObject = new JSONObject(JSONString);
            sumonner = new SummonerObject(null, sumonnerObject.getString("id"),
                    sumonnerObject.getString("accountId"), sumonnerObject.getString("puuid"),
                    sumonnerObject.getString("name"),sumonnerObject.getInt("profileIconId"),sumonnerObject.getInt("summonerLevel"));
            return sumonner;
        }
        catch (Exception e){Log.e("Hata",
                "Boş değer döndü SumonnerName: " + summonerName + " Region: " + region + " API Key: " + apiKey +
                        "\n" + e.toString());
            return null; }
    }


    //get current match data
    //V-3 YAPILDI
    public List<ChampionMasterObject> getMasteries(String summonerID, String region,int championID){
        List<ChampionMasterObject> masterObjects = new ArrayList<ChampionMasterObject>();

        try{
            masterObjects.clear();
            String data=readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"+summonerID+"/by-champion/"+championID+"?api_key="+apiKey);
            JSONObject master = new JSONObject(data);
            masterObjects.add(new ChampionMasterObject(master.getInt("championPoints"), master.getInt("championPointsUntilNextLevel"), master.getInt("championLevel"),
                    master.getInt("tokensEarned"), master.getInt("championId"), master.getInt("championPointsSinceLastLevel"),
                    master.getString("lastPlayTime"), master.getBoolean("chestGranted")));
        }
        catch (Exception e){
            masterObjects.add(new ChampionMasterObject(0, 0, 0,
                    0, 0, 0,
                    "", false));
        }

        return masterObjects;
    }
    //get current match data
    public CurrentGameObject getCurrentMatch(String summonerID, String region){
        CurrentGameObject cgo;
        List<ParticipantObject> participants = new ArrayList<ParticipantObject>();


        JSONObject obje1, obje2;
        JSONArray array1;

        String JSONString = readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/"+summonerID+"?api_key=" + apiKey);
        try{
            obje1 = new JSONObject(JSONString);
            array1 = obje1.getJSONArray("participants");
            for (int i = 0; i < array1.length(); i++){
                List<MasterObject> masterObjects = new ArrayList<MasterObject>();
                masterObjects.clear();
                obje2 = array1.getJSONObject(i);

                participants.add(new ParticipantObject(obje2.getInt("spell1Id"), obje2.getInt("spell2Id"), obje2.getInt("profileIconId"),
                        obje2.getInt("championId"), obje2.getInt("teamId"), obje2.getString("summonerId"), obje2.getString("summonerName"),masterObjects));
            }
            cgo = new CurrentGameObject(obje1.getInt("gameLength"),
                    obje1.getInt("mapId"),
                    obje1.getInt("gameId"),
                    obje1.getInt("gameQueueConfigId"),
                    obje1.getString("gameMode"),
                    obje1.getString("gameType"),
                    participants);
            return cgo;
        }catch (Exception e){Log.e("Hata", e.toString());}

        return null;
    }
    //get sumonner leagues stats
    //V-3 YAPILDI
    public List<LeagueObject> getSummonerLeague(String summonerID, String region){
        List<LeagueObject> leagues = new ArrayList<LeagueObject>();
        JSONObject obje1;
        JSONArray array1;

        String JSONString = readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/league/v4/entries/by-summoner/"+summonerID+"?api_key=" + apiKey);

        try {
            array1 = new JSONArray(JSONString);
            int miniSeriestarget, miniSerieslosses, miniSerieswins;
            String miniSeriesprogress;

            for (int i = 0; i < array1.length(); i++){
                try{miniSeriesprogress = array1.getJSONObject(i).getJSONObject("miniSeries").getString("progress");}
                catch (Exception e){miniSeriesprogress = "";}
                try{miniSeriestarget = array1.getJSONObject(i).getJSONObject("miniSeries").getInt("target");}
                catch (Exception e){miniSeriestarget = 0;}
                try{miniSerieslosses = array1.getJSONObject(i).getJSONObject("miniSeries").getInt("losses");}
                catch (Exception e){miniSerieslosses = 0;}
                try{miniSerieswins = array1.getJSONObject(i).getJSONObject("miniSeries").getInt("wins");}
                catch (Exception e){miniSerieswins = 0;}
                if (array1.getJSONObject(i).getString("queueType").equals("RANKED_SOLO_5x5"))
                    leagues.add(new LeagueObject(array1.getJSONObject(i).getString("queueType"),
                        array1.getJSONObject(i).getString("leagueId"),
                        array1.getJSONObject(i).getString("tier"),
                        array1.getJSONObject(i).getString("rank"),
                        array1.getJSONObject(i).getInt("leaguePoints"),
                        array1.getJSONObject(i).getInt("losses"),
                        array1.getJSONObject(i).getInt("wins"),
                        array1.getJSONObject(i).getBoolean("freshBlood"),
                        array1.getJSONObject(i).getBoolean("hotStreak"),
                        array1.getJSONObject(i).getBoolean("inactive"),
                        array1.getJSONObject(i).getBoolean("veteran"),
                        miniSeriesprogress,
                        miniSeriestarget,
                        miniSerieslosses,
                        miniSerieswins));
            }
            return leagues;
        }catch (Exception e){
            leagues.add(new LeagueObject("", "", "UNRANKED", "", 0, 0, 0, false, false, false, false, "", 0, 0, 0));
            return leagues;
        }

    }
    //get items
    //V-3 YAPILDI
    public List<ItemObject> getItem(Context context){
        List<ItemObject> item = new ArrayList<ItemObject>();

        JSONObject obje1, obje2, obje3,obje4,obje5;
       try{
           String JSONString = readURL("http://ggeasylol.com/api/"+ context.getString(R.string.language2)+"/item.json");
           obje1 = new JSONObject(JSONString);
            obje2 = obje1.getJSONObject("data");
            Iterator<?> key = obje2.keys();

            while (key.hasNext()){
                List<Item2Object> from = new ArrayList<Item2Object>();
                List<Item2Object> to = new ArrayList<Item2Object>();
                String gold="";
                String resim="";
                obje3 = obje2.getJSONObject((String) key.next());
                obje4=obje3.getJSONObject("maps");
                try {JSONArray array=obje3.getJSONArray("into");
                    for(int i=0;i<array.length();i++){
                        to.add(new Item2Object(array.getString(i),obje1.getString("version")));
                    }
                }catch (Exception e){

                }
                try {JSONArray array=obje3.getJSONArray("from");
                    for(int i=0;i<array.length();i++){
                        from.add(new Item2Object(array.getString(i),obje1.getString("version")));
                    }
                }catch (Exception e){

                }
                try {obje5=obje3.getJSONObject("gold");gold=obje5.optString("total");} catch (Exception e){}
                try {obje5=obje3.getJSONObject("image");resim=obje5.optString("full");} catch (Exception e){}
                try {
                    if (obje4.getBoolean("11"))
                        item.add(new ItemObject(resim , obje3.getString("name"),obje1.getString("version"),gold, obje3.getString("plaintext"),from,to));

                }
                catch (Exception e){
                }
            }
            return item;
       }catch (Exception e){

       }
        return null;
    }
    //get champion object from id
    //V-3 YAPILDI
    public ChampionObject getStaticChampion(int championID){
        ChampionObject co;
        JSONArray obje1;

        try{
            String JSONString = readURL("http://ggeasylol.com/json/get_champion.php?championID="+championID);

            obje1 = new JSONArray(JSONString);

                JSONObject object=obje1.getJSONObject(0);
                co = new ChampionObject(object.getString("championKey"), object.getString("championName"),
                        "", object.getString("championID"));



            return co;
        }
        catch (Exception e){

        }
        return null;
    }
    //get spell object from id
    //V-3 YAPILDI
    public SpellObject getStaticSpell(int spellID){
        SpellObject so;


        try{
            String JSONString = readURL("http://ggeasylol.com/json/get_spell.php?spellID="+spellID);
            JSONObject object=new JSONObject(JSONString);
            so = new SpellObject(Integer.toString(object.getInt("spellID")), object.getString("spellKey"), object.getString("spellName"));

            return so;
        }catch (Exception e){

        }
        return null;

    }
    //getChampion masteries
    //V-3 YAPILDI
    public List<ChampionMasterObject> getChampionMasteries(String summonerID, String region){
        List<ChampionMasterObject> masteries = new ArrayList<ChampionMasterObject>();

        String JSONString = readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"+summonerID+"?api_key=" + apiKey);

        JSONObject obje1;
        JSONArray array1;

        try{
            array1 = new JSONArray(JSONString);
            for (int i = 0; i < array1.length(); i++){
                obje1 = array1.getJSONObject(i);

                masteries.add(new ChampionMasterObject(obje1.getInt("championPoints"),
                        obje1.getInt("championPointsUntilNextLevel"),
                        obje1.getInt("championLevel"),
                        obje1.getInt("tokensEarned"),
                        obje1.getInt("championId"),
                        obje1.getInt("championPointsSinceLastLevel"),
                        obje1.getString("lastPlayTime"),
                        obje1.getBoolean("chestGranted")));
            }
            return masteries;
        }catch (Exception e){ }
        return null;
    }
    //get static champion data
    //V-3 YAPILDI
    public List<ChampionObject> getChampionStaticData(Context context){
        List<ChampionObject> champions = new ArrayList<ChampionObject>();

        JSONObject obje1, obje2, obje3;

        try{
            String JSONString = readURL("https://"+regionToPlatform(context.getString(R.string.language)).toLowerCase()+".api.riotgames.com/lol/static-data/v4/champions?locale="+context.getString(R.string.language2)+"&api_key="+ apiKey);
            obje1 = new JSONObject(JSONString);
            obje2 = obje1.getJSONObject("data");

            Iterator<?> key = obje2.keys();
            while (key.hasNext()){
                obje3 = obje2.getJSONObject((String) key.next());

                champions.add(new ChampionObject(obje3.getString("key"),
                        obje3.getString("name"),
                        obje3.getString("title"),
                        Integer.toString(obje3.getInt("id"))));
            }
            return champions;
        }catch (Exception e){
            try {
                String JSONString = readURL("https://br1.api.riotgames.com/lol/static-data/v4/champions?locale="+context.getString(R.string.language2)+"&api_key="+ apiKey);
                obje1 = new JSONObject(JSONString);
                obje2 = obje1.getJSONObject("data");

                Iterator<?> key = obje2.keys();
                while (key.hasNext()){
                    obje3 = obje2.getJSONObject((String) key.next());

                    champions.add(new ChampionObject(obje3.getString("key"),
                            obje3.getString("name"),
                            obje3.getString("title"),
                            Integer.toString(obje3.getInt("id"))));
                }
                return champions;

            }
            catch (Exception e1){

            }
        }
        return null;
    }



    //get champion freeToPlay
    //V-3 YAPILDI
    public List<Integer> getChampionFreeToPlay(String region){
        List<Integer> champions=new ArrayList<Integer>();
        JSONObject obj1,obj2;
        JSONArray array1;
        try {
            String JSONString= readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/platform/v3/champion-rotations?api_key="+apiKey);
            obj1=new JSONObject(JSONString);
            array1= obj1.getJSONArray("freeChampionIds");
            for(int i=0;i<array1.length();i++){
                champions.add(array1.getInt(i));
            }
            return champions;

        }
        catch (Exception e){

        }
        return null;
    }
    //get Team Stats
    //V-3 YAPILDI

    //get Summoner Stats
    //V-3 YAPILDI
    public MissionObject getMatch(String  matchID, String region, String sihirdarID){
        String data=readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/match/v4/matches/"+matchID+"?api_key="+apiKey);
        MissionObject missionObject;
        try {
            JSONObject obj1=new JSONObject(data);
            JSONArray array=obj1.getJSONArray("participantIdentities");
            int y=0;
            for (int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject object1=object.getJSONObject("player");
                String id=object1.getString("summonerId");
                if(id.equals(sihirdarID)){
                    y=i;
                    break;
                }
            }
            JSONArray array1=obj1.getJSONArray("participants");
            JSONObject obj2=array1.getJSONObject(y);
            JSONObject obj3=obj2.getJSONObject("stats");
            try {
                missionObject=new MissionObject(obj3.getBoolean("win"),obj3.getInt("champLevel"),obj3.getInt("kills"),obj3.getInt("doubleKills"),obj3.getInt("tripleKills"),
                        obj3.getInt("quadraKills"),obj3.getInt("pentaKills"),obj3.getInt("deaths"),obj3.getInt("assists"),obj3.getInt("totalMinionsKilled"),obj3.getInt("neutralMinionsKilled"),
                        obj3.getInt("goldEarned"),obj3.getInt("turretKills"),obj3.getInt("wardsPlaced"),obj3.getInt("wardsKilled"),obj3.getInt("largestMultiKill"),obj3.getInt("totalDamageDealtToChampions"));

            }
            catch (Exception e){
                missionObject=new MissionObject(obj3.getBoolean("win"),obj3.getInt("champLevel"),obj3.getInt("kills"),obj3.getInt("doubleKills"),obj3.getInt("tripleKills"),
                        obj3.getInt("quadraKills"),obj3.getInt("pentaKills"),obj3.getInt("deaths"),obj3.getInt("assists"),obj3.getInt("totalMinionsKilled"),obj3.getInt("neutralMinionsKilled"),
                        obj3.getInt("goldEarned"),obj3.getInt("turretKills"),0,0,obj3.getInt("largestMultiKill"),obj3.getInt("totalDamageDealtToChampions"));

            }

            return missionObject;
        }
        catch (JSONException e) {
            missionObject=new MissionObject(false,0,0,0,0,
                    0,0,0,0,0,0,
                    0,0,0,0,0,0);


            return missionObject;
        }
    }
    public List<MatchHistoryObject> getHistory(List<MatchIdObject> matchID, String region, String sihirdarID){
        List<MatchHistoryObject> historyObject=new ArrayList<MatchHistoryObject>();
        for (int j=0;j<matchID.size();j++){
            String data=readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/match/v4/matches/"+matchID.get(j).getMatchID()+"?api_key="+apiKey);

            try {
                JSONObject obj1=new JSONObject(data);
                JSONArray array=obj1.getJSONArray("participantIdentities");
                int y=0;
                for (int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONObject object1=object.getJSONObject("player");
                    String id=object1.getString("summonerId");
                    if(id.equals(sihirdarID)){
                        y=i;
                        break;
                    }
                }
                JSONArray array1=obj1.getJSONArray("participants");
                JSONObject obj2=array1.getJSONObject(y);
                JSONObject obj3=obj2.getJSONObject("stats");

                try {

                    historyObject.add(new MatchHistoryObject(obj3.getBoolean("win"),obj3.getInt("champLevel"),obj3.getInt("kills"),obj3.getInt("deaths"),obj3.getInt("assists"),
                            obj2.getInt("championId"),obj1.getInt("queueId"),obj2.getInt("spell1Id"),obj2.getInt("spell2Id"),obj3.getInt("totalMinionsKilled"),obj3.getInt("neutralMinionsKilled"),obj3.getInt("goldEarned"),
                            obj3.getInt("item0"),obj3.getInt("item1"),obj3.getInt("item2"),obj3.getInt("item3"),obj3.getInt("item4"),obj3.getInt("item5"),obj3.getInt("item6")));
                }
                catch (Exception e){
                }

            }
            catch (JSONException e) {

            }
        }

        return historyObject;
    }
    //get PUAN
    public String getPuan(String sihirdarAdi,String sifre) {
        String data=readURL("http://ggeasylol.com/api/check_user.php?SihirdarAdi="+sihirdarAdi+"&Sifre="+sifre);
        try {
            JSONArray array=new JSONArray(data);
            JSONObject object=array.getJSONObject(0);



            return object.getString("Puan");
        }
        catch (Exception e){
            return "HATA";
        }


    }
    public List<RozetObject> getRozet(String sihirdarAdi,String region,String lang,String token,String icon) {
        String data=readURL("http://ggeasylol.com/json/login.php?SihirdarAdi="+sihirdarAdi+"&Region="+region+"&lang="+lang+"&token="+token+"&icon="+icon);
        List<RozetObject> rozetObjects=new ArrayList<RozetObject>();
        try {
            JSONArray array=new JSONArray(data);
            for(int i =0; i < array.length();i++) {
                JSONObject object=array.getJSONObject(i);
                rozetObjects.add(new RozetObject(object.getString("mission"),object.getInt("total")));
            }




            return rozetObjects;
        }
        catch (Exception e){
            return null;
        }


    }
    //get ROZET

    public List<MatchIdObject> getMatchIDs(String accountID, String region, int index){
        String data=readURL("https://"+regionToPlatform(region).toLowerCase()+".api.riotgames.com/lol/match/v4/matchlists/by-account/"+accountID+"?endIndex="+index+"&beginIndex=0&api_key="+apiKey);
        List<MatchIdObject> matchIdObject=new ArrayList<MatchIdObject>();

        try {
            JSONObject object=new JSONObject(data);
            JSONArray array=object.getJSONArray("matches");
            for (int i=0;i<array.length();i++){
                JSONObject object1=array.getJSONObject(i);
                matchIdObject.add(new MatchIdObject(object1.getString("gameId")));
            }



            return matchIdObject;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    //set Region Platform
    public String regionToPlatform(String region) {
        switch (region)
        {
            case "BR"  : return "BR1" ;
            case "EUNE": return "EUN1";
            case "EUW" : return "EUW1";
            case "JP"  : return "JP1" ;
            case "KR"  : return "KR"  ;
            case "LAN" : return "LA1" ;
            case "LAS" : return "LA2" ;
            case "NA"  : return "NA1" ;
            case "OCE" : return "OC1" ;
            case "TR"  : return "TR1" ;
            case "RU"  : return "RU"  ;
            case "PBE" : return "PBE1";
        }
        return null;
    }




    public String readURL(String link) {
        URL u = null;
        try {
            String new_link = link.replace(" ", "");
            Log.e("URL", new_link);
            u = new URL(new_link);
            URLConnection conn = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuffer buffer = new StringBuffer();

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                buffer.append(inputLine);

            in.close();

            return buffer.toString();
        }
        catch (Exception e) {
            Log.e("Hata",
                "İnternet sorunu" +
                        "\n" + e.toString());
            return null;
        }
    }
    private String convertToUTF8(String s) {
        Charset.forName("UTF-8").encode(s);
        return s;
    }
}
