package Task3_Load;

import Task2_Save.GameProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        String pathToDestination = String.join(File.separator,"Games", "savegames") + File.separator;
        String pathToZip = pathToDestination + "save.zip";

        openZip(pathToZip, pathToDestination);
        openFiles(pathToDestination);
    }

    private static void openFiles(String pathToDestination) {
        // по заданию надо открыть один файл, сделаем все 3
        File dirSavegames = new File(pathToDestination);
        if (dirSavegames.isDirectory()) {
            for (File save : dirSavegames.listFiles()) {
                if (save.isDirectory()) {
                    continue;
                }
                String path = save.getPath();
                int i = path.lastIndexOf('.');
                if (i >= 0) {
                    String extension = path.substring(i + 1);
                    if (extension.equals("dat")) {
                        openProgress(path); // как требуется по заданию (хотя, есть файл, можно сразу передавать его, в принципе)
                    }
                }
            }
        }
    }

    private static void openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream  fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(gameProgress);
    }

    private static void openZip(String pathToZip, String pathToDestination) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = pathToDestination + entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
