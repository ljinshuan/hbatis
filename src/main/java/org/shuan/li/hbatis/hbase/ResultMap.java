package org.shuan.li.hbatis.hbase;

import com.tmall.wireless.fun.biz.common.hbase.typehandler.TypeHandler;
import com.tmall.wireless.fun.biz.common.hbase.typehandler.TypeHandlerFactory;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinshuan.li on 2016/8/3.
 *
 */
public class ResultMap {

	private static TypeHandlerFactory typeHandlerFactory = new TypeHandlerFactory();
	private String id;
	private Class<?> type;
	private String family;
	private byte[] familyBytes;
	private Map<String, ResultMapping> resultMappings = new HashMap<String, ResultMapping>();

	public static ResultMap createResultMap(Class<?> clazz, String family) throws NoSuchMethodException {
		Field[] fields = clazz.getDeclaredFields();
		ResultMap resultMap = new ResultMap();
		resultMap.setId(clazz.getName());
		resultMap.setFamily(family);
		resultMap.setType(clazz);
		Map<String, ResultMapping> resultMappings = resultMap.getResultMappings();

		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations == null || annotations.length == 0) {
				continue;
			}
			for (Annotation annotation : annotations) {
				if (annotation.annotationType().equals(HColumn.class)) {
					HColumn hColumn = (HColumn) annotation;
					String colName = hColumn.name();

					ResultMapping resultMapping = new ResultMapping();
					resultMapping.setColunmName(colName);
					resultMapping.setQualifier(Bytes.toBytes(colName));
					resultMapping.setJavaType(field.getType());
					resultMapping.setPropertyName(field.getName());
					String name = field.getName();
					String methodSuffix = name.substring(0, 1).toUpperCase() + name.substring(1);
					TypeHandler hanler = typeHandlerFactory.getTypeHandler(resultMapping.getJavaType());
					if (field.getType().isPrimitive()) {
						resultMapping.setNullValue(0);
					} else {
						resultMapping.setNullValue(null);
					}
					resultMapping.setTypeHanler(hanler);

					Method method = clazz.getDeclaredMethod("set" + methodSuffix, field.getType());
					resultMapping.setSetter(method);
					method = clazz.getDeclaredMethod("get" + methodSuffix, null);
					resultMapping.setGetter(method);
					resultMappings.put(field.getName(), resultMapping);
					break;
				}
			}
		}
		if (resultMappings == null || resultMappings.isEmpty()) {
			throw new IllegalArgumentException("NO Declared Annotions in " + clazz.toString());
		}
		return resultMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFamily() {
		return family;
	}

	public byte[] getFamilyBytes() {
		return familyBytes;
	}

	private void setFamily(String family) {
		this.family = family;
		this.familyBytes = Bytes.toBytes(family);
	}

	public Map<String, ResultMapping> getResultMappings() {
		return resultMappings;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	/**
	 * 获取原生数据
	 * 
	 * @param result
	 * @return
	 */
	public Map<String, Object> getPrimitiveValues(Result result) {

		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, ResultMapping> resultMappings = this.resultMappings;
		Collection<ResultMapping> values = resultMappings.values();
		for (ResultMapping resultMapping : values) {
			byte[] value = result.getValue(familyBytes, resultMapping.getQualifier());
			String propertyName = resultMapping.getPropertyName();
			TypeHandler typeHanler = resultMapping.getTypeHanler();
			if (typeHanler==null){
				throw new IllegalStateException("can not find handler for "+propertyName);
			}
			Object primitiveData = typeHanler.getResult(propertyName, resultMapping.getJavaType(),
					value);
			if (primitiveData == null) {
				primitiveData = resultMapping.getNullValue();
			}
			data.put(propertyName, primitiveData);
		}
		return data;
	}

	public Object set2Instance(Map<String, Object> primitiveValues) throws Exception {

		Object instance = type.newInstance();
		Collection<ResultMapping> values = resultMappings.values();
		for (ResultMapping resultMapping : values) {
			Method setter = resultMapping.getSetter();
			if (setter==null){
				continue;
			}
			Object arg = primitiveValues.get(resultMapping.getPropertyName());
			setter.invoke(instance,arg);
		}
		return instance;
	}
}
