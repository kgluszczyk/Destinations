package com.kgluszczyk.destinations.presentation;

import static com.kgluszczyk.destinations.presentation.ListItemsFactory.ListItemType.COUNTRY;
import static com.kgluszczyk.destinations.presentation.ListItemsFactory.ListItemType.DESTINATION;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import com.kgluszczyk.destinations.R;
import com.kgluszczyk.destinations.data.Destination;

public class ListItemsFactory {

    public static DestinationListItem createDestination(Destination destination) {
        return new DestinationListItem(Uri.parse(destination.getImage()), destination.getTitle(), destination.getDescription());
    }

    public static Country createCountry(Destination destination) {
        @DrawableRes int resource = -1;
        switch (destination.getCountry()){
            case "Scotland":
                resource = R.drawable.scotlant;
                break;
            case "Georgia":
                resource = R.drawable.georgia;
                break;
            case "Czech Republic":
                resource = R.drawable.czech_republic;
                break;
            case "Portugal":
                resource = R.drawable.portugal;
                break;
            case "Germany":
                resource = R.drawable.germany;
                break;
            case "Great Brittan":
                resource = R.drawable.great_brittan;
                break;
            case "Austria":
                resource = R.drawable.austria;
                break;
            case "Poland":
                resource = R.drawable.poland;
                break;

        }
        return new Country(resource, destination.getCountry());
    }

    public static class DestinationListItem extends BaseListItem {
        public final String content;
        public final String details;
        public final Uri uri;

        public DestinationListItem(final Uri uri, String content, String details) {
            this.uri = uri;
            this.content = content;
            this.details = details;
            item = DESTINATION;
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public static class Country extends BaseListItem {
        public @DrawableRes
        Integer logo;
        private String country;
        private Uri uri;
        private Bitmap imageBitmap;

        public Country() {
            item = COUNTRY;
        }

        public Country(final @DrawableRes int logo, String country) {
            this();
            this.logo = logo;
            this.country = country;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(final Uri data) {
            this.uri = data;
            imageBitmap = null;
        }

        public String getCountry() {
            return country;
        }

        public void setBitmap(final Bitmap imageBitmap) {
            this.imageBitmap = imageBitmap;
            uri = null;
        }

        public Bitmap getImageBitmap() {
            return imageBitmap;
        }
    }

    public static abstract class BaseListItem {
        ListItemType item;

        public ListItemType getItem() {
            return item;
        }
    }

    public enum ListItemType {
        DESTINATION, COUNTRY
    }
}
