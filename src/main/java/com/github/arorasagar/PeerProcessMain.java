package com.github.arorasagar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class PeerProcessMain {

    private static PeerProcessConfig peerProcessConfig;

    public PeerProcessMain(File config) throws InvalidFileException, IOException {
        peerProcessConfig = PeerProcessConfig.readFromJson(config);
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                throw new RuntimeException("empty");
            }

            int peerId = Integer.parseInt(args[0]);
            PeerProcessMain peerProcessMain = new PeerProcessMain(new File("config.json"));

            Peer peer = null;
            Collection<RemotePeerInfo> peersToConnectTo = new LinkedList<>();
            for (Peer currentPeer: peerProcessConfig.getPeers()) {
                if (currentPeer.getPeerId() == peerId) {
                    peer = currentPeer;
                    break;
                }
                peersToConnectTo.add((RemotePeerInfo) currentPeer);
            }

            assert peer != null;

            PeerProcessManager peerProcessManager = new PeerProcessManager(peerProcessConfig, peer, peersToConnectTo);
            peerProcessManager.start();
        } catch (InvalidFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
