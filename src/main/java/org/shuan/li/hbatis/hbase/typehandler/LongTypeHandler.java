package org.shuan.li.hbatis.hbase.typehandler;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by jinshuan.li on 2016/8/3.
 *
 */
public class LongTypeHandler implements TypeHandler {

	@Override
	public Object getResult(String propertyName, Class<?> javaType, byte[] value) {

		if (value == null) {
			return null;
		}
		return Bytes.toLong(value);
	}

	@Override
	public byte[] getParamValue(Object param) {

		if (param == null) {
			return null;
		}
		return Bytes.toBytes((Long) param);
	}
}
