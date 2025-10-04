package com.matteo.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe che contiene metodi di supporto per i database SQL
 * 
 * @author Matteo Basso
 */
public class SqlDatabaseUtils {
    /**
     * Esegue le query
     * 
     * @param connection la connessione al database (senza specificare il database)
     * @param bufferedReader il bufferedReader
     * @throws SQLException nel caso in cui si verifichi un errore interno al database
     * @throws IOException nel caso si sia verificato un errore di lettura dal BufferedReader
     */
    private static void executeQueriesFromFile(Connection connection, BufferedReader bufferedReader) throws SQLException, IOException {
        String delimiter = ";";
        StringBuilder sb = new StringBuilder();
        String readLine;
        while((readLine = bufferedReader.readLine()) != null) {
            if(readLine.startsWith("DELIMITER")) {
                delimiter = readLine.replaceFirst("DELIMITER", "").trim();
            } else if(!readLine.trim().isEmpty()) {
                sb.append(readLine + "\n");
                if(readLine.endsWith(delimiter) && !readLine.startsWith("DELIMITER")) {
                    String query = sb.toString();
                    int index = query.lastIndexOf(delimiter);
                    if(index != -1) {
                        query = query.substring(0, index);
                        query += ";";
                    }
                    
                    try(Statement stmt = connection.createStatement()) {
                        stmt.execute(query);
                    }
                    sb = new StringBuilder();
                }
            }
        }
    }

    /**
     * Questo metodo inizializza il database (crea il database, le relative tabelle, trigger, ...)
     * @param connection la connessione al database (senza specificare il database)
     * @param file il file che contiene le query da eseguire
     * @throws SQLException nel caso in cui si verifichi un errore interno al database
     * @throws IOException nel caso si sia verificato un errore di lettura dal file
     */
    public static void initializeDatabaseFromFile(Connection connection, File file) throws SQLException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            executeQueriesFromFile(connection, br);
        }
    }

    /**
     * Questo metodo inizializza il database (crea il database, le relative tabelle, trigger, ...)
     * 
     * @param connection la connessione al database (senza specificare il database)
     * @param inputStream l'InputStream dal quale leggere le query da eseguire
     * @throws SQLException nel caso in cui si verifichi un errore interno al database
     * @throws IOException nel caso si sia verificato un errore di lettura dall'InputStream
     */
    public static void initializeDatabaseFromInputStream(Connection connection, InputStream inputStream) throws SQLException, IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            executeQueriesFromFile(connection, br);
        }
    }
}
