package cn.cncc.caos.common.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionDetailUtil {
    public static String getExceptionDetail(Exception ex) {
        String ret = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            pout.close();
            out.close();
        } catch (Exception e) {

        }
        return ret;
    }
}
