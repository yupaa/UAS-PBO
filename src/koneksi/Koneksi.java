/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class Koneksi {
    private static Connection conn;

    public static Connection getKoneksi() {
        if (conn == null) {
            try {
                // URL conn ke database SQLite
                String url = "jdbc:sqlite:databaseUAS.db";
                // Membuat conn ke database
                conn = DriverManager.getConnection(url);
                System.out.println("Koneksi ke database berhasil!");
            } catch (SQLException e) {
                System.out.println("Koneksi ke database gagal: " + e.getMessage());
            }
        }
        return conn;
    }
}
