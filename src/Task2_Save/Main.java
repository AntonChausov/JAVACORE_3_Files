package Task2_Save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        GameProgress[] gameProgress = {new GameProgress(1, 2, 3, 456),
                                        new GameProgress(9, 8, 7, 654),
                                        new GameProgress(6, 6, 6, 666)};
        String[] paths = new String[gameProgress.length];
        String pathToSaves = String.join(File.separator, "Games", "savegames") + File.separator;
        String path;
        String pathToZip = pathToSaves + "save.zip";
        int count = 0;

        for (GameProgress gp : gameProgress) {
            path = String.join("",pathToSaves, "save", Integer.toString(++count), ".dat");
            saveGame(gp, path);
            paths[count - 1] = path; //Как-то не изящно, но хочется, чтобы файлы начинались с "1", а вторую переменную добавлять не хочется
        }

        zipFiles(paths, pathToZip);
    }

    private static void zipFiles(String[] paths, String pathToZip) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(new File(pathToZip)))) {
            for (String pathToFile : paths) {
                int index = pathToFile.lastIndexOf(File.separator);
                String name = pathToFile.substring(index + 1);

                FileInputStream fis = new FileInputStream(pathToFile);

                ZipEntry entry = new ZipEntry(name);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        deleteFiles(paths);
    }

    private static void deleteFiles(String[] paths) {
        for (String pathToFile : paths) {
            File file = new File(pathToFile);
            if (file.exists()){
                file.delete();
            }
        }
    }

    private static void saveGame(GameProgress gp, String path) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
