/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import daoImplement.DaoMahasiswa;
import daoInterface.InterfaceMahasiswa;
import model.ModelDashboard;
import view.ViewDashboard;

/**
 *
 * @author asus
 */
public class ControllerDashboard {
    private ViewDashboard view;
    private ModelDashboard model;
    private InterfaceMahasiswa daoMahasiswa;

    public ControllerDashboard(ViewDashboard view) {
        this.view = view;
        this.model = new ModelDashboard();
        this.daoMahasiswa = new DaoMahasiswa();
        updateDashboard();
    }

    public void updateDashboard() {
        int jumlah = daoMahasiswa.getCount();
        int laki = daoMahasiswa.getLakiCount();
        int perempuan = daoMahasiswa.getPrCount();
        int ang62 = daoMahasiswa.get62Count();
        int ang63 = daoMahasiswa.get63Count();
        int ang64 = daoMahasiswa.get64Count();
        int ang65 = daoMahasiswa.get65Count();
        int d3 = daoMahasiswa.getD3Count();
        int st = daoMahasiswa.getSTCount();
        int ks = daoMahasiswa.getKSCount();
        int yk = daoMahasiswa.getKotaCount();
        int sleman = daoMahasiswa.getSlCount();
        int bantul = daoMahasiswa.getBtCount();
        int kp = daoMahasiswa.getKpCount();
        int gk = daoMahasiswa.getGkCount();
        view.getJmlLabel().setText(String.valueOf(jumlah));
        view.getLakiLabel().setText(String.valueOf(laki));
        view.getPrLabel().setText(String.valueOf(perempuan));
        view.getAkt62Label().setText(String.valueOf(ang62));
        view.getAkt63Label().setText(String.valueOf(ang63));
        view.getAkt64Label().setText(String.valueOf(ang64));
        view.getAkt65Label().setText(String.valueOf(ang65));
        view.getD3Label().setText(String.valueOf(d3));
        view.getStLabel().setText(String.valueOf(st));
        view.getKsLabel().setText(String.valueOf(ks));
        view.getYkLabel().setText(String.valueOf(yk));
        view.getSlLabel().setText(String.valueOf(sleman));
        view.getBtLabel().setText(String.valueOf(bantul));
        view.getKpLabel().setText(String.valueOf(kp));
        view.getGkLabel().setText(String.valueOf(gk));
    }
    
    
}
