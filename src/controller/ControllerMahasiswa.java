/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import daoImplement.DaoMahasiswa;
import daoInterface.InterfaceMahasiswa;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.ModelMahasiswa;
import model.ModelTabelMahasiswa;
import view.ViewDashboard;
import view.ViewLogin;
import view.ViewMahasiswa;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class ControllerMahasiswa {
    ViewMahasiswa frameMahasiswa;
    ViewDashboard frameDashboard;
    InterfaceMahasiswa interfaceMahasiswa;
    List<ModelMahasiswa> listMahasiswa;
    
    public ControllerMahasiswa(ViewMahasiswa frameMahasiswa){
        this.frameMahasiswa=frameMahasiswa;
        interfaceMahasiswa = new DaoMahasiswa();
        listMahasiswa = interfaceMahasiswa.getAll();
    }

    public ControllerMahasiswa() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    //tombol reset
    public void reset(){
        frameMahasiswa.getTextNim().setText("");
        frameMahasiswa.getTextNama().setText("");
        frameMahasiswa.getBtnGrpJK().clearSelection();
        frameMahasiswa.getBtnGrpAngkatan().clearSelection();
        frameMahasiswa.getComboProdi().setSelectedIndex(0);
        frameMahasiswa.getTextAlamat().setText("");
        frameMahasiswa.getComboKabKot().setSelectedIndex(0);
        frameMahasiswa.getTextNim().setEditable(true); // Menonaktifkan edit
        frameMahasiswa.getTextNim().setEnabled(true); // Menonaktifkan klik
    }
    
    //tampil data ke tabel
    public void isiTabel(){
        listMahasiswa = interfaceMahasiswa.getAll();
        ModelTabelMahasiswa tmhs = new ModelTabelMahasiswa(listMahasiswa);
        frameMahasiswa.getTabelMhs().setModel(tmhs);
    }
    
    // menampilkan data ke form jika data tabel diklik
    public void isiField(int row){
        //mendapatkan NIM
        frameMahasiswa.getTextNim().setText(String.valueOf(listMahasiswa.get(row).getNim())); 
        frameMahasiswa.getTextNim().setEditable(false); // Menonaktifkan edit
        frameMahasiswa.getTextNim().setEnabled(false); // Menonaktifkan klik
        
        //mendapatkan nama
        frameMahasiswa.getTextNama().setText(listMahasiswa.get(row).getNama());
        
        // mendapatkan jenis kelamin
        String jenisKelamin = listMahasiswa.get(row).getJenisKelamin();
        // Memilih radio button berdasarkan jenis kelamin
        if (jenisKelamin.equalsIgnoreCase("Laki-Laki")) {
            frameMahasiswa.getBtnLaki().setSelected(true);
        } else {
            frameMahasiswa.getBtnPerempuan().setSelected(true);
        }
        
        // mendapatkan angkatan
        String angkatan = listMahasiswa.get(row).getAngkatan();
        // Memilih radio button berdasarkan jenis kelamin
        if (angkatan.equalsIgnoreCase("62")) {
            frameMahasiswa.getBtn62().setSelected(true);
        } else if (angkatan.equalsIgnoreCase("63")) {
            frameMahasiswa.getBtn63().setSelected(true);
        } else if (angkatan.equalsIgnoreCase("64")) {
            frameMahasiswa.getBtn64().setSelected(true);
        } else {
            frameMahasiswa.getBtn65().setSelected(true);
        }
        
        //mendapatkan alamat
        frameMahasiswa.getTextAlamat().setText(listMahasiswa.get(row).getAlamat());
        
        //mendapatkan prodi
        frameMahasiswa.getComboProdi().setSelectedItem(listMahasiswa.get(row).getProdi());
        
        //mendapatkan kabupaten/kota tempat tinggal
        frameMahasiswa.getComboKabKot().setSelectedItem(listMahasiswa.get(row).getKota());
    }
    
    // masukkan data dari form ke db
    public void insert(){
        // Memeriksa apakah TextField tidak kosong
        boolean nimFilled = !frameMahasiswa.getTextNim().getText().trim().isEmpty();
        boolean namaFilled = !frameMahasiswa.getTextNama().getText().trim().isEmpty();
        boolean alamatFilled = !frameMahasiswa.getTextAlamat().getText().trim().isEmpty();

        // Memeriksa apakah JComboBox memiliki pilihan yang dipilih
        boolean prodiFilled = frameMahasiswa.getComboProdi().getSelectedItem() != null;
        boolean kotaFilled = frameMahasiswa.getComboKabKot().getSelectedItem() != null;

        // Memeriksa apakah ada JRadioButton yang dipilih dalam ButtonGroup
        boolean jkSelected = frameMahasiswa.getBtnGrpJK().getSelection() != null;
        boolean angkatanSelected = frameMahasiswa.getBtnGrpAngkatan().getSelection() != null;

        // Memastikan semua field terisi
        if (nimFilled && namaFilled && alamatFilled && jkSelected && angkatanSelected && prodiFilled && kotaFilled) {
            try {
                // Mendapatkan NIM dari TextField
                int nim = Integer.parseInt(frameMahasiswa.getTextNim().getText().trim());

                // Memeriksa apakah NIM sudah ada di database
                if (interfaceMahasiswa.isNimExist(nim)) {
                    JOptionPane.showMessageDialog(null, "NIM sudah ada di database. Silakan masukkan NIM yang berbeda.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Memeriksa apakah comboProdi dan comboKabKot terpilih index ke-0
                    String prodi = (String) frameMahasiswa.getComboProdi().getSelectedItem();
                    String kabKot = (String) frameMahasiswa.getComboKabKot().getSelectedItem();

                    if (prodi.equals(" ") || kabKot.equals(" ")) {
                        JOptionPane.showMessageDialog(null, "Silakan lengkapi data.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Lakukan operasi insert
                        ModelMahasiswa mhs = new ModelMahasiswa();
                        mhs.setNim(nim);
                        mhs.setNama(frameMahasiswa.getTextNama().getText().trim());
                        mhs.setAlamat(frameMahasiswa.getTextAlamat().getText().trim());
                        mhs.setProdi(prodi);
                        mhs.setKota(kabKot);

                        // Mendapatkan jenis kelamin dari button group
                        if (frameMahasiswa.getBtnLaki().isSelected()) {
                            mhs.setJenisKelamin("Laki-Laki");
                        } else {
                            mhs.setJenisKelamin("Perempuan");
                        }

                        // Mendapatkan angkatan dari button group
                        if (frameMahasiswa.getBtn62().isSelected()) {
                            mhs.setAngkatan("62");
                        } else if (frameMahasiswa.getBtn63().isSelected()) {
                            mhs.setAngkatan("63");
                        } else if (frameMahasiswa.getBtn64().isSelected()) {
                            mhs.setAngkatan("64");
                        } else {
                            mhs.setAngkatan("65");
                        }

                        // Lakukan penyimpanan data mahasiswa ke database atau data structure
                        interfaceMahasiswa.insert(mhs);

                        JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan!");
                    }
                }

            } catch (NumberFormatException e) {
                // Jika nim bukan angka, tampilkan pesan error
                JOptionPane.showMessageDialog(null, "NIM harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pastikan Semua Field Telah Diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void update(){
        // Memeriksa apakah TextField tidak kosong
        boolean nimFilled = !frameMahasiswa.getTextNim().getText().trim().isEmpty();
        boolean namaFilled = !frameMahasiswa.getTextNama().getText().trim().isEmpty();
        boolean alamatFilled = !frameMahasiswa.getTextAlamat().getText().trim().isEmpty();

        // Memeriksa apakah JComboBox memiliki pilihan yang dipilih
        boolean prodiFilled = frameMahasiswa.getComboProdi().getSelectedItem() != null;
        boolean kotaFilled = frameMahasiswa.getComboKabKot().getSelectedItem() != null;

        // Memeriksa apakah ada JRadioButton yang dipilih dalam ButtonGroup
        boolean jkSelected = frameMahasiswa.getBtnGrpJK().getSelection() != null;
        boolean angkatanSelected = frameMahasiswa.getBtnGrpAngkatan().getSelection() != null;

        // Memastikan semua field terisi dan valid
        if (nimFilled && namaFilled && alamatFilled && jkSelected && angkatanSelected && prodiFilled && kotaFilled) {
            try {
                // Mendapatkan NIM dari TextField
                int nim = Integer.parseInt(frameMahasiswa.getTextNim().getText().trim());

                // Lakukan validasi tambahan untuk comboProdi dan comboKabKot
                String prodi = (String) frameMahasiswa.getComboProdi().getSelectedItem();
                String kabKot = (String) frameMahasiswa.getComboKabKot().getSelectedItem();

                if (prodi.equals(" ") || kabKot.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Silakan lengkapi data..", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Lakukan operasi update
                    ModelMahasiswa mhs = new ModelMahasiswa();
                    mhs.setNim(nim);
                    mhs.setNama(frameMahasiswa.getTextNama().getText().trim());
                    mhs.setAlamat(frameMahasiswa.getTextAlamat().getText().trim());
                    mhs.setProdi(prodi);
                    mhs.setKota(kabKot);

                    // Mendapatkan jenis kelamin dari button group
                    if (frameMahasiswa.getBtnLaki().isSelected()) {
                        mhs.setJenisKelamin("Laki-Laki");
                    } else if (frameMahasiswa.getBtnPerempuan().isSelected()) {
                        mhs.setJenisKelamin("Perempuan");
                    }

                    // Mendapatkan angkatan dari button group
                    if (frameMahasiswa.getBtn62().isSelected()) {
                        mhs.setAngkatan("62");
                    } else if (frameMahasiswa.getBtn63().isSelected()) {
                        mhs.setAngkatan("63");
                    } else if (frameMahasiswa.getBtn63().isSelected()) {
                        mhs.setAngkatan("64");
                    } else  {
                        mhs.setAngkatan("65");
                    }

                    // Lakukan penyimpanan data mahasiswa ke database atau data structure
                    interfaceMahasiswa.update(mhs);

                    JOptionPane.showMessageDialog(null, "Data Berhasil Di-update!");
                }

            } catch (NumberFormatException e) {
                // Jika nim bukan angka, tampilkan pesan error
                JOptionPane.showMessageDialog(null, "NIM harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pastikan Semua Field Telah Diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // hapus data pada database
    public void delete(){
        // Memeriksa apakah TextField NIM tidak kosong
        boolean nimFilled = !frameMahasiswa.getTextNim().getText().trim().isEmpty();

        if (nimFilled) {
            try {
                // Konversi NIM dari string ke integer
                int nim = Integer.parseInt(frameMahasiswa.getTextNim().getText().trim());

                // Memanggil metode delete di DAO
                interfaceMahasiswa.delete(nim);

                // Tampilkan pesan sukses
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");

                // Reset form setelah penghapusan
                reset();

            } catch (NumberFormatException e) {
                // Jika NIM bukan angka, tampilkan pesan error
                JOptionPane.showMessageDialog(null, "NIM harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Jika NIM kosong, tampilkan pesan kesalahan
            JOptionPane.showMessageDialog(null, "Field NIM tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    // cari data
    public void isiTabelCariNama(){
        listMahasiswa = interfaceMahasiswa.getCariNama(frameMahasiswa.getTextCari().getText());
        ModelTabelMahasiswa mhs = new ModelTabelMahasiswa(listMahasiswa);
        frameMahasiswa.getTabelMhs().setModel(mhs);
    }
    
    public void cariNama(){
        if(!frameMahasiswa.getTextCari().getText().isEmpty()){
            interfaceMahasiswa.getCariNama(frameMahasiswa.getTextCari().getText());
            isiTabelCariNama();
        }else{
            JOptionPane.showMessageDialog(frameMahasiswa,"Silakan Pilih Data!");
        }
    }
    
    public void refresh(){
        frameMahasiswa.getTextCari().setText(" ");
    }
    
    // Fungsi untuk mencetak data ke CSV
    public void printToCSV() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(frameMahasiswa);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".csv")) {
            selectedFile = new File(filePath + ".csv");
        }
        try {
            frameMahasiswa.printToCSV(selectedFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            frameMahasiswa.showMessage("Error printing to CSV: " + ex.getMessage());
        }
        }
    }
    
    public void updateCount() {
        int count = interfaceMahasiswa.getCount();
    }
    
}

    

    

