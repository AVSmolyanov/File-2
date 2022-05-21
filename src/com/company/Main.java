package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static List<String> objs = new ArrayList<>();

    static void saveGame(String path, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(path, false)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(progress);
                oos.close();
                objs.add(path);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            fos.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void zipFiles(String path, List<String> objs) {
        try (FileOutputStream fos = new FileOutputStream(path, false)) {
            try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                for (String name : objs) {
                    zos.putNextEntry(new ZipEntry(name));
                    try (FileInputStream fis = new FileInputStream(name)) {
                        byte[] buffer = new byte[fis.available()];
                        fis.read(buffer);
                        zos.write(buffer);
                        zos.closeEntry();
                        fis.close();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                zos.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            fos.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static void delFiles(List<String> objs) {
        for (String name : objs) {
            File file = new File(name);
            if (file.delete() == false) {
                System.out.println("Ошибка удаления файла " + name);
            }
        }
    }

    public static void main(String[] args) {
        // write your code here
        saveGame("F:/TEST/Games/savegames/save1.dat", new GameProgress(10, 5, 1, 1.1));
        saveGame("F:/TEST/Games/savegames/save2.dat", new GameProgress(10, 5, 1, 1.1));
        saveGame("F:/TEST/Games/savegames/save3.dat", new GameProgress(10, 5, 1, 1.1));
        zipFiles("F:/TEST/Games/savegames/save.zip", objs);
        delFiles(objs);
    }
}
