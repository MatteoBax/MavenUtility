package com.matteo.MavenUtility.JavaExecutableUtils;

import java.net.URI;
import java.nio.file.Paths;

public class JavaExecutableUtils {
	/**
	 * 
	 * @return la cartella dell'eseguibile java, null se non esiste
	 */
	public static String getExecutableDir() {
		try {
			String mainClassName = System.getProperty("sun.java.command").split(" ")[0];
			Class<?> mainClass = mainClassName.endsWith(".jar") ? JavaExecutableUtils.class : Class.forName(mainClassName);
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
