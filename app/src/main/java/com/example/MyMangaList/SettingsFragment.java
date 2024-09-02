package com.example.MyMangaList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.MyMangaList.ViewModel.ListViewModel;

import java.util.List;

public class SettingsFragment extends Fragment {

    private TextView total_priceTextView;
    private TextView topsTextView;
    private TextView bottomsTextView;
    private TextView outwearTextView;
    private TextView accessoriesTextView;
    private TextView bagsTextView;
    private TextView footwearTextView;

    private ListViewModel listViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If true, the fragment has menu items to contribute.
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.statistics));

            total_priceTextView = view.findViewById(R.id.total_price);
            topsTextView = view.findViewById(R.id.tops);
            bottomsTextView = view.findViewById(R.id.bottoms);
            outwearTextView = view.findViewById(R.id.outwear);
            accessoriesTextView = view.findViewById(R.id.accessories);
            bagsTextView = view.findViewById(R.id.bags);
            footwearTextView = view.findViewById(R.id.footwear);

            listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int tops = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Tops")){
                            tops++;
                        }
                    }
                    topsTextView.setText(String.valueOf(tops));
                }
            });

            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int bottoms = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Bottoms")){
                            bottoms++;
                        }
                    }
                    bottomsTextView.setText(String.valueOf(bottoms));
                }
            });

            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int outwear = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Outwear")){
                            outwear++;
                        }
                    }
                    outwearTextView.setText(String.valueOf(outwear));
                }
            });

            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int accessories = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Accessories")){
                            accessories++;
                        }
                    }
                    accessoriesTextView.setText(String.valueOf(accessories));
                }
            });

            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int bags = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Bags")){
                            bags++;
                        }
                    }
                    bagsTextView.setText(String.valueOf(bags));
                }
            });

            listViewModel.getCardItems().observe((LifecycleOwner) activity, new Observer<List<CardItem>>() {
                @Override
                public void onChanged(List<CardItem> cardItems) {
                    int footwear = 0;
                    for(CardItem elem : cardItems){
                        if (elem.getCategory().equals("Footwear")){
                            footwear++;
                        }
                    }
                    footwearTextView.setText(String.valueOf(footwear));
                }
            });

            ListViewModel listViewModel =
                    new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Float>() {
                @Override
                public void onChanged(Float price) {
                    if(price == null){
                        total_priceTextView.setText("0.00");

                    } else {
                        total_priceTextView.setText(String.valueOf(price));
                    }
                }
            });
        }
    }

    /**
     * @param menu     The options menu in which you place your items.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.app_bar_finances).setVisible(false);
        menu.findItem(R.id.app_bar_search).setVisible(false);
    }
}
