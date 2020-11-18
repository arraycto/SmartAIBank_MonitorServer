package com.dcfs.smartaibank.manager.operations.web.convert;


/**
 * @param <T>
 * @param <R>
 * @author tanchena
 * @date 2019/12/20 22:02
 */
@FunctionalInterface
public interface ConvertFactory<T, R> {
	/**
	 * 转换器工厂
	 *
	 * @param str 转换对象
	 * @return 转换后的对象
	 */
	R convert(T str);

}
