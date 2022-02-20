package com.github.arorasagar;

import java.io.File;

public final class FilePiece {

    private final int index;
    private final File file;

    public FilePiece(int index, File file) {
        this.index = index;
        this.file = file;
    }

    public int getIndex() {
        return index;
    }

    public File getFile() {
        return file;
    }
}
