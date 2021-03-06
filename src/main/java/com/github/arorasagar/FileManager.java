package com.github.arorasagar;

import java.io.*;
import java.util.BitSet;
import java.util.Optional;

/**
 * FileManager class is responsible for maintain the pieces of the file.
 */
public class FileManager {

    private boolean hasFile;
    private final PeerProcessConfig config;
    public static int pieceSize;
    public static long fileSize = 0;
    public static int totalPieces = 0;
    private FilePiece[] filePieces;
    int countPieces;
    private BitSet fileSet;

    public FileManager(PeerProcessConfig config, Peer peer) {
        this.config = config;
        pieceSize = config.getPieceSize();
        fileSize = config.getFileSize();
        totalPieces = (int) Math.ceil(fileSize / pieceSize);
        filePieces = new FilePiece[totalPieces];
        fileSet = new BitSet(totalPieces);
        if (peer.isHasFile()) {
            hasFile = true;
            filePieces = chunkFileIntoPieces(new File(config.getFileName()));
            fileSet = new BitSet(totalPieces);
            fileSet.set(0, totalPieces);
            countPieces = totalPieces;
        }
    }

    private File createTempFile(int index) throws IOException {
        File tempFile = File.createTempFile("temp_", String.valueOf(index));
        tempFile.deleteOnExit();

        return tempFile;
    }

    public FilePiece[] chunkFileIntoPieces(File file) {
        FilePiece[] filePieces = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {

            assert fileSize == file.length();
            filePieces = new FilePiece[totalPieces];
            byte[] buffer = new byte[pieceSize];
            int index = 0;
            for (int i = 0; i < totalPieces; i++) {
                int bytesRead = bufferedInputStream.read(buffer, i * pieceSize, (i + 1) * pieceSize);
                File tempFile = createTempFile(index);
                try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }
                //tempFile.deleteOnExit();
                filePieces[index] = new FilePiece(index, tempFile);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePieces;
    }

    public FilePiece[] getFilePieces() {
        return filePieces;
    }

    public FilePiece getFilePiece(int index) {
        return filePieces[index];
    }

    public byte[] getFilePieceBy(int index) throws IOException {
        FilePiece filePiece = getFilePiece(index);
        if (filePiece == null) {
            // log something is wrong/
            return null;
        }
        byte[] b = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePiece.getFile()))) {
            b = bufferedInputStream.readAllBytes();
        }
        return b;
    }

    public void updateFilePiece(int index, FilePiece filePiece) {
        filePieces[index] = filePiece;
        fileSet.set(index);
        countPieces++;
    }

    public void updateFilePieceFromByte(int index, byte[] bytesFilePiece) throws IOException {
        if (filePieces[index] != null) {
            // log that this piece has already been updated.
            return;
        }
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(createTempFile(index)))) {
            bufferedOutputStream.write(bytesFilePiece);
        }
        countPieces++;
    }

    public boolean isDownloadComplete() {
        return countPieces == totalPieces;
    }

    public int getPieceSize() {
        return pieceSize;
    }

    public int getTotalPieces() {
        return totalPieces;
    }

    public BitSet getBytesFromFilePieces() {
        return fileSet;
    }

    public Optional<BitSet> fileSet() {
        if (countPieces > 0) {
            return Optional.of(getBytesFromFilePieces());
        }
        return Optional.empty();
    }
}
