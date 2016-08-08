package org.shuan.li.hbatis.hbase.typehandler;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * Created by jinshuan.li on 2016/8/3.
 */
public class DateTypeHandler implements TypeHandler {
	@Override
	public Object getResult(String propertyName, Class<?> javaType, byte[] value) {

		if (value == null) {
			return null;
		}
		if (javaType.equals(Date.class)) {
			long time = Bytes.toLong(value);
			return new Date(time);
		}
		return null;
	}

	@Override
	public byte[] getParamValue(Object param) {

		if (param == null) {
			return null;
		}
        Date time=(Date)param;

		return Bytes.toBytes(time.getTime());
	}
}
