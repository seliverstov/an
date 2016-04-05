package com.animenavigator.main;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animenavigator.common.AnimeViewHolder;
import com.animenavigator.common.ImageLoader;
import com.animenavigator.common.ItemSelectedCallback;
import com.animenavigator.R;
import com.animenavigator.db.Contract;
import com.animenavigator.model.Anime;

import java.text.DecimalFormat;

import skyfish.CursorRecyclerViewAdapter;

/**
 * Created by a.g.seliverstov on 22.03.2016.
 */
public class ListAdapter extends CursorRecyclerViewAdapter<AnimeViewHolder> {
    private Context mContext;

    public ListAdapter(Context context, Cursor cursor){
        super(context, cursor);
        this.mContext = context;
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        AnimeViewHolder viewHolder = new AnimeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AnimeViewHolder holder, Cursor cursor) {
        final Anime anime = Anime.fromCursor(cursor);

        holder.mTitle.setText(anime.title);

        holder.mRating.setText(mContext.getString(R.string.rating_tmp, new DecimalFormat("#.#").format(anime.rating)));

        Cursor genresCursor  = mContext.getContentResolver().query(Contract.GenreEntry.buildGenreForManga((long) anime._id), null, null, null, null);
        if (genresCursor!=null){
            String s = Anime.genresFromCursorAsString(genresCursor);
            s = (s==null || "".equals(s))? mContext.getString(R.string.unknown) : s;
            holder.mGenres.setText(mContext.getString(R.string.genres_tmp, s));
            genresCursor.close();
        }

        Cursor themesCursor  = mContext.getContentResolver().query(Contract.ThemeEntry.buildThemesForManga((long) anime._id), null, null, null, null);
        if (themesCursor!=null){
            String s = Anime.themesFromCursorAsString(themesCursor);
            s = (s==null || "".equals(s))? mContext.getString(R.string.unknown) : s;
            holder.mThemes.setText(mContext.getString(R.string.themes_tmp, s));
            themesCursor.close();
        }

        Cursor creatorsCursor  = mContext.getContentResolver().query(Contract.PersonEntry.buildPersonsForManga((long) anime._id), null, null, null, null);
        if (creatorsCursor!=null){
            String s = Anime.creatorsFromCursorAsString(creatorsCursor);
            s = (s==null || "".equals(s))? mContext.getString(R.string.unknown) : s;
            holder.mCreators.setText(s);
            creatorsCursor.close();
        }

        holder.mPlot.setText((anime.plot==null)?mContext.getString(R.string.unknown):anime.plot);

        ImageLoader.loadImageToView(anime.posterUrl, mContext, holder.mPoster);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof ItemSelectedCallback) {
                    ((ItemSelectedCallback) mContext).onItemSelected(anime._id);
                }
            }
        });
    }
}
