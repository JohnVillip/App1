package com.example.villip.app1.dataprovider;

import android.net.Uri;

public class LinksContract {
    public static final String AUTHORITY =  "com.example.villip.app1.LinksContentProvider";
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);
    public static final String URL = "content://" + AUTHORITY + "/links";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private LinksContract() {}

    public static final class Links {

        private Links() {}

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/link";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/link";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "links");

        public static final String LINKS_TABLE = "linksTable";

        public static final String _ID = "_id";
        public static final String LINK = "link";
        public static final String STATUS = "status";
        public static final String TIME = "time";

        public static final String[] DEFAULT_PROJECTION = new String[] {
                Links._ID,
                Links.LINK,
                Links.STATUS,
                Links.TIME
        };
    }

}
