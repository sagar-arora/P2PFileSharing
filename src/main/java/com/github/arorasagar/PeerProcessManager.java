package com.github.arorasagar;

import com.github.arorasagar.message.HandshakeMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class PeerProcessManager extends Thread {

    PeerProcessConfig peerProcessConfig;
    Peer peer;
    private final Collection<RemotePeerInfo> remotePeerInfos;
    private volatile boolean notStopped = true;
    ConcurrentHashMap<PeerConnection, Boolean> connectionHandlerMap;
    FileManager fileManager;
    ConnectionManager connectionManager;
    Collection<PeerConnection> peerConnections = new LinkedList<>();

    public PeerProcessManager(PeerProcessConfig peerProcessConfig, Peer peer, Collection<RemotePeerInfo> remotePeerInfos) {
        this.peerProcessConfig = peerProcessConfig;
        this.peer = peer;
        this.remotePeerInfos = remotePeerInfos;
        this.fileManager = new FileManager(peerProcessConfig, peer);
        this.connectionManager = new ConnectionManager(peerProcessConfig, peerConnections);
    }

    public void connectToOtherPeers() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    /**
     * this will listen to upcoming connections. As soon as new connection comes, send them the handshake message.
     * add the peer to peersCollection.
     *
     * @throws IOException
     */
    public void startServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket(peer.getPort());
        while(notStopped) {
            Socket socket = serverSocket.accept();
            PeerConnection peerConnection = new PeerConnection(socket, -1);
            peerConnection.sendMessage(new HandshakeMessage(peer.getPeerId()));
            connectionHandlerMap.putIfAbsent(peerConnection, true);
        }
    }

    public void start() {
        connectToOtherPeers();
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
