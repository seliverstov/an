package com.animenavigator.model;

import android.database.Cursor;

import static com.animenavigator.db.Contract.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by a.g.seliverstov on 21.03.2016.
 */
public class Anime {

    public Integer _id;
    public String title;
    public String posterUrl;
    public float rating;
    public List<String> genres;
    public List<String> themes;
    public List<String> alternativeTitles;
    public List<String> creators;
    public String plot;

    public static String printList(List list){
        String result = list.toString();
        if (result.startsWith("[")) result=result.substring(1);
        if (result.endsWith("]")) result=result.substring(0,result.length()-1);
        return result.replaceAll("Genre: ","").replaceAll("Theme: ", "");
    }

    public static List<String> genresFromCursor(Cursor c){
        List<String> result = new ArrayList<>();
        while (c.moveToNext()){
            result.add(c.getString(c.getColumnIndex(GenreEntry.NAME_COLUMN)));
        }
        return result;
    }

    public static String genresFromCursorAsString(Cursor c){
        String result = "";
        while (c.moveToNext()){
            String s = c.getString(c.getColumnIndex(GenreEntry.NAME_COLUMN));
            result+= (c.isFirst())?s:", "+s;
        }
        return result;
    }

    public static List<String> themesFromCursor(Cursor c){
        List<String> result = new ArrayList<>();
        while (c.moveToNext()){
            result.add(c.getString(c.getColumnIndex(ThemeEntry.NAME_COLUMN)));
        }
        return result;
    }

    public static String themesFromCursorAsString(Cursor c){
        String result = "";
        while (c.moveToNext()){
            String s = c.getString(c.getColumnIndex(ThemeEntry.NAME_COLUMN));
            result+= (c.isFirst())?s:", "+s;
        }
        return result;
    }

    public static List<String> creatorsFromCursor(Cursor c){
        List<String> result = new ArrayList<>();
        while (c.moveToNext()){
            result.add(c.getString(c.getColumnIndex(PersonEntry.NAME_COLUMN)));
        }
        return result;
    }

    public static String creatorsFromCursorAsString(Cursor c){
        String result = "";
        while (c.moveToNext()){
            String s = c.getString(c.getColumnIndex(PersonEntry.NAME_COLUMN));
            result+= (c.isFirst())?s:", "+s;
        }
        return result;
    }

    public static Anime fromCursor(Cursor c){
        Anime a = new Anime();
        a._id = c.getInt(c.getColumnIndex(MangaEntry._ID));
        a.title = c.getString(c.getColumnIndex(MangaEntry.NAME_COLUMN));
        a.plot = c.getString(c.getColumnIndex(MangaEntry.PLOT_COLUMN));
        a.rating = c.getFloat(c.getColumnIndex(MangaEntry.WEIGHTED_SCORE_COLUMN));
        a.posterUrl = c.getString(c.getColumnIndex(MangaEntry.PICTURE_COLUMN));

        a.alternativeTitles = new ArrayList<>();
        a.alternativeTitles.add(a.title);
        return a;
    }
}
