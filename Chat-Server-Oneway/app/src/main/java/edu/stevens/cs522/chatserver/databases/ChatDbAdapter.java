package edu.stevens.cs522.chatserver.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.stevens.cs522.chatserver.contracts.MessageContract;
import edu.stevens.cs522.chatserver.contracts.PeerContract;
import edu.stevens.cs522.chatserver.entities.Message;
import edu.stevens.cs522.chatserver.entities.Peer;

/**
 * Created by dduggan.
 */

public class ChatDbAdapter {

    private static final String DATABASE_NAME = "messages.db";

    private static final String MESSAGE_TABLE = "messages";

    private static final String PEER_TABLE = "peers";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE = null; // TODO

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATING PEER TABLE
            db.execSQL("CREATE TABLE " + PEER_TABLE
                    + "(" + PeerContract._ID + " INTEGER PRIMARY KEY , "
                    + PeerContract.NAME + " TEXT NOT NULL, "
                    + PeerContract.ADDRESS + " TEXT NOT NULL, "
                    + PeerContract.TIMESTAMP + " INTEGER NOT NULL)");

            // CREATEING MESSAGE TABLE
            db.execSQL("CREATE TABLE " + MESSAGE_TABLE
             + "(" + MessageContract._ID + " INTEGER PRIMARY KEY , "
             + MessageContract.MESSAGE_TEXT + " TEXT NOT NULL, "
             + MessageContract.TIMESTAMP + " INTEGER NOT NULL, "
             + MessageContract.SENDER + " TEXT NOT NULL, "
             + MessageContract.SENDER_ID + " INTEGER NOT NULL, "
             + "FOREIGN KEY (" + MessageContract.SENDER_ID + ") REFERENCES " + PEER_TABLE + "(" + PeerContract._ID + ")  ON DELETE CASCADE"
             + ")");

            db.execSQL("CREATE INDEX MessagesPeerIndex ON " + MESSAGE_TABLE + " (" + PeerContract._ID +")");
            db.execSQL("CREATE INDEX PeerNameIndex ON " + PEER_TABLE + "(" + PeerContract.NAME + ")");


            Log.i("OnCreateDatabase", "Created");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + PEER_TABLE);
            onCreate(db);
        }
    }


    public ChatDbAdapter(Context _context) {
        dbHelper = new DatabaseHelper(_context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        // TODO
        db = dbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    public Cursor fetchAllMessages() {
        // TODO
        return db.rawQuery("SELECT * FROM " + MESSAGE_TABLE, null);
    }

    public Cursor fetchAllPeers() {
        // TODO
        Cursor cursor =  db.rawQuery("SELECT * FROM " + PEER_TABLE, null);
        if (cursor != null) {
            return cursor;
        }
        return null;
    }

    public Peer fetchPeer(long peerId) {
        // TODO
        /* select *
         * from PEER_TABLE
         *  where PEER_ID = peerId */
        String selection = PeerContract._ID + "=" + peerId;
        String[] columns = new String[]{"*"};
        Cursor cursor = db.query(PEER_TABLE, columns, selection,  null, null, null, null);
        if (cursor.moveToFirst()) {
            Peer peer = new Peer(cursor);
            return peer;
        }
        return null;
    }

    public Cursor fetchMessagesFromPeer(Peer peer) {
        // TODO
        /*
        * SELECT *
        * FROM MESSAGE_TABLE
        * WHERE SENDER_ID =  peer.id*/
        String selection = MessageContract.SENDER_ID + " = " + peer.id;
        String[] columns = new String[]{"*"};
        Cursor cursor = db.query(MESSAGE_TABLE, columns, selection, null, null, null, null);
        return cursor;
    }

    public long persist(Message message) throws SQLException {
        // TODO

        Log.i("senderID:  ", Long.toString(message.senderId));
        ContentValues messageContent = new ContentValues();
        message.writeToProvider(messageContent);
        return db.insert(MESSAGE_TABLE, null, messageContent);

    }

    /**
     * Add a peer record if it does not already exist; update information if it is already defined.
     */
    public long persist(Peer peer) throws SQLException {
        // TODO
        Long peerId = 0L;
        ContentValues peerContent = new ContentValues();
        peer.writeToProvider(peerContent);
        String SQLquery = "SELECT * FROM " + PEER_TABLE + " WHERE " + PeerContract.NAME + " =?";
        String[] args = new String[]{peer.name};
        Cursor cursor = db.rawQuery(SQLquery, args);
        if (cursor == null || !cursor.moveToFirst()) {
            // if not exist, insert a new row
            peerId = db.insert(PEER_TABLE, null, peerContent);
            peer.id = peerId;
            peer.writeToProvider(peerContent);
            Log.i("Inserted", peer.name + " inserted with id: " + peer.id );
        }
        else {
            peer.id = PeerContract.getId(cursor);
            String where = PeerContract.NAME + " =? ";
            String[] whereArgs = new String[]{peer.name};

            int num = db.update(PEER_TABLE, peerContent, where, whereArgs);

            Log.i("UPDATING PEER", "Number of rows affected: " + num + "Already inserted peer id: " + peer.id);
        }
        return peer.id;
    }

    public void close() {
        // TODO
        db.close();
    }
}