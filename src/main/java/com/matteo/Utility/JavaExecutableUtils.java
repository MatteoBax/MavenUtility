package com.matteo.Utility;

import java.net.URI;
import java.nio.file.Paths;

public class JavaExecutableUtils {
	/**
	 * 
	 * @return la cartella dell'eseguibile java, null se non esiste
	 */
	public static String getExecutableDir() {
		try {
			String cmd = System.getProperty("sun.java.command");
			if(cmd == null || cmd.isEmpty()) {
				return null;
			}
			
			String mainClassName = cmd.split(" ")[0];
			Class<?> mainClass = cmd.endsWith(".jar") ? JavaExecutableUtils.class : Class.forName(mainClassName);
			URI codePathUri = mainClass
			        .getProtectionDomain()
			        .getCodeSource()
			        .getLocation()
			        .toURI();
			
			String execDir = Paths.get(codePathUri).getParent().toString();
			return execDir;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
