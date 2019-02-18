package edu.stevens.cs522.chatserver.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Date;

import edu.stevens.cs522.base.DateUtils;

/**
 * Created by dduggan.
 */

public class MessageContract implements BaseColumns {

    public static final String MESSAGE_TEXT = "message_text";

    public static final String TIMESTAMP = "timestamp";

    public static final String SENDER = "sender";

    public static final String SENDER_ID = "sender_id";

    // TODO remaining columns in Messages table

    private static int messageTextColumn = -1;

    private static int idColumn = -1;

    private static int timeStampColumn = -1;

    private static int senderColumn = -1;

    private static int sender_IdColumn = -1;

    public static String getMessageText(Cursor cursor) {
        if (messageTextColumn < 0) {
            messageTextColumn = cursor.getColumnIndexOrThrow(MESSAGE_TEXT);
        }
        return cursor.getString(messageTextColumn);
    }

    public static void putMessageText(ContentValues out, String messageText) {
        out.put(MESSAGE_TEXT, messageText);
    }

    // TODO remaining getter and putter operations for other columns

    // Column_id
    public static Long getId(Cursor cursor) {
        if (idColumn < 0) {
            idColumn = cursor.getColumnIndexOrThrow(_ID);
        }
        return cursor.getLong(idColumn);
    }

    public static void putId(ContentValues out, Long id) {
        out.put(_ID, id);
    }
    // Date
    public static Date getTimestamp (Cursor cursor) {
        if (timeStampColumn < 0) {
            timeStampColumn = cursor.getColumnIndexOrThrow(TIMESTAMP);
        }
        return DateUtils.getDate(cursor, timeStampColumn);
    }

    public static void putTimestamp (ContentValues out, Date timeStamp) {
        DateUtils.putDate(out, TIMESTAMP, timeStamp);
    }
    // sender name
    public static String getSender(Cursor cursor) {
        if (sender_IdColumn < 0) {
            sender_IdColumn = cursor.getColumnIndexOrThrow(SENDER);
        }
        return cursor.getString(sender_IdColumn);
    }

    public static void putSender(ContentValues out, String sender) {
        out.put(SENDER, sender);
    }
    //sender id
    public static Long getSenderId(Cursor cursor) {
        if (sender_IdColumn < 0) {
            sender_IdColumn = cursor.getColumnIndexOrThrow(SENDER_ID);
        }
        return cursor.getLong(sender_IdColumn);
    }

    public static void putSenderId(ContentValues out, Long senderId) {
        out.put(SENDER_ID, senderId);
    }
}



