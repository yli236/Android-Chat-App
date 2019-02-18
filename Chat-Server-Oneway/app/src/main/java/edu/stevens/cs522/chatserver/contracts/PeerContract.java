package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;

/**
 * Created by dduggan.
 */

public class PeerContract implements BaseColumns {

    // TODO define column names, getters for cursors, setters for contentvalues
    public static final String NAME = "name";

    public static final String ADDRESS = "address";

    public static final String TIMESTAMP = "timestamp";

    public static int idColumn = -1;

    public static int nameColumn = -1;

    public static int addressColumn = -1;

    public static int timeStampColumn = -1;

    public static Long getId(Cursor cursor) {
        if (idColumn < 0) {
            idColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getLong(idColumn);
    }

    public static void putId(ContentValues out, Long id) {
        out.put(_ID, id);
    }

    public static String getName(Cursor cursor) {
        if (nameColumn < 0) {
            nameColumn = cursor.getColumnIndexOrThrow(NAME);
        }
        return cursor.getString(nameColumn);
    }

    public static void putName(ContentValues out, String name) {
        out.put(NAME, name);
    }

    public static InetAddress getAddress(Cursor cursor) {
        if (addressColumn < 0) {
            addressColumn = cursor.getColumnIndexOrThrow(ADDRESS);
        }
        return InetAddressUtils.getAddress(cursor, addressColumn);
    }

    public static void putAddress(ContentValues out, InetAddress address) {
        InetAddressUtils.putAddress(out, ADDRESS, address);
    }

    public static Date getTimestamp(Cursor cursor) {
        if (timeStampColumn < 0) {
            timeStampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return DateUtils.getDate(cursor, timeStampColumn);
    }

    public static void putTimestamp(ContentValues out, Date timestamp) {
        DateUtils.putDate(out, TIMESTAMP, timestamp);
    }
}
