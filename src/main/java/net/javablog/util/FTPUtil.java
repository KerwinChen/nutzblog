package net.javablog.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTPUtil {

    private static Log log = Logs.get();

    private static int defaultTimeoutSecond = 300;
    private static int connectTimeoutSecond = 300;
    private static int dataTimeoutSecond = 300;


    private static void uploadDirectory_(FTPClient ftpClient,
                                         String remoteDirPath, String localParentDir, String remoteParentDir)
            throws IOException {

        System.out.println("LISTING directory: " + localParentDir);

        //创建根目录
        boolean created = ftpClient.makeDirectory(remoteDirPath);
        if (created) {
            System.out.println("CREATED the directory: "
                    + remoteDirPath);
        } else {
            System.out.println("COULD NOT create the directory: "
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
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile(ftpClient,
                            localFilePath, remoteFilePath);
                    if (uploaded) {
                        System.out.println("UPLOADED a file to: "
                                + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT upload the file: "
                                + localFilePath);
                    }
                } else {
                    // create directory on the server
                    boolean created_ = ftpClient.makeDirectory(remoteFilePath);
                    if (created_) {
                        System.out.println("CREATED the directory: "
                                + remoteFilePath);
                    } else {
                        System.out.println("COULD NOT create the directory: "
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

    public static boolean uploadSingleFile(FTPClient ftpClient,
                                           String localFilePath, String remoteFilePath) throws IOException {
        File localFile = new File(localFilePath);

        InputStream inputStream = new FileInputStream(localFile);
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


    public static void main(String[] args) {

        boolean b = testconn("1.95.29.151", "dd50", "7947a9");
        System.out.println(b);

    }

    private static void testupload() {
        String server = "23.95.29.151";
        String user = "dd50";
        String pass = "7947a9";

        String localDir = "C:\\Users\\Administrator\\Desktop\\ace";
        String remoteDir = "/target";

        uploadDirectory(server, user, pass, localDir, remoteDir);
    }

    private static void uploadDirectory(String server, String user, String pass, String localDir, String remoteDir) {
        FTPClient ftpClient = new FTPClient();
        try {
            // connect and login to the server
            ftpClient.connect(server, 21);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            System.out.println("Connected");

            FTPUtil.uploadDirectory_(ftpClient, remoteDir, localDir, "");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 5秒钟算超时
     *
     * @param server
     * @param user
     * @param pass
     * @return
     */
    public static boolean testconn(String server, String user, String pass) {

        FTPClient ftpClient = new FTPClient();

        try {
            ftpClient.setConnectTimeout(5000);
            // connect and login to the server
            ftpClient.connect(server, 21);
            boolean rs=ftpClient.login(user, pass);
            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            System.out.println("Connected");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");

            return rs;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

    }

}