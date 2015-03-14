package com.davidvaz.bootxperia.flashutils;

import android.os.Environment;

import java.io.File;

/**
 * Created by davidvaz on 23/1/14.
 */
public class SaveDir {

    private String LOG_TAG = "XRM";

    public static String STORAGE_DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/bootXperia/";
    public static String RAW_BOOT_PATH = "/sdcard/bootXperia/kernel.img";
    public static String RAW_BACKUP_PATH = "/sdcard/bootXperia/fotabackup.img";

    public SaveDir() {
        File valid = new File(STORAGE_DIRECTORY + "valid.txt");
        if (!valid.exists()) {
            valid.mkdirs();
        }
    }

    public Boolean existsbootImage() {
        File boot = new File(STORAGE_DIRECTORY + "kernel.img");
        return boot.exists();
    }

    public Boolean existsFotaBackup() {
        File backup = new File(STORAGE_DIRECTORY + "fotabackup.img");
        return backup.exists();
    }

    public String bootPath() {
        return STORAGE_DIRECTORY + "kernel.img";
    }


    public String backupBath() {
        return STORAGE_DIRECTORY + "fotabackup.img";
    }

    public Boolean validboot() {
        File f = new File(bootPath());
        return (((f.length() / 1024) / 1024) > 5);
    }
}
