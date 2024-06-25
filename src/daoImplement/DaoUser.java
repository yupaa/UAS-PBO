/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImplement;

import daoInterface.InterfaceUser;
import model.ModelUser;
import java.sql.PreparedStatement;
import koneksi.Koneksi;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class DaoUser implements InterfaceUser{
    Connection connection;
    
    public DaoUser(){
        connection = Koneksi.getKoneksi();
    }

    @Override
     public boolean login(ModelUser user) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
    
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                // User ditemukan, bisa melakukan login
                System.out.println("Login berhasil untuk pengguna: " + user.getUsername());
                return true; // Login berhasil
            } else {
                // User tidak ditemukan, login gagal
                System.out.println("Login gagal. Username atau password salah.");
                return false; // Login gagal
            }
        } catch (SQLException ex) {
            // Tangani kesalahan SQL
            ex.printStackTrace();
            return false; // Login gagal jika ada kesalahan
        }
    }
    
}
