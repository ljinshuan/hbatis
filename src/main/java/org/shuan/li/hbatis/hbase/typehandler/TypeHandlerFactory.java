package org.shuan.li.hbatis.hbase.typehandler;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jinshuan.li on 2016/8/3.
 */
public class TypeHandlerFactory {

	private Map<Class, TypeHandler> typeHanlerMap = new ConcurrentHashMap<Class, TypeHandler>();

	public TypeHandlerFactory() {

		TypeHandler hanler = new LongTypeHandler();
		this.register(Long.TYPE, hanler);
		this.register(Long.class, hanler);

		hanler = new IntegerTypeHandler();
		this.register(Integer.TYPE, hanler);
		this.register(Integer.class, hanler);

		hanler = new DoubleTypeHandler();
		this.register(Double.TYPE, hanler);
		this.register(Double.class, hanler);

		hanler = new DateTypeHandler();
		this.register(Date.class, hanler);

		hanler = new BooleanTypeHandler();
		this.register(Boolean.TYPE, hanler);
		this.register(Boolean.class, hanler);

		hanler=new StringTypeHandler();
		this.register(String.class,hanler);
	}

	public void register(Class claazz, TypeHandler hanler) {

		typeHanlerMap.put(claazz, hanler);
	}

	public TypeHandler getTypeHandler(Class clazz) {
		return typeHanlerMap.get(clazz);
	}
}
