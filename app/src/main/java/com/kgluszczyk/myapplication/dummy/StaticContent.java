package com.kgluszczyk.myapplication.dummy;

import static com.kgluszczyk.myapplication.dummy.StaticContent.ListItemType.UNIWERSYTET;
import static com.kgluszczyk.myapplication.dummy.StaticContent.ListItemType.ZABYTEK;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import com.kgluszczyk.myapplication.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StaticContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<StaticContent.BazowyListItem> ITEMS = new ArrayList<com.kgluszczyk.myapplication.dummy.StaticContent.BazowyListItem>() {{
        add(new ZabytekItem(R.drawable.zabytek_1));
        add(new UniwersytetListItem(R.drawable.po, "Politechnika Opolska", "Założona w ..."));
        add(new ZabytekItem(R.drawable.zabytek_2));
        add(new ZabytekItem(R.drawable.zabytek_3));
        add(new UniwersytetListItem(R.drawable.uo, "Uniwersytet Opolsko", "Założony w ..."));
        add(new ZabytekItem(R.drawable.zabytek_4));
        add(new ZabytekItem(R.drawable.zabytek_5));
        add(new UniwersytetListItem(R.drawable.wsb, "WSB", "Założony w ..."));
        add(new UniwersytetListItem(R.drawable.wszia, "WSZIE", "Założony w ..."));
    }};

    public static class UniwersytetListItem extends com.kgluszczyk.myapplication.dummy.StaticContent.BazowyListItem {
        public final @DrawableRes
        int logo;
        public final String content;
        public final String details;

        public UniwersytetListItem(final int logo, String content, String details) {
            this.logo = logo;
            this.content = content;
            this.details = details;
            item = UNIWERSYTET;
        }

        @Override
        public String toString() {
            return content;
        }
    }

    public static class ZabytekItem extends com.kgluszczyk.myapplication.dummy.StaticContent.BazowyListItem {
        public final @DrawableRes
        int logo;
        private Uri uri;
        private Bitmap imageBitmap;

        public ZabytekItem(final @DrawableRes int logo) {
            this.logo = logo;
            item = ZABYTEK;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(final Uri data) {
            this.uri = data;
            imageBitmap = null;
        }

        public void setBitmap(final Bitmap imageBitmap) {
            this.imageBitmap = imageBitmap;
            uri = null;
        }

        public Bitmap getImageBitmap() {
            return imageBitmap;
        }
    }

    public static abstract class BazowyListItem {
        ListItemType item;

        public ListItemType getItem() {
            return item;
        }
    }

    public enum ListItemType {
        UNIWERSYTET, ZABYTEK
    }
}
