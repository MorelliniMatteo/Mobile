package com.example.MyMangaList.ViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.MyMangaList.CardItem;
import com.example.MyMangaList.Database.CardItemRepository;

/**
 * ViewModel that handles the data flow between the MainActivity and its Fragment
 */
public class AddViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();

    private final CardItemRepository repository;

    public AddViewModel(@NonNull Application application) {
        super(application);
        repository = new CardItemRepository(application);
    }

    /**
     * Method that set the image taken as a bitmap
     * @param bitmap the image taken by the user
     */
    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap.setValue(bitmap);
    }

    public MutableLiveData<Bitmap> getImageBitmap() {
        return imageBitmap;
    }

    public void addCardItem(CardItem item) {
        repository.addCardItem(item);
    }

    public void deleteCardItem(CardItem item){ repository.deleteCardItem(item); }

    public void increment(CardItem item) {
        repository.increment(item);
    }

}
