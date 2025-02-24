import java.util.Scanner;

// javac -d bin -sourcepath src src/Main.java
// java -cp bin Main
public class Main {
    public static void main(String[] args) {
        int pilihan = 0;
        try (Scanner sc = new Scanner(System.in)) {
            while (pilihan != 2) {
                System.out.println("Menu:");
                System.out.println("1. Mulai IQ Puzzler Pro Solver");
                System.out.println("2. Keluar");
                System.out.print("Pilihan: ");
                pilihan = Integer.parseInt(sc.nextLine());
                // pilihan = sc.nextInt();
                // sc.nextLine();

                while (pilihan<1 || pilihan>2) {
                    System.out.println("Pilihan tidak valid");
                    System.out.print("Pilihan: ");
                    pilihan = Integer.parseInt(sc.nextLine());
                    // pilihan = sc.nextInt();
                    // sc.nextLine();
                }

                switch (pilihan) {
                    case 1 -> Solver.driver(sc);
                    case 2 -> System.out.println("Bye byee~");
                }
            }
        }
    }
}