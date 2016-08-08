package org.shuan.li.hbatis.hbase;

import com.tmall.wireless.fun.biz.common.hbase.typehandler.TypeHandler;

import java.lang.reflect.Method;

/**
 * Created by jinshuan.li on 2016/8/3.
 */
public class ResultMapping {

    private String colunmName;
    private byte[] qualifier;
    private Class<?> javaType;
    private String propertyName;

    private TypeHandler typeHanler;

    private Object nullValue;

    private Method getter;
    private Method setter;

    public Object getNullValue() {
        return nullValue;
    }

    public void setNullValue(Object nullValue) {
        this.nullValue = nullValue;
    }

    public TypeHandler getTypeHanler() {
        return typeHanler;
    }

    public void setTypeHanler(TypeHandler typeHanler) {
        this.typeHanler = typeHanler;
    }

    public Method getGetter() {
        return getter;
    }

    public void setGetter(Method getter) {
        this.getter = getter;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColunmName() {
        return colunmName;
    }

    public void setColunmName(String colunmName) {
        this.colunmName = colunmName;
    }

    public byte[] getQualifier() {
        return qualifier;
    }

    public void setQualifier(byte[] qualifier) {
        this.qualifier = qualifier;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }
}
