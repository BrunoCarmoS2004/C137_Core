package br.com.c137.project.financial.core.multitenancy.mastertenant.config;

public class DBContextHolder {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

	public static void setCurrentDb(String dbType) {
		contextHolder.set(dbType);
	}

	public static String getCurrentDb() {
		return contextHolder.get();
	}

	public static void clear() {
		contextHolder.remove();
	}

}
