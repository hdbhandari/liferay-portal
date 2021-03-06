/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.portal.kernel.transaction.TransactionInvokerUtil}
 */
@Deprecated
public class ServiceBeanMethodInvocationFactoryUtil {

	public static ServiceBeanMethodInvocationFactory
		getServiceBeanMethodInvocationFactory() {

		return _serviceBeanMethodInvocationFactory;
	}

	public static Object proceed(
			Object target, Class<?> targetClass, Method method,
			Object[] arguments, String[] methodInterceptorBeanIds)
		throws Exception {

		return getServiceBeanMethodInvocationFactory().proceed(
			target, targetClass, method, arguments, methodInterceptorBeanIds);
	}

	public void setServiceBeanMethodInvocationFactory(
		ServiceBeanMethodInvocationFactory serviceBeanMethodInvocationFactory) {

		_serviceBeanMethodInvocationFactory =
			serviceBeanMethodInvocationFactory;
	}

	private static ServiceBeanMethodInvocationFactory
		_serviceBeanMethodInvocationFactory;

}