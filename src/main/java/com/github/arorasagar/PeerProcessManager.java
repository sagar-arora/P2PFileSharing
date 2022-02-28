package com.github.arorasagar;

import com.github.arorasagar.message.HandshakeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PeerProcessManager extends Thread {

    private final PeerProcessConfig peerProcessConfig;
    private final Peer peer;
    private final Collection<RemotePeerInfo> remotePeersAlreadyRunning;
    private volatile boolean running = true;
    ConcurrentHashMap<PeerConnection, Boolean> connectionHandlerMap;
    private final FileManager fileManager;
    private final ConnectionManager connectionManager;
    List<PeerConnection> peerConnections = Collections.synchronizedList(new ArrayList<>());

    private static final Logger LOGGER = LoggerFactory.getLogger(PeerProcessManager.class);

    public PeerProcessManager(PeerProcessConfig peerProcessConfig,
                              Peer peer,
                              Collection<RemotePeerInfo> remotePeersAlreadyRunning) {
        this.peerProcessConfig = peerProcessConfig;
        this.peer = peer;
        this.remotePeersAlreadyRunning = remotePeersAlreadyRunning;
        this.fileManager = new FileManager(peerProcessConfig, peer);
        this.connectionManager = new ConnectionManager(peerProcessConfig, peerConnections, fileManager, peer);
    }

    public void connectToOtherPeers() {

        LOGGER.info("[{}] Starting thread to previously running peers.", peer.getPeerId());

        new Thread(new Runnable() {
            @Override
            public void run() {

                LinkedList<RemotePeerInfo> remotePeerInfoList = new LinkedList<>(remotePeersAlreadyRunning);
                while (remotePeerInfoList.size() > 0) {
                    RemotePeerInfo remotePeerInfo = remotePeerInfoList.poll();
                    Socket socket = null;
                    try {
                         socket = new Socket(remotePeerInfo.address, remotePeerInfo.port);
                    } catch (IOException e) {
                        LOGGER.error("[{}] Error occurred while creating socket connection : {}", peer.peerId, e);
                    }

                    if (socket != null) {
                        try {
                            LOGGER.error("[{}] Successfully created the socket with remote peer: {}:{}",
                                    peer.peerId, remotePeerInfo.peerId, remotePeerInfo.port);
                            connectionHandlerMap.putIfAbsent(new PeerConnection(socket), true);
                        } catch (Exception e) {
                            LOGGER.error("[{}] Error occurred while creating socket connection : {}", peer.peerId, e);
                            remotePeerInfoList.offer(remotePeerInfo);
                        }
                    } else {
                        // keep trying
                        remotePeerInfoList.offer(remotePeerInfo);
                    }
                }
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
        LOGGER.info("Starting the server on the port : {}", peer.getPort());
        ServerSocket serverSocket = new ServerSocket(peer.getPort());
        while (running) {
            Socket socket = serverSocket.accept();
            PeerConnection peerConnection = new PeerConnection(socket, -1);
            peerConnection.sendMessage(new HandshakeMessage(peer.getPeerId()));
            connectionHandlerMap.putIfAbsent(peerConnection, true);
            peerConnections.add(peerConnection);
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
