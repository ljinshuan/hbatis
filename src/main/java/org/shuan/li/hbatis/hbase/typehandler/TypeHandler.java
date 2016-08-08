package org.shuan.li.hbatis.hbase.typehandler;

/**
 * Created by jinshuan.li on 2016/8/3.
 */
public interface TypeHandler {

    Object getResult(String propertyName, Class<?> javaType, final byte[] value);

    byte[] getParamValue(Object param);
}
