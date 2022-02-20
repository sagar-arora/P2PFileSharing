package com.github.arorasagar;

import java.io.*;

/**
 * FileManager class is responsible for maintain the pieces of the file.
 */
public class FileManager {

    private boolean hasFile;
    private final PeerProcessConfig config;
    private final int pieceSize;
    private long fileSize = 0;
    private int totalPieces = 0;
    private FilePiece[] filePieces;
    int countPieces;

    public FileManager(PeerProcessConfig config, Peer peer) {
        this.config = config;
        this.pieceSize = config.getPieceSize();
        this.totalPieces = (int) Math.ceil(fileSize / pieceSize);
        filePieces = new FilePiece[totalPieces];
        if (peer.isHasFile()) {
            hasFile = true;
            filePieces = chunkFileIntoPieces(new File(config.getFileName()));
        }
        this.fileSize = config.getFileSize();
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
                File tempFile = File.createTempFile("temp_",String.valueOf(index));
                try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }
                tempFile.deleteOnExit();
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

    public byte[] getFilePieceBy(int index) {
    /*    FileInputStream fileInputStream = new FileInputStream();
        return filePieces[index].;*/

        return null;
    }

    public void updateFilePiece(int index, FilePiece filePiece) {
        filePieces[index] = filePiece;
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
}
