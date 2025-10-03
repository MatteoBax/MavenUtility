package com.matteo.Utility;

/**
 * Classe OSValidator
 * <br>
 * Contiene i metodi per verificare che sistema operativo � in esecuzione
 * 
 * @author Matteo Basso
 */
public class OSValidator {
	private static final String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * 
	 * @return <b>true</b> se il sistema operativo � Windows, <b>false</b> altrimenti.
	 */
	public static boolean isWindows() {
        return OS.contains("win");
    }
	
	/**
	 * 
	 * @return <b>true</b> se il sistema operativo � macOS, <b>false</b> altrimenti.
	 */
    public static boolean isMacOS() {
        return OS.contains("mac");
    }
    
    /**
	 * 
	 * @return <b>true</b> se il sistema operativo fa parte della famiglia UNIX, <b>false</b> altrimenti.
	 */
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }
    
    /**
	 * 
	 * @return <b>true</b> se il sistema operativo � Oracle Solaris, <b>false</b> altrimenti.
	 */
    public static boolean isSolaris() {
        return OS.contains("sunos");
    }
}
