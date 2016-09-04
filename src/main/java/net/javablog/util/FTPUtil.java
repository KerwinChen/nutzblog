package net.javablog.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.apache.log4j.Logger;
import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPUtil {

    private static Logger log = Logger.getLogger("FTP");

//    private static int defaultTimeoutSecond = 300;
//    private static int connectTimeoutSecond = 300;
//    private static int dataTimeoutSecond = 300;


    private static void uploadDirectory_(FTPClient ftpClient,
                                         String remoteDirPath, String localParentDir, String remoteParentDir)
            throws IOException {

        log.info("LISTING directory: " + localParentDir);

        //创建根目录
        boolean created = ftpClient.makeDirectory(remoteDirPath);
        if (created) {
            log.info("CREATED the directory: "
                    + remoteDirPath);
        } else {
            log.info("COULD NOT create the directory: "
                    + remoteDirPath);
        }


        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();
        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "/" + remoteParentDir
                        + "/" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "/" + item.getName();
                }


                if (item.isFile()) {
                    // upload the file
                    String localFilePath = item.getAbsolutePath();
                    log.info("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile_(ftpClient,
                            localFilePath, remoteFilePath);
                    if (uploaded) {
                        log.info("UPLOADED a file to: "
                                + remoteFilePath);
                    } else {
                        log.info("COULD NOT upload the file: "
                                + localFilePath);
                    }
                } else {
                    // create directory on the server
                    boolean created_ = ftpClient.makeDirectory(remoteFilePath);
                    if (created_) {
                        log.info("CREATED the directory: "
                                + remoteFilePath);
                    } else {
                        log.info("COULD NOT create the directory: "
                                + remoteFilePath);
                    }

                    // upload the sub directory
                    String parent = remoteParentDir + "/" + item.getName();
                    if (remoteParentDir.equals("")) {
                        parent = item.getName();
                    }

                    localParentDir = item.getAbsolutePath();
                    uploadDirectory_(ftpClient, remoteDirPath, localParentDir,
                            parent);
                }
            }
        }
    }

    private static boolean uploadSingleFile_(FTPClient ftpClient,
                                            String localFilePath, String remoteFilePath) throws IOException {
        File localFile = new File(localFilePath);

        InputStream inputStream = new FileInputStream(localFile);

        String remotepath = new File(remoteFilePath).getParent();
        if (Lang.isWin()) {
            remotepath = remotepath.replace('\\', '/');
        }

        if (!ftpClient.changeWorkingDirectory(remotepath)) {
            makeDirectory(ftpClient, remotepath);
        }

        try {

            ftpClient.setCopyStreamListener(new CopyStreamAdapter() {
                @Override
                public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                    log.info("已经上传的大小: " + (String.format("%.2f", totalBytesTransferred / 1024.0 / 1024.0)) + "Mb  " + localFilePath + " >>> " + remoteFilePath);
                }
            });

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remoteFilePath, inputStream);
        } finally {
            inputStream.close();
        }
    }

    private static void makeDirectory(FTPClient client, String remotepath) {
        String[] dirs = remotepath.split("/");
        log.info("开始创建目录:" + remotepath);
        try {
            for (int i = 0; i < dirs.length; i++) {
                String dir = dirs[i];
                //放弃对中文的处理，因为没有权限设置环境变量。 http://blog.csdn.net/dqswuyundong/article/details/11993835
//            dir = new String(dir.getBytes("GBK"), "ISO-8859-1");
                if (!org.nutz.lang.Strings.isBlank(dir)) {
                    if (!client.changeWorkingDirectory(dir)) {
                        client.makeDirectory(dir);
                        log.info("创建子目录:" + dir);
                        client.changeWorkingDirectory(dir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void uploadDirectory(String server, String user, String pass, String localDir, String remoteDir) {
        FTPClient ftpClient = new FTPClient();
        try {
            // connect and login to the server
            ftpClient.connect(server, 21);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            log.info("Connected");

            FTPUtil.uploadDirectory_(ftpClient, remoteDir, localDir, "");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            log.info("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 3秒钟算超时
     *
     * @param ftpip
     * @param ftpuser
     * @param ftppwd
     * @return
     */
    public static boolean testconn(String ftpip, String ftpuser, String ftppwd ) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.setConnectTimeout(3000);
            // connect and login to the server
            ftpClient.connect(ftpip, 21);
            boolean rs = ftpClient.login(ftpuser, ftppwd);
            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            log.info("Connected");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            log.info("Disconnected");

            return rs;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    private static void testupload() {
        String server = "23.95.29.151";
        String user = "dd50";
        String pass = "7947a9";

        String localDir = "C:\\Users\\Administrator\\Desktop\\ace";
        String remoteDir = "/target";
        uploadDirectory(server, user, pass, localDir, remoteDir);
    }


    private static void testuploadfile() {
        String server = "23.95.29.151";
        String user = "dd50";
        String pass = "7947a9";

        String localDir = "E:\\workspace\\work_me\\nutzblog\\pom.xml";
        String remoteDir = "/target/test/test/ddd/ddd.pro";
        uploadSingleFile(server, user, pass, localDir, remoteDir);
    }


    /**
     * Removes a non-empty directory by delete all its sub files and
     * sub directories recursively. And finally remove the directory.
     */
    public static void removeDirectory(FTPClient ftpClient, String parentDir,
                                       String currentDir) throws IOException {
        String dirToList = parentDir;
        if (!currentDir.equals("")) {
            dirToList += "/" + currentDir;
        }

        FTPFile[] subFiles = ftpClient.listFiles(dirToList);

        if (subFiles != null && subFiles.length > 0) {
            for (FTPFile aFile : subFiles) {
                String currentFileName = aFile.getName();
                if (currentFileName.equals(".") || currentFileName.equals("..")) {
                    // skip parent directory and the directory itself
                    continue;
                }
                String filePath = parentDir + "/" + currentDir + "/"
                        + currentFileName;
                if (currentDir.equals("")) {
                    filePath = parentDir + "/" + currentFileName;
                }

                if (aFile.isDirectory()) {
                    // remove the sub directory
                    removeDirectory(ftpClient, dirToList, currentFileName);
                } else {
                    // delete the file
                    boolean deleted = ftpClient.deleteFile(filePath);
                    if (deleted) {
                        log.info("DELETED the file: " + filePath);
                    } else {
                        log.info("CANNOT delete the file: "
                                + filePath);
                    }
                }
            }

            // finally, remove the directory itself
            boolean removed = ftpClient.removeDirectory(dirToList);
            if (removed) {
                log.info("REMOVED the directory: " + dirToList);
            } else {
                log.info("CANNOT remove the directory: " + dirToList);
            }
        }
    }


    public static void uploadSingleFile(String ftpip, String ftpuser, String ftppwd, String localfile, String remotefile) {
        FTPClient ftpClient = new FTPClient();
        try {
            // connect and login to the server
            ftpClient.connect(ftpip, 21);
            ftpClient.login(ftpuser, ftppwd);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            log.info("Connected");

            FTPUtil.uploadSingleFile_(ftpClient, localfile, remotefile);

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            log.info("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            log.info("upload over " + remotefile);
        }

    }


    public static void main(String[] args) {

//        boolean b = testconn("23.95.29.151", "dd50", "7947a9");
//        log.info(b);
//        testupload();
//        testuploadfile();

//        testuploadfile();
        removeDirectory("23.95.29.151", "dd50", "7947a9", "/");


    }


    private static void removeDirectory(String ftpip, String ftpuser, String ftppwd, String remotepath) {

        FTPClient ftpClient = new FTPClient();
        try {
            // connect and login to the server
            ftpClient.connect(ftpip, 21);
            ftpClient.login(ftpuser, ftppwd);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            log.info("Connected");

            //入口肯定是 空
            FTPUtil.removeDirectory(ftpClient, remotepath, "");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            log.info("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}