package com.kgluszczyk.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kgluszczyk.myapplication.dummy.ListItemsFactory;
import com.kgluszczyk.myapplication.dummy.ListItemsFactory.BaseListItem;
import dagger.android.support.DaggerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ItemFragment extends DaggerFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    @Inject
    DestinationsService destinationsService;
    @Inject
    DestinationDao destinationDao;
    @Inject
    MyItemRecyclerViewAdapter adapter;

    private int mColumnCount = 1;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public ItemFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        Context context = view.getContext();
        final RecyclerView recyclerView = (RecyclerView) view;
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        recyclerView.setAdapter(adapter);
        compositeDisposable.add(destinationDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    adapter.updateData(convertResponse(response));
                    Log.d("TAG", "Odczytano elementy");
                }, error -> Log.e("INTERNET", "Upse...", error)));

        compositeDisposable.add(destinationsService.getDestinationsSingle()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> {
                    destinationDao.deleteAll();
                    destinationDao.insertAll(response);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("TAG", "Dodano elementy");
                }, error -> Log.e("INTERNET", "Upse...", error)));
        return view;
    }

    public MyItemRecyclerViewAdapter getAdapter() {
        return adapter;
    }

    private List<BaseListItem> convertResponse(List<Destination> destinations) {
        List<BaseListItem> listItems = new ArrayList<>();
        for (int i = 0; i < destinations.size(); i++) {
            Destination destination = destinations.get(i);
            if (i == 0 || !destinations.get(i - 1).getCountry().equals(destination.getCountry())) {
                listItems.add(ListItemsFactory.createCountry(destination));
            }
            listItems.add(ListItemsFactory.createDestination(destination));
        }
        return listItems;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(BaseListItem item);

        void onLongClickListener(BaseListItem item);
    }
}
