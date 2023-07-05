package org.games.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * IO工具类
 *
 * @author gy on 2014-6-6下午2:42:02
 */
public class IOUtil {

    /**
     * 输入流到输出流
     *
     * @param in  输入流
     * @param out 输出流
     * @throws IOException
     */
    public static void in2out(InputStream in, OutputStream out) {
        try {
            byte[] bs = new byte[1024];
            int len = in.read(bs);
            while(len != -1) {
                out.write(bs, 0, len);
                len = in.read(bs);
            }
            out.flush();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 输入流到文件
     *
     * @param in
     * @param file 文件路径
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void in2File(InputStream in, String file) {
        in2File(in, new File(file));
    }

    /**
     * 输入流到文件
     *
     * @param in
     * @param file
     * @author gy on 2014-6-6下午2:44:45
     */
    public static void in2File(InputStream in, File file) {
        try {
            in2out(in, new BufferedOutputStream(new FileOutputStream(file)));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入流得到String内容
     *
     * @param in
     * @return
     * @author gy on 2014-6-6下午2:45:24
     */
    public static String in2String(InputStream in) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        in2out(in, bout);
        String string = bout.toString();
        return string;
    }

    /**
     * 输入流得到byte[]内容
     *
     * @param in
     * @return
     * @author gy on 2014-6-6下午2:46:57
     */
    public static byte[] in2bytes(InputStream in) {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        IOUtil.in2out(in, bout);
        byte[] string = bout.toByteArray();
        return string;
    }

    /**
     * byte[]类型传入输出流
     *
     * @param bytes
     * @param out
     * @author gy on 2014-6-6下午2:48:35
     */
    public static void out4byte(byte[] bytes, OutputStream out) {
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        IOUtil.in2out(in, out);
    }

    /**
     * String类型传入输出流
     *
     * @param bytes
     * @param out
     * @author gy on 2014-6-6下午2:48:35
     */
    public static void out4String(String bytes, OutputStream out) {
        out4byte(bytes.getBytes(), out);
    }

    /**
     * object类型转byte[]类型
     *
     * @param object
     * @return
     * @author gy on 2014-6-6下午2:50:52
     */
    public static byte[] object2bytes(Object object) {
        byte[] bs = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream obout = new ObjectOutputStream(bout);
            obout.writeObject(object);
            bs = bout.toByteArray();
            obout.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return bs;
    }

    public static Object object4bytes(byte[] bs) {
        Object object = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bs);
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            object=ois.readObject();
            ois.close();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }
}
