package com.kgluszczyk.destinations.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.api.DestinationsService;
import com.kgluszczyk.destinations.data.Destination;
import com.kgluszczyk.destinations.data.DestinationDao;
import com.kgluszczyk.destinations.presentation.ListItemsFactory;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.BaseListItem;
import com.kgluszczyk.destinations.presentation.MyItemRecyclerViewAdapter;
import dagger.android.support.DaggerFragment;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        compositeDisposable.add(destinationDao.getAllRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    adapter.updateData(convertResponse(response));
                    Log.d("RX", "From DB: " + response);
                }, error -> Log.e("RX", "Ups...", error)));

        compositeDisposable.add(
                Flowable.interval(30, TimeUnit.SECONDS)
                        .startWith(Long.valueOf(0))
                        .doOnNext(timer -> Log.d("RX", "Timer" + timer))
                        .flatMap(__ -> destinationsService.getDestinationsSingle().toFlowable())
                        .subscribeOn(Schedulers.io())
                        .doOnNext(response -> {
                            Log.d("RX", "Insert and replace to db:" + response);

                            destinationDao.replaceAll(response);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> Log.d("RX", "DB updated"), error -> Log.e("RX", "Ups...", error)));
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
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
