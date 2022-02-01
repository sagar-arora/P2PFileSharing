package com.github.arorasagar;

import java.io.File;
import java.io.IOException;

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
            for (Peer currentPeer: peerProcessConfig.getPeers()) {
                if (currentPeer.getPeerId() == peerId) {
                    peer = currentPeer;
                    break;
                }
            }

            assert peer != null;

            PeerProcessManager peerProcessManager = new PeerProcessManager(peerProcessConfig, peer);
            peerProcessManager.start();
        } catch (InvalidFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
