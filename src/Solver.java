import java.io.File;
import java.util.Scanner;
import lib.*;

public class Solver {
    public static void driver(Scanner sc) {
        System.out.println("Masukkan nama file test case (dengan .txt): ");
        String fileName = sc.nextLine();

        while (fileName.isEmpty() || !fileName.contains(".txt")) {
            System.out.println("Masukkan nama file test case (dengan .txt): ");
            fileName = sc.nextLine();
        }

        File file = new File("test\\"+fileName);

        while(!file.exists()) {
            System.out.println("Tidak ada file test case pada folder test\nMasukkan nama file test case (dengan .txt): ");
            fileName = sc.nextLine();
            file = new File("test\\"+fileName);
        }

        PuzzlePiece p = new PuzzlePiece();
        Board b = new Board(0, 0, 0);
        p.setValid(true);
        b.setFileName(fileName);

        p.readFile(fileName,b);
        b.setPieces(p.pieces);
        // p.printPieces();

        System.out.println("\n===================\n");

        boolean solved = false;
        if (p.getValid() && b.getKasus().equals("DEFAULT")) {
            long startTime = System.nanoTime();
            solved = b.solve();
            long endTime = System.nanoTime();
            long duration = ((endTime-startTime)/1_000_000);
            b.setTime(duration);
        }

        if (solved) {
            System.out.println("Solusi berhasil ditemukan!");
        } else {
            System.out.println("Tidak ada solusi yang ditemukan");
            if (!b.getKasus().equals("DEFAULT")) {
                System.out.println("Kasus bukan DEFAULT");
            }
        }
        p.printPieces();
        b.printSolution();
        b.saveSolution(sc, fileName);
    }
}