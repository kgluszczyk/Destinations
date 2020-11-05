package com.kgluszczyk.destinations.presentation;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.kgluszczyk.destinations.data.Destination;
import com.kgluszczyk.destinations.domain.DestinationsInteractor;
import com.kgluszczyk.destinations.presentation.ListItemsFactory.BaseListItem;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

public class DestinationsViewModel extends ViewModel {

    private MutableLiveData<List<BaseListItem>> destinationListItemLiveData = new MutableLiveData<>();
    private DestinationsInteractor destinationsInteractor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public DestinationsViewModel(final DestinationsInteractor destinationsInteractor) {
        this.destinationsInteractor = destinationsInteractor;
        observeDestinations();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public LiveData<List<BaseListItem>> getData() {
        return destinationListItemLiveData;
    }

    private void observeDestinations() {
        compositeDisposable.add(destinationsInteractor.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    destinationListItemLiveData.postValue(convertResponse(response));
                    Log.d("RX", "From DB: " + response);
                }, error -> Log.e("RX", "Ups...", error)));

        compositeDisposable.add(
                Flowable.interval(30, TimeUnit.SECONDS)
                        .startWith(Long.valueOf(0))
                        .doOnNext(timer -> Log.d("RX", "Timer" + timer))
                        .flatMap(__ -> destinationsInteractor.fetch())
                        .subscribeOn(Schedulers.io())
                        .doOnNext(response -> {
                            boolean isReplaced = destinationsInteractor.replace(response);
                            Log.d("RX", isReplaced ? "" : "NO" + "Insert and replace to db:" + response);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> Log.d("RX", "DB updated"), error -> Log.e("RX", "Ups...", error)));
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
}
