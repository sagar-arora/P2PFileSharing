package com.github.arorasagar;

import com.github.arorasagar.message.*;

import java.util.Optional;

public interface PeerToPeerProtocol {

    Optional<BitfieldMessage> handshakeMessage(HandshakeMessage handshakeMessage);

    InterestedMessage bitfieldMessage(BitfieldMessage bitfieldMessage);

    // when I receive the have message, just update that peer has
    // the content of the file.
    void haveMessage(HaveMessage haveMessage);

    Optional<FieldMessage> requestedMessage(RequestMessage requestMessage);

    void unchokeMessage(UnchokeMessage unchokeMessage);
}
