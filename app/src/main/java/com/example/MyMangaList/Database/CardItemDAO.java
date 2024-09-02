package com.example.MyMangaList.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.MyMangaList.CardItem;

import java.util.List;

@Dao
public interface CardItemDAO {

    //The selected OnConflictStrategy ignores a new CardItem if it's already in the list
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCardItem(CardItem cardItem);

    // @Transaction: anything inside the method runs in a single transaction.
    @Transaction
    @Query("SELECT * FROM MyMangaList ORDER BY item_id DESC")
    LiveData<List<CardItem>> getCardItems();

    @Delete
    void delete(CardItem cardItem);

    @Transaction
    @Update
    void incrementCount(CardItem cardItem);

    @Transaction
    @Query("SELECT SUM(item_price) FROM MyMangaList")
    LiveData<Float> get_total_price();

}
