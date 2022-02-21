package com.github.arorasagar;

import java.util.Iterator;

public class FileBytesIterator implements Iterator<Byte> {

    byte[] bytes;
    int index = 0;
    public FileBytesIterator(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean hasNext() {
        return index == bytes.length;
    }

    @Override
    public Byte next() {
        if (index >= bytes.length) {
            return null;
        }
        return bytes[index++];
    }

}

