package com.example.MyMangaList.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.MyMangaList.CardItem;

import java.util.List;

public class CardItemRepository {
    private final CardItemDAO cardItemDAO;

    private final LiveData<List<CardItem>> cardItemList;

    public CardItemRepository(Application application){
        CardItemDatabase db = CardItemDatabase.getDatabase(application);
        cardItemDAO = db.cardItemDAO();

        cardItemList = cardItemDAO.getCardItems();
    }


    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<CardItem>> getCardItemList() {
        return cardItemList;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void addCardItem(CardItem cardItem) {
        CardItemDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.addCardItem(cardItem);
            }
        });
    }

    public void deleteCardItem(CardItem item) {
        CardItemDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.delete(item);
            }
        });
    }

    public void increment(CardItem cardItem){
        CardItemDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cardItemDAO.incrementCount(cardItem);
            }
        });
    }

    public LiveData<Float> getTotalPrice(){ return cardItemDAO.get_total_price(); }

}
