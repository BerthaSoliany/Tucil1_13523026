package lib;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class PuzzlePiece {
    int id, row, col;
    boolean valid;
    int[][] shape;
    public PuzzlePiece[] pieces;

    public PuzzlePiece() {
        this.pieces = null;
    }

    public PuzzlePiece(int Id, int r, int c, int[][] newShape) {
        id = Id;
        row = r;
        col = c;
        shape = new int[row][col];
        for (int i=0;i<row;i++) {
            System.arraycopy(newShape[i], 0, shape[i], 0, newShape[i].length);
        }
        valid = true;
    }

    public int getId() {
        return id;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int[][] getShape() {
        return shape;
    }
    public boolean getValid() {
        return valid;
    }

    public void setId(int Id) {
        id = Id;
    }
    public void setRow(int r) {
        row = r;
    }
    public void setCol(int c) {
        col = c;
    }
    public void setShape(int[][] newShape, int r, int c) {
        shape = new int[r][c];
        for (int i=0;i<r;i++) {
            System.arraycopy(newShape[i], 0, shape[i], 0, newShape[i].length);
        }
    }
    public void setValid(boolean v) {
        valid = v;
    }

    private int[][] rotate(int[][] shapes) {
        int r = shapes.length;
        int c = shapes[0].length;
        int[][] rotated = new int[c][r];
        for (int i=0;i<r;i++) {
            for (int j=0;j<c;j++) {
                rotated[j][r-1-i] = shapes[i][j];
            }
        }
        return rotated;
    }
    
    private int[][] reflect(int[][] shapes) {
        int r = shapes.length;
        int c = shapes[0].length;
        int[][] flipped = new int[r][c];
        for (int i=0;i<r;i++) {
            for (int j=0;j<c;j++) {
                flipped[i][c-1-j] = shapes[i][j];
            }
        }
        return flipped;
    }
    
    public void printPiece() {
        System.out.println("Piece " + (char) id);
        for (int i=0;i<row;i++) {
            for (int j=0;j<col;j++) {
                if (shape[i][j]==0) {
                    System.out.print(". ");
                } else {
                    System.out.print(Color.colorPrint((char) shape[i][j])+" ");
                }
            }
            System.out.println();
        }
    }

    public void printPieces() {
        if (pieces == null) {
            System.out.println("Belum ada puzzle yang terbaca.");
            return;
        }

        for (int i=0;i<pieces.length;i++) {
            PuzzlePiece piece = pieces[i];
            if (piece != null) {
                piece.printPiece();
            }
        }
    }

    public PuzzlePiece[] getPieces() {
        PuzzlePiece[] newPieces = new PuzzlePiece[8];
        int count = 0;
        int[][] shape1 = shape;
        int[][] shape2 = reflect(shape);

        for (int i=0;i<4;i++) {
            newPieces[count++] = new PuzzlePiece(id, shape1.length, shape1[0].length, shape1);
            shape1 = rotate(shape1);
        }
        for (int i=0;i<4;i++) {
            newPieces[count++] = new PuzzlePiece(id, shape2.length, shape2[0].length, shape2);
            shape2 = rotate(shape2);
        }
        return newPieces;
    }   

    // public int countPiece() {
    //     int count = 0;
    //     if (pieces != null) {
    //         for (int i=0;i<pieces.length;i++) {
    //             count ++;
    //         }
    //     }
    //     // System.out.println("Jumlah piece: "+count);
    //     return count;
    // }

    public void readFile(String fileName, Board b) {
        fileName = "test\\" + fileName;
        try {
            int N,M,P;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String firsString = br.readLine();
            StringTokenizer st = new StringTokenizer(firsString);
    
            if (firsString==null||firsString.trim().isEmpty()||st.countTokens()<3||st.countTokens()>3) {
                br.close();
                setValid(false);
                throw new IOException("Format file N M P tidak valid");
            }

            try {
                N = Integer.parseInt(st.nextToken());
                M = Integer.parseInt(st.nextToken());
                P = Integer.parseInt(st.nextToken());
            } catch (NumberFormatException e) {
                br.close();
                setValid(false);
                throw new IOException("Format salah: N, M, dan P harus berupa angka.");
            }
            System.out.println("N:"+N+" M:"+M+" P:"+P);

            if (N<=0||M<=0){
                br.close();
                setValid(false);
                throw new IOException("Dimensi papan tidak valid.");
            }
    
            String kasus = br.readLine();
            System.out.println("Kasus:"+kasus);
            
            // set board
            b.setHeights(N);
            b.setWidths(M);
            b.setPieceCount(P);
            b.setKasus(kasus);

            // cek baris file
            int max = (P+N+2)*10; 
            String[] lines = new String[max];
            int count = 0;
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    lines[count++] = " ";
                } else {
                    lines[count++] = line;
                }
            }

            String[] fixLines = new String[count];
            System.arraycopy(lines, 0, fixLines, 0, count);
            lines = fixLines;

            int r = lines.length;
            int c = 0;
            for (int i = 0; i < r; i++) {
                if (lines[i].length() > c) {
                    c = lines[i].length();
                }
            }

            // menemukan banyak pieces
            boolean[] foundChars = new boolean[256];
            int idCount = 0;
            for (int i=0; i<r;i++) {
                for (int j=0;j<lines[i].length();j++) {
                    char huruf = lines[i].charAt(j);
                    if (huruf>='A' && huruf<='Z' && !foundChars[huruf]) {
                        foundChars[huruf] = true;
                        idCount++;
                    }
                }
            }

            char[] id = new char[idCount];
            int index = 0;
            for (int i=0;i<256;i++) {
                if (foundChars[i]) {
                    id[index++] = (char) i;
                }
            }

            // inisialiasi untuk semua pieces
            pieces = new PuzzlePiece[idCount];

            for (int i=0;i<idCount;i++) {
                char huruf = id[i];
                int[][] shape = new int[r][c];

                for (int j=0;j<r;j++) {
                    char[] chars = lines[j].toCharArray();
                    for (int k=0;k<chars.length;k++) {
                        if (chars[k] == huruf) {
                            shape[j][k] = huruf;
                        }
                    }
                }

                // hapus row/col yg 0 semua
                int idxAwal = 0, idxAkhir = r-1;
                int idxKiri = 0, idxKanan = c-1;

                while (idxAwal<r&& isRowZero(shape[idxAwal])) idxAwal++;
                while (idxAkhir>=0 && isRowZero(shape[idxAkhir])) idxAkhir--;
                while (idxKiri<c && isColumnZero(shape, idxKiri, idxAwal, idxAkhir)) idxKiri++;
                while (idxKanan>=0 && isColumnZero(shape, idxKanan, idxAwal, idxAkhir)) idxKanan--;

                if (idxAwal>idxAkhir||idxKiri>idxKanan) {
                    pieces[i] = new PuzzlePiece(huruf, 0, 0, new int[0][0]);
                    continue;
                }

                int[][] newShape = new int[idxAkhir-idxAwal+1][idxKanan-idxKiri+1];
                for (int j = idxAwal; j <= idxAkhir; j++) {
                    System.arraycopy(shape[j], idxKiri, newShape[j-idxAwal], 0, newShape[0].length);
                }
                // simpan shape ke public pieces
                pieces[i] = new PuzzlePiece(huruf, newShape.length, newShape[0].length, newShape);
            }

            br.close();

            if (pieces.length != P) {
                setValid(false);
                throw new IOException("Jumlah blok puzzle tidak sesuai dengan P.");
            }
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan dalam membaca file.");
            setValid(false);
            e.printStackTrace();
        }
    }

    private boolean isRowZero(int[] row) {
        for (int i=0;i<row.length;i++) {
            int num = row[i];
            if (num!=0) {
                return false;
            }
        }
        return true;
    }

    private boolean isColumnZero(int[][] shape, int c, int idxAwal, int idxAkhir) {
        for (int i=idxAwal;i<=idxAkhir;i++) {
            if (shape[i][c]!=0) {
                return false;
            }
        }
        return true;
    }

    // public PuzzlePiece getPieceByChar(char label) {
    //     int targetId = (int) label;
    //     // boolean found = findPiecebyChar(label);
    //     // if (found) {
    //         for (int i=0;i<pieces.length;i++) {
    //             PuzzlePiece piece = pieces[i];
    //             if (piece.id == targetId) {
    //                 return piece;
    //             }
    //         }
    //     // }
    //     return null;
    // }

    // public boolean findPiecebyChar(char label) {
    //     int targetId = (int) label;
    //     boolean found = false;
    //     for (int i=0;i<pieces.length;i++) {
    //         PuzzlePiece piece = pieces[i];
    //         if (piece.id == targetId) {
    //             found = true;
    //         }
    //     }
    //     return found;
    // }

}