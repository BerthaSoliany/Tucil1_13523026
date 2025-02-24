package lib;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Board {
    int rows, cols, pieceCount, attempts;
    long time;
    int[][] elmnts;
    String kasus, fileName;
    PuzzlePiece[] pieces;
    int Idx = 0;

    public Board(int r, int c, int p) {
        rows = r;
        cols = c;
        pieceCount = p;
        elmnts = new int[rows][cols];
        time = 0;
        attempts = 0;
        kasus = "";
        fileName = "";
    }

    public int getHeights() {
        return rows;
    }
    public int getWidths() {
        return cols;
    }
    public long getTime() {
        return time;
    }
    public int getAttempts() {
        return attempts;
    }
    public int getElement(int row, int col) {
        return elmnts[row][col];
    }
    public String getKasus() {
        return kasus;
    }
    public String getFileName() {
        return fileName;
    }
    public int getPieceCount() {
        return pieceCount;
    }

    public void setAttempts(int count) {
        attempts = count;
    }
    public void setElement(int row, int col, int value) {
        elmnts[row][col] = value;
    }
    public void setHeights(int r) {
        rows = r;
        elmnts = new int[rows][cols];
    }
    public void setWidths(int c) {
        cols = c;
        elmnts = new int[rows][cols];
    }
    public void setTime(long t) {
        time = t;
    }
    public void setKasus(String k) {
        kasus = k;
    }
    public void setFileName(String f) {
        fileName = f;
    }
    public void setPieceCount(int p) {
        pieceCount = p;
    }
    public void setPieces(PuzzlePiece[] pieces) {
        this.pieces = pieces;
    }

    public void printBoard() {
        System.out.println("Dimensi papan: "+getHeights()+"x"+getWidths());
        System.out.println("Jumlah blok puzzle: "+getPieceCount());

        for (int i=0;i<getHeights();i++) {
            for (int j=0;j<getWidths();j++) {
                if (getElement(i, j)!=0) {
                    System.out.print(Color.colorPrint((char) getElement(i, j))+" ");                    
                } else {
                    System.out.print(getElement(i,j)+" ");
                }
            }
            System.out.println();
        }
    }

    public void printSolution() {
        printBoard();
        System.out.println("Waktu pencarian: "+getTime()+"ms\n");
        System.out.println("Banyak kasus yang ditinjau: "+getAttempts()+"\n");
    }

    public void createNewFile(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            System.out.println("File "+fileName+" sudah ada. Overwrite file");
        } else {
            try {
                if (file.createNewFile()) {
                    System.out.println("File baru berhasil dibuat: "+fileName);
                } else {
                    System.out.println("Gagal membuat file");
                }
            } catch (IOException e) {
                System.out.println("Error making file");
                e.printStackTrace();
            }
        }
    }

    public void writeFile(String fileName) {
        try {
            FileWriter solutionFile = new FileWriter(fileName);
            solutionFile.write("Solusi "+getFileName()+"\n");
            // if (v){
            //     solutionFile.write("Solusi berhasil ditemukan\n");
            // } else {
            //     solutionFile.write("Tidak ada solusi\n");
            // }
            solutionFile.write("");
            for (int i=0;i<getHeights();i++) {
                for (int j=0;j<getWidths();j++) {
                    if (getElement(i, j)!=0) {
                        solutionFile.write((char) getElement(i, j)+" ");                    
                    } else {
                        solutionFile.write(getElement(i,j)+" ");
                    }
                }
                solutionFile.write("\n");
            }
            solutionFile.write("Waktu pencarian: "+getTime()+"ms\n");
            solutionFile.write("Banyak kasus yang ditinjau: "+getAttempts()+"\n");
            solutionFile.close();
            System.out.println("Solusi berhasil di simpan pada "+fileName);
        } catch (IOException e) {
            System.out.println("Error writing file");
            e.printStackTrace();
        }
    }

    public void saveSolution(Scanner sc, String fileName) {
        fileName = "test\\" +"solution_"+ fileName;
        System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
        String answer = sc.nextLine();

        if (answer.equals("ya")||answer.isEmpty()) {
            createNewFile(fileName);
            writeFile(fileName);
        } else {
            System.out.println("Solusi untuk "+fileName+" tidak disimpan");
        }
    }

    public boolean isFull(int[][] b) {
        for (int i=0;i<rows;i++) {
            for (int j=0;j<cols;j++) {
                if (b[i][j]==0) {
                    return false;
                }
            }
        }
        return true;
    }

    // mencari urutan piece akan ditempatkan dengan konsep permutasi
    public int factorial(int n) {
        int res = 1;
        for (int i=2;i<=n;i++) {
            res*=i;
        }
        return res;
    }

    public PuzzlePiece[][] setOrderPieces(PuzzlePiece[] pieces) {
        int total = factorial(pieces.length);
        PuzzlePiece[][] newPieces = new PuzzlePiece[total][pieces.length];
        
        Idx = 0;
        order(pieces, 0, newPieces);
        
        return newPieces;
    }

    public void order(PuzzlePiece[] arr, int k, PuzzlePiece[][] res) {
        if (k == arr.length) {
            res[Idx] = arr.clone();
            Idx++;
        } else {
            for (int i=k;i<arr.length;i++) {
                swap(arr, i, k);
                order(arr, k + 1, res);
                swap(arr, i, k);
            }
        }
    }

    public void swap(PuzzlePiece[] arr, int i, int j) {
        PuzzlePiece temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public boolean canSetPiece(int[][] b, int[][] shape, int r, int c) {
        for (int i=0;i<shape.length;i++) {
            for (int j=0;j<shape[i].length;j++) {
                if (shape[i][j]!=0 && b[r+i][c+j]!=0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setPiece(int[][] b, int[][] shape, int r, int c, int id) {
        for (int i=0;i<shape.length;i++) {
            for (int j=0;j<shape[i].length;j++) {
                if (shape[i][j]!=0) {
                    b[r+i][c+j]=id;
                }
            }
        }
    }
    
    public boolean solve() {
        PuzzlePiece[][] orders = setOrderPieces(pieces);

        for (int i=0;i<orders.length;i++) {
            PuzzlePiece[] order = orders[i];
            int[][] copy = new int[rows][cols];
            
            if (aBF(copy, order)) {
                elmnts = copy;
                return true;
            }
        }
        return false;
    }

    // inti algoritma brute force
    public boolean aBF(int[][] b, PuzzlePiece[] order) {
        for (int n=0;n<order.length;n++) { 
            PuzzlePiece piece = order[n];
            boolean placed = false; 
            PuzzlePiece[] rotations = piece.getPieces();

            for (int i = 0; i < rotations.length; i++) {
                PuzzlePiece rotated = rotations[i]; 
                for (int j = 0; j <= rows - rotated.shape.length; j++) {
                    for (int k = 0; k <= cols - rotated.shape[0].length; k++) {
                        attempts++;
                        if (canSetPiece(b, rotated.shape, j, k)) {
                            setPiece(b, rotated.shape, j, k, piece.id);
                            placed = true;
                            break;
                        }
                    }
                    if (placed) break;
                }
                if (placed) break;
            }

            if (!placed) return false;
        }
        return isFull(b);
    }

}