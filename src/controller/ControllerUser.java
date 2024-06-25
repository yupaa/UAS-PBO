/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import daoImplement.DaoUser;
import model.ModelUser;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class ControllerUser {
    
    private DaoUser daoUser; // Sesuaikan dengan nama DAO yang sesuai
    
    // Constructor
    public ControllerUser() {
        this.daoUser = new DaoUser(); // Inisialisasi objek DaoUser
    }
    
    // Method untuk login pengguna
    public boolean login(String username, String password) {
        ModelUser user = new ModelUser();
        user.setUsername(username);
        user.setPassword(password);
        
        // Panggil metode login dari DaoUser
        return daoUser.login(user);
    }
}

