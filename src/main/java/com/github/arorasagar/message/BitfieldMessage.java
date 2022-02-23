package com.github.arorasagar.message;

import lombok.NonNull;

import java.util.BitSet;

public class BitfieldMessage extends Message {

    public BitfieldMessage(@NonNull BitSet payload) {
        super(payload.length(), MessageType.BITFIELD, payload.toByteArray());
    }
}
