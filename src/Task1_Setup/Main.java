package Task1_Setup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();

        boolean gamesExist = prepareDirGames();

        if(!gamesExist){
            System.out.println("Something wrong. Setup canceled.");
            return;
        }

        createDir(log, "Games//src");
        createDir(log, "Games//res");
        createDir(log, "Games//savegames");
        createDir(log, "Games//temp");

        createDir(log, "Games//src//main");
        createDir(log, "Games//src//test");

        createDir(log, "Games//res//drawables");
        createDir(log, "Games//res//vectors");
        createDir(log, "Games//res//icons");

        createEmptyFile(log, "Games//src//main//Main.java");
        createEmptyFile(log, "Games//src//main//Utils.java");

        createEmptyFile(log, "Games//temp//temp.txt");

        writeLog(log);
    }

    private static boolean prepareDirGames() {
        //Папка Games в корне проекта
        //но если нет...
        File dirGames = new File("Games");
        boolean gamesExist = dirGames.exists();
        if (!gamesExist){
            gamesExist = dirGames.mkdir();
        }
        return gamesExist;
    }

    private static void writeLog(StringBuilder log) {
        String text = log.toString();
        try (FileWriter writer = new FileWriter("Games//temp//temp.txt", false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void createEmptyFile(StringBuilder log, String path) {
        boolean result = false;
        File file = new File(path);
        try {
            result = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        appendLog(result, log, path, Types.File);
    }

    private static void createDir(StringBuilder log, String path) {
        boolean result;
        File dir = new File(path);
        result = dir.mkdir();
        appendLog(result, log, path, Types.Catalog);
    }

    private static void appendLog(boolean result, StringBuilder log, String name, Types type) {
        log.append(type);
        log.append(" ");
        log.append('"');
        log.append(name);
        log.append('"');
        log.append(" ");
        log.append("create");
        log.append(result ? " successful" : " unsuccessful");
        log.append('\n');
    }

}
