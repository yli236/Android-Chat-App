package edu.stevens.cs522.chatserver.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;
import edu.stevens.cs522.chatserver.contracts.PeerContract;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable, Persistable {

    // Will be database key
    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;

    public Peer() {
    }

    public Peer(Cursor cursor) {
        this.id = PeerContract.getId(cursor);
        this.name = PeerContract.getName(cursor);
        this.timestamp = PeerContract.getTimestamp(cursor);
        this.address = PeerContract.getAddress(cursor);
    }

    public Peer(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.timestamp = DateUtils.readDate(in);
        this.address = InetAddressUtils.readAddress(in);
    }

    @Override
    public void writeToProvider(ContentValues out) {
        // TODO
        //PeerContract.putId(out, this.id);
        PeerContract.putAddress(out, this.address);
        PeerContract.putName(out, this.name);
        PeerContract.putTimestamp(out, this.timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(this.id);
        out.writeString(this.name);
        DateUtils.writeDate(out, this.timestamp);
        InetAddressUtils.writeAddress(out, this.address);
    }

    public static final Creator<Peer> CREATOR = new Creator<Peer>() {

        @Override
        public Peer createFromParcel(Parcel source) {
            return new Peer(source);
        }

        @Override
        public Peer[] newArray(int size) {
            return new Peer[size];
        }

    };
}
