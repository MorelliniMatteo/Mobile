package com.example.MyMangaList.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.MyMangaList.CardItem;
import com.example.MyMangaList.Database.CardItemRepository;

import java.util.List;

/**
 * ViewModel that handles the data flow between the list in HomeFragment and the element selected
 * in the DetailsFragment
 */
public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<CardItem> itemSelected = new MutableLiveData<>();

    public LiveData<List<CardItem>> cardItems;

    private CardItemRepository repository;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = new CardItemRepository(application);
        cardItems = repository.getCardItemList();
    }

    public LiveData<List<CardItem>> getCardItems() {
        return cardItems;
    }

    public MutableLiveData<CardItem> getItemSelected() {
        return itemSelected;
    }

    public void setItemSelected(CardItem cardItem) {
        itemSelected.setValue(cardItem);
    }

    public LiveData<Float> getTotalPrice() {
        return repository.getTotalPrice();
    }

}
