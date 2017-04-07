package br.com.sistemacomercialerp.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	public static void registrarLoggerInfo(Class parametro, String msg) {
		Logger.getLogger(parametro).info(msg);
	}

	public static void registrarLoggerErro(Class parametro, String msg) {
		Logger.getLogger(parametro).error(msg);
	}

}
