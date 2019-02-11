package edu.stevens.cs522.chatserver.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.InetAddress;
import java.util.Date;

import edu.stevens.cs522.base.DateUtils;
import edu.stevens.cs522.base.InetAddressUtils;

/**
 * Created by dduggan.
 */

public class Peer implements Parcelable {

    // Will be database key
    public long id;

    public String name;

    // Last time we heard from this peer.
    public Date timestamp;

    // Where we heard from them
    public InetAddress address;

    public int port;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(name);
        DateUtils.writeDate(out, timestamp);
        InetAddressUtils.writeAddress(out, address);
        out.writeInt(port);
    }

    public Peer(Parcel in) {

        this.id = in.readLong();
        this.name = in.readString();
        this.timestamp = DateUtils.readDate(in);
        this.address = InetAddressUtils.readAddress(in);
        this.port = in.readInt();

    }

    public Peer( String name, Date timestamp, InetAddress address, int port) {
        //this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.address = address;
        this.port = port;
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
