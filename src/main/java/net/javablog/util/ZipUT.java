package net.javablog.util;


import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.io.File;
import java.util.ArrayList;


public class ZipUT {


    /**
     * 添加多个文件到压缩包中
     *
     * @param targetZip
     * @param filesToAdd
     * @param relatpath  　在压缩包中的位置　比如/,test/ ,test1/test2/
     */
    public static void AddFilesToFolderInZip(String targetZip, ArrayList<File> filesToAdd, String relatpath) {
        try {

            ZipFile zipFile = new ZipFile(targetZip);

            // Initiate Zip Parameters
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

            // Set the compression level.
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            // Sets the folder in the zip file to which these new files will be added.
            // In this example, test2 is the folder to which these files will be added.
            // Another example: if files were to be added to a directory test2/test3, then
            // below statement should be parameters.setRootFolderInZip("test2/test3/");
            parameters.setRootFolderInZip(relatpath);

            // Now add files to the zip file
            zipFile.addFiles(filesToAdd, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }


    }


    public static void AddFolder(String targetzip, String folderToAdd) {

        try {
            ZipFile zipFile = new ZipFile(targetzip);

            // Initiate Zip Parameters which define various properties such
            // as compression method, etc.
            ZipParameters parameters = new ZipParameters();

            // set compression method to store compression
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            // Set the compression level
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            // Add folder to the zip file
            zipFile.addFolder(folderToAdd, parameters);

        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

}
