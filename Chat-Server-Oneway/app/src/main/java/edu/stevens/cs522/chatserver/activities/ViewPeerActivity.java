package edu.stevens.cs522.chatserver.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import edu.stevens.cs522.chatserver.R;
import edu.stevens.cs522.chatserver.databases.ChatDbAdapter;
import edu.stevens.cs522.chatserver.entities.Peer;

/**
 * Created by dduggan.
 */

public class ViewPeerActivity extends Activity {

    public static final String PEER_ID_KEY = "peer-id";

    private ChatDbAdapter chatDbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_peer);

        long peerId = getIntent().getLongExtra(PEER_ID_KEY, -1);
        if (peerId < 0) {
            throw new IllegalArgumentException("Expected peer id as intent extra");
        }

        // TODO init the UI
        chatDbAdapter = new ChatDbAdapter(this);
        chatDbAdapter.open();
        Log.i("ID", "peer id is: " + peerId);
        Peer peer = chatDbAdapter.fetchPeer(peerId);
        Log.i("name", "peer name is: " + peer.name);

        TextView username = (TextView) findViewById(R.id.view_user_name);
        username.setText("Hello");
        TextView timestamp = (TextView) findViewById(R.id.view_timestamp);
        Log.i("name", "peer timestamp is: " + peer.timestamp);

        timestamp.setText(peer.timestamp.toString());
        TextView address = (TextView) findViewById(R.id.view_address);
        address.setText(peer.address.toString());

        chatDbAdapter.close();
    }

}
