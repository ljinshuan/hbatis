package org.shuan.li.hbatis.hbase;

import com.tmall.wireless.fun.biz.common.hbase.typehandler.TypeHandler;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinshuan.li on 2016/8/3.
 */
public class HBaseMapHelper {

    private static Map<String, ResultMap> resultMaps = new ConcurrentHashMap<String, ResultMap>();

    /**
     *
     * @param clazz
     * @param familyName
     * @param <T>
     * @return
     */
    public static <T> T mapResult(Class<T> clazz, Result result, String familyName) {

        try {
            String key = clazz.getName() + "_" + familyName;
            ResultMap resultMap = resultMaps.get(key);
            if (resultMap == null) {
                resultMap = ResultMap.createResultMap(clazz, familyName);
                resultMaps.put(key, resultMap);
            }

            Map<String, Object> primitiveValues = resultMap.getPrimitiveValues(result);
            return (T) resultMap.set2Instance(primitiveValues);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Put mapInsert(Object object, String familyName, final byte[] rowKey) {
        Put put = new Put(rowKey);
        try {
            Class<?> clazz = object.getClass();
            String key = clazz.getName() + "_" + familyName;
            ResultMap resultMap = resultMaps.get(key);
            if (resultMap == null) {
                resultMap = ResultMap.createResultMap(clazz, familyName);
                resultMaps.put(key, resultMap);
            }
            Map<String, ResultMapping> resultMappings = resultMap.getResultMappings();

            for (String property : resultMappings.keySet()) {
                ResultMapping resultMapping = resultMappings.get(property);
                Method getter = resultMapping.getGetter();
                if (getter == null) {
                    continue;
                }
                Object arg = getter.invoke(object, null);
                TypeHandler typeHanler = resultMapping.getTypeHanler();
                if (typeHanler==null){
                    throw new IllegalStateException("can not find handler for "+property);
                }
                byte[] paramValue = typeHanler.getParamValue(arg);
                if (paramValue == null) {
                    continue;
                }
                put.add(resultMap.getFamilyBytes(), resultMapping.getQualifier(), paramValue);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return put;
    }

    public static Put buildPut(Class<?> clazz, String familyName, Map<String, Object> params, final byte[] rowKey) {
        Put put = new Put(rowKey);
        return buildPut(put, clazz, familyName, params);
    }

    public static Put buildPut(Put put, Class<?> clazz, String familyName, Map<String, Object> params) {
        try {
            String key = clazz.getName() + "_" + familyName;
            ResultMap resultMap = resultMaps.get(key);
            if (resultMap == null) {
                resultMap = ResultMap.createResultMap(clazz, familyName);
                resultMaps.put(key, resultMap);
            }
            Map<String, ResultMapping> resultMappings = resultMap.getResultMappings();
            for (String property : params.keySet()) {
                ResultMapping resultMapping = resultMappings.get(property);
                if (resultMapping == null) {
                    throw new IllegalArgumentException("not support keys " + property);
                }
                TypeHandler typeHanler = resultMapping.getTypeHanler();
                if (typeHanler==null){
                    throw new IllegalStateException("can not find handler for "+property);
                }
                byte[] paramValue = typeHanler.getParamValue(params.get(property));
                if (paramValue != null) {
                    put.add(resultMap.getFamilyBytes(), resultMapping.getQualifier(), paramValue);
                }

            }

            return put;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
