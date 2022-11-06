import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner();
        scanner.scan("D:\\Stuff\\faculta\\anu3-sem1\\FLCD\\lab\\project\\FLCD-lab\\P1.txt");
        try {
            PrintWriter writer = new PrintWriter("out.txt");
            writer.print(scanner.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(scanner);


    }
}
