package com.example.MyMangaList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.MyMangaList.ViewModel.AddViewModel;
import com.example.MyMangaList.ViewModel.ListViewModel;

public class DetailsFragment extends Fragment {
    private TextView itemTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private TextView priceTextView;
    private TextView categoryTextView;
    private TextView locationTextView;

    private ImageView itemImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, "Details");

            itemTextView = view.findViewById(R.id.item_name);
            descriptionTextView = view.findViewById(R.id.item_description);
            dateTextView = view.findViewById(R.id.acquisition_date);
            priceTextView = view.findViewById(R.id.item_price);
            categoryTextView = view.findViewById(R.id.category_label);
            itemImageView = view.findViewById(R.id.item_image);
            locationTextView = view.findViewById(R.id.location);

            ListViewModel listViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getItemSelected().observe(getViewLifecycleOwner(), new Observer<CardItem>() {
                @Override
                public void onChanged(CardItem cardItem) {
                    itemTextView.setText(cardItem.getItemName());
                    descriptionTextView.setText(cardItem.getItemDescription());
                    dateTextView.setText(cardItem.getDate());
                    priceTextView.setText(cardItem.getPrice());
                    categoryTextView.setText(cardItem.getCategory());
                    locationTextView.setText(cardItem.getLocation());
                    String image_path = cardItem.getImageResource();
                    if (image_path.contains("ic_")){
                        Drawable drawable = ResourcesCompat.getDrawable(activity.getResources(),
                                R.drawable.ic_baseline_android_24, activity.getTheme());
                        itemImageView.setImageDrawable(drawable);
                    } else {
                        Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(image_path));
                        if (bitmap != null){
                            itemImageView.setImageBitmap(bitmap);
                            itemImageView.setBackgroundColor(Color.WHITE);
                        }
                    }
                }
            });

            AddViewModel addViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
            view.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addViewModel.deleteCardItem(listViewModel.getItemSelected().getValue());
                    ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();
                }
            });

            view.findViewById(R.id.wear_count_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listViewModel.getItemSelected().getValue().setWear_count(listViewModel.getItemSelected().getValue().getWear_count()+1);
                    addViewModel.increment(listViewModel.getItemSelected().getValue());
                    ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();
                }
            });

        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.app_bar_search).setVisible(false);
    }
}
