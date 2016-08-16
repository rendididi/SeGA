package org.sega.ProcessDesigner.util;
/**
 * 解析异常工具类
 * @author WXf
 *
 */
public class ExceptionUtil {
	public static String getExceptionAllinformation(Exception ex){
        String sOut = ex.getMessage() + " &&&& ";
        StackTraceElement[] trace = ex.getStackTrace();
        int i = 0;
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
            i++;
            if(i == 3){
            	break;
            }
        }
        return sOut;
	}
}