/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class ModelTabelMahasiswa extends AbstractTableModel{
    
    java.util.List<ModelMahasiswa> list_mahasiswa;
    
    public ModelTabelMahasiswa(java.util.List<ModelMahasiswa> list_mahasiswa){
        this.list_mahasiswa=list_mahasiswa;
    }

    @Override
    public int getRowCount() {
        return list_mahasiswa.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "NIM";
            case 1 -> "Nama";
            case 2 -> "Jenis Kelamin";
            case 3 -> "Angkatan";
            case 4 -> "Program Studi";
            case 5 -> "Alamat";
            case 6 -> "Kabupaten/Kota";
            default -> null;
        };
    }
    
    

    @Override
    public Object getValueAt(int row, int column) {
        return switch (column) {
            case 0 -> list_mahasiswa.get(row).getNim();
            case 1 -> list_mahasiswa.get(row).getNama();
            case 2 -> list_mahasiswa.get(row).getJenisKelamin();
            case 3 -> list_mahasiswa.get(row).getAngkatan();
            case 4 -> list_mahasiswa.get(row).getProdi();
            case 5 -> list_mahasiswa.get(row).getAlamat();
            case 6 -> list_mahasiswa.get(row).getKota();
            default -> null;
        };
    }
    
}
