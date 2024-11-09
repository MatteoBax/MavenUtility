package com.matteo.MavenUtility.loadResourceFromClassLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileResourcesUtils {
	public InputStream getFileFromResourceAsStream(String fileName) throws FileNotFoundException{
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
	
	public static InputStream getFileFromResourceAsStream(ClassLoader classLoader, String fileName) throws FileNotFoundException{
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new FileNotFoundException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
	
	private static boolean isDirectoryJar(ClassLoader classLoader, String fileName) throws IOException {
		URL resourceUrl = classLoader.getResource(fileName);
		
        if (resourceUrl != null) {
            String urlPath = resourceUrl.getPath();
            if (urlPath.startsWith("file:") && urlPath.contains("!")) {
                String[] parts = urlPath.split("!");
                String jarPath = parts[0].replaceFirst("file:", "");
                String internalPath = parts[1].substring(1); // Rimuove il primo slash

                JarFile jarFile = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().equals(internalPath)) {   
                        return entry.isDirectory();
                    }
                }
                throw new FileNotFoundException("Risorsa non trovata nel JAR");
            } else {
            	throw new IOException("Il percorso non punta a un JAR.");
            }
        } else {
        	throw new FileNotFoundException("Risorsa non trovata nel JAR");
        }
	}
	
	private static boolean isDirectoryFile(ClassLoader classLoader, String fileName) throws Exception{
		URL resourceUrl = classLoader.getResource(fileName);
		File f = new File(resourceUrl.getFile());
		return f.isDirectory();
	}
	
	public static boolean isDirectory(ClassLoader classLoader, String fileName) {
		try {
			return isDirectoryJar(classLoader, fileName);
		} catch (IOException e) {
			try {
				return isDirectoryFile(classLoader, fileName);
			} catch (Exception e1) {
				return false;
			}
		}		
	}
	
	private static boolean resourceExistJar(ClassLoader classLoader, String fileName) throws IOException {
		try {
			isDirectoryJar(classLoader, fileName);
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	private static boolean resourceExistFile(ClassLoader classLoader, String fileName) throws Exception {
		URL resourceUrl = classLoader.getResource(fileName);
		
		File resource = new File(resourceUrl.getFile());
		return resource.exists();
	}

	public static boolean resourceExist(ClassLoader classLoader, String fileName) {
		System.out.println("REQUIRING RESOURCE " + fileName);
		try {
			return resourceExistJar(classLoader, fileName);
		} catch (IOException e) {
			try {
				return resourceExistFile(classLoader, fileName);
			} catch (Exception e1) {
				return false;
			}
		}
	}
	
	private static boolean isFileJar(ClassLoader classLoader, String fileName) throws Exception {
		return !isDirectoryJar(classLoader, fileName);
	}
	
	private static boolean isFileNoJar(ClassLoader classLoader, String fileName) throws Exception {
		URL resourceUrl = classLoader.getResource(fileName);
		
		File resource = new File(resourceUrl.getFile());
		return resource.isFile();
	}
	
	public static boolean isFile(ClassLoader classLoader, String fileName) {
		try {
			return isFileJar(classLoader, fileName);
		} catch (Exception e) {
			try {
				return isFileNoJar(classLoader, fileName);
			} catch (Exception e1) {
				return false;
			}
		}
	}
}
