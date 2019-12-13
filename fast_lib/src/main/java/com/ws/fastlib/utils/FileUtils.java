package com.ws.fastlib.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileUtils {

    public static byte[] readBytes(String filePath) {
        return fileRead(new File(filePath));
    }

    public static byte[] readBytes(File file) {
        return fileRead(file);
    }

    public static String read(String filePath) {
        return read(new File(filePath));
    }

    public static String read(File file) {
        byte[] bytes = fileRead(file);
        if (bytes != null) {
            return new String(bytes, Charset.forName("UTF-8"));
        }
        return null;
    }

    private static byte[] fileRead(File file) {
        if (file.exists()) {
            FileInputStream fis = null;
            FileChannel channel = null;
            try {
                fis = new FileInputStream(file);
                channel = fis.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(fis.available());
                channel.read(buffer);
                buffer.flip();
                return buffer.array();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.safeClose(channel, fis);
            }
        }
        return null;
    }

    public static void write(String filePath, String content) {
        write(new File(filePath), content);
    }

    public static void write(File file, String content) {
        write(file, content.getBytes(Charset.forName("UTF-8")));
    }

    public static void write(String filePath, InputStream is) {
        write(new File(filePath), is);
    }

    public static void write(File file, InputStream is) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buff = new byte[4096];
            int len;
            while ((len = is.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeClose(is, fos);
        }
    }

    public static void write(String filePath, byte[] bytes) {
        write(new File(filePath), bytes);
    }

    public static void write(File file, byte[] bytes) {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fos = null;
        FileChannel channel = null;
        try {
            fos = new FileOutputStream(file);
            channel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.safeClose(channel, fos);
        }
    }

    public static void mkdir(String filePath) {
        mkdir(new File(filePath));
    }

    public static void mkdir(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFile(String filePath) {
        deleteFile(new File(filePath));
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        deleteFile(f);
                    }
                }
            }
            file.delete();
        }
    }
}
