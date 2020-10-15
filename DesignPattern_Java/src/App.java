import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        // createDirectory();
        SaveFile("//01 | 为什么说每个程序员都要尽早地学习并掌握设计模式相关知识？");
    }

    private static void createDirectory() throws IOException {

        for (int i = 1; i <= 1; i++) {
            String dirPath = "E:\\DesignPattern_Java\\DesignPattern_Java\\src\\oop\\oop_" + i;
            File dir = new File(dirPath);
            if (dir.exists())
                continue;
            boolean r = dir.mkdir();
            if (!r)
                throw new IllegalArgumentException("err");
            for (int j = 1; j < 5; j++) {
                File file = new File(dirPath + "\\oop_" + i + "_" + j + ".java");
                boolean r2 = file.createNewFile();
                if (!r2)
                    throw new IllegalArgumentException("err2");
            }
        }
    }

    private static void SaveFile(String title) throws IOException {
        int ii = 1;
        int jj = 1;
        String dirPath2 = "E:\\DesignPattern_Java\\DesignPattern_Java\\src\\oop\\oop_" + ii;
        File file2 = new File(dirPath2 + "\\oop_" + ii + "_" + jj + ".java");

        BufferedWriter out = new BufferedWriter(new FileWriter(file2));
        out.write(title);
        out.close();
    }
}
