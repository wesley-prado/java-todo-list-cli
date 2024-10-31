package com.codemages.cli.unit.mocks.interfaces;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class Mocker {
		protected final List<String>   calledMethods = new ArrayList<String>();
		protected final List<Object[]> passedArgs    = new ArrayList<Object[]>();

		public void assertCalledWith(
						String methodName,
						int callOrder,
						Object[] args
		) {
				Object[] calledArgs = getMethodArgs(methodName, callOrder);

				for (int i = 0; i < args.length; i++) {
						compareStructures(args[i], calledArgs[i]);
				}
		}

		@SuppressWarnings("UnusedReturnValue")
		private static boolean compareStructures(Object obj1, Object obj2) {
				if (obj1 == null || obj2 == null) {
						return false;
				}

				if (obj1.getClass() != obj2.getClass()) {
						return false;
				}

				Field[] fields1 = obj1.getClass().getDeclaredFields();
				Field[] fields2 = obj2.getClass().getDeclaredFields();

				if (fields1.length != fields2.length) {
						return false;
				}

				for (int i = 0; i < fields1.length; i++) {
						fields1[i].setAccessible(true);
						fields2[i].setAccessible(true);

						try {
								Object value1 = fields1[i].get(obj1);
								Object value2 = fields2[i].get(obj2);

								if (value1 == null && value2 != null ||
										value1 != null && !value1.equals(value2)) {
										return false;
								}
						} catch (IllegalAccessException e) {
								e.printStackTrace();
								return false;
						}
				}

				return true;
		}

		@SuppressWarnings("unused")
		public void assertCalledWithStrict(
						String methodName,
						int callOrder,
						Object[] args
		) {
				Object[] calledArgs = getMethodArgs(methodName, callOrder);

				for (int i = 0; i < args.length; i++) {
						Assertions.assertEquals(
										args[i],
										calledArgs[i],
										"Argument mismatch"
						);
				}
		}

		public Object[] getMethodArgs(String methodName, int callOrder) {
				assertCallOrder(callOrder);
				assertWasCalled(methodName);

				Object[] args = passedArgs.get(callOrder - 1);
				assertHasArgs(args);

				return args;
		}

		private void assertCallOrder(int callOrder) {
				Assertions.assertTrue(
								callOrder > 0,
								"callOrder must be greater than 0"
				);
		}

		public void assertWasCalled(String methodName) {
				Assertions.assertTrue(
								calledMethods.contains(methodName),
								"Method " + methodName + " was not called"
				);
		}

		private void assertHasArgs(Object[] args) {
				Assertions.assertTrue(
								args.length > 0,
								"Method was not called with any arguments"
				);
		}

		protected void saveMethodAndArgs(String methodName, Object[] args) {
				calledMethods.add(methodName);
				passedArgs.add(args);
		}
}
