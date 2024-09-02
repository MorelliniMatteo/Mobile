package com.example.MyMangaList.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.MyMangaList.CardItem;
import com.example.MyMangaList.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CardItem.class, User.class}, version = 8, exportSchema = false)
public abstract class CardItemDatabase extends RoomDatabase {
    public abstract CardItemDAO cardItemDAO();
    public abstract UserDAO userDAO();

    private static volatile CardItemDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CardItemDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardItemDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    CardItemDatabase.class, "card_item_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}