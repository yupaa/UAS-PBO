/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImplement;


import java.sql.Connection;
import daoInterface.InterfaceMahasiswa;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import koneksi.Koneksi;
import model.ModelMahasiswa;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public class DaoMahasiswa implements InterfaceMahasiswa{
    
    Connection connection;
    final String insert = "INSERT INTO mahasiswa (nim,nama,jk,angkatan,prodi,alamat,kota) VALUES (?,?,?,?,?,?,?);";
    final String update = "UPDATE mahasiswa SET nama=?, jk=?, angkatan=?, prodi=?, alamat=?, kota=? WHERE nim=?;";
    final String delete = "DELETE FROM mahasiswa WHERE nim=?;";
    final String select = "SELECT * FROM mahasiswa;";
    final String cariNama = "SELECT * FROM mahasiswa WHERE nama like ?;";
    
    public DaoMahasiswa(){
        connection = Koneksi.getKoneksi();
    }

    @Override
    public void insert(ModelMahasiswa mhs) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(insert);
            statement.setInt(1, mhs.getNim());
            statement.setString(2, mhs.getNama());
            statement.setString(3, mhs.getJenisKelamin());
            statement.setString(4, mhs.getAngkatan());
            statement.setString(5, mhs.getProdi());
            statement.setString(6, mhs.getAlamat());
            statement.setString(7, mhs.getKota());
            
            // Menjalankan statement SQL untuk melakukan insert data
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new student was inserted successfully!");
            }
            //statement.executeUpdate();
            //ResultSet rs = statement.getGeneratedKeys();
            
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch(SQLException ex){
                ex.printStackTrace();;
            }
        }
    }

    @Override
    public void update(ModelMahasiswa mhs) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(update);
            statement.setInt(7, mhs.getNim());
            statement.setString(1, mhs.getNama());
            statement.setString(2, mhs.getJenisKelamin());
            statement.setString(3, mhs.getAngkatan());
            statement.setString(4, mhs.getProdi());
            statement.setString(5, mhs.getAlamat());
            statement.setString(6, mhs.getKota());
            
            statement.executeUpdate();            
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch(SQLException ex){
                ex.printStackTrace();;
            }
        }
    }

    @Override
    public void delete(int Nim) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(delete);
            statement.setInt(1, Nim);
            statement.executeUpdate();            
        } catch(SQLException ex){
            ex.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch(SQLException ex){
                ex.printStackTrace();;
            }
        }
    }

    @Override
    public List<ModelMahasiswa> getAll() {
        List<ModelMahasiswa> lb = null;
        try{
            lb = new ArrayList<ModelMahasiswa>(); 
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()){
                ModelMahasiswa mhs = new ModelMahasiswa ();
                mhs.setNim(rs.getInt("nim"));
                mhs.setNama(rs.getString("nama"));
                mhs.setJenisKelamin(rs.getString("jk"));
                mhs.setAngkatan(rs.getString("angkatan"));
                mhs.setProdi(rs.getString("prodi"));
                mhs.setAlamat(rs.getString("alamat"));
                mhs.setKota(rs.getString("kota"));
                lb.add(mhs);
                
            }
        } catch(SQLException ex){
            Logger.getLogger(DaoMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return lb;
    }

    @Override
    public List<ModelMahasiswa> getCariNama(String nama) {
        List<ModelMahasiswa> lb = null;
        String query = "SELECT * FROM mahasiswa WHERE nama LIKE ?";
        try{
            lb = new ArrayList<ModelMahasiswa>(); 
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,"%"+ nama + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ModelMahasiswa mhs = new ModelMahasiswa ();
                mhs.setNim(rs.getInt("nim"));
                mhs.setNama(rs.getString("nama"));
                mhs.setJenisKelamin(rs.getString("jk"));
                mhs.setAngkatan(rs.getString("angkatan"));
                mhs.setProdi(rs.getString("prodi"));
                mhs.setAlamat(rs.getString("alamat"));
                mhs.setKota(rs.getString("kota"));
                lb.add(mhs);
                
            }
        } catch(SQLException ex){
            Logger.getLogger(DaoMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return lb;
    }
    
    @Override
    public boolean isNimExist(int nim) {
    String query = "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?";
    try (PreparedStatement st = connection.prepareStatement(query)) {
        st.setInt(1, nim);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException ex) {
        Logger.getLogger(DaoMahasiswa.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
}

    @Override
    public void export(List<ModelMahasiswa> mahasiswas, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Header CSV
            writer.write("NIM,Nama,Jenis Kelamin,Angkatan,Prodi,Alamat,Kota");
            writer.newLine();

            // Data mahasiswa
            for (ModelMahasiswa mhs : mahasiswas) {
                writer.write(String.format("%d,%s,%s,%s,%s,%s,%s",
                        mhs.getNim(),
                        escapeSpecialCharacters(mhs.getNama()),
                        escapeSpecialCharacters(mhs.getJenisKelamin()),
                        escapeSpecialCharacters(mhs.getAngkatan()),
                        escapeSpecialCharacters(mhs.getProdi()),
                        escapeSpecialCharacters(mhs.getAlamat()),
                        escapeSpecialCharacters(mhs.getKota())));
                writer.newLine();
            }

            System.out.println("Data berhasil di-export ke file CSV: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menghindari masalah karakter khusus dalam CSV
    private String escapeSpecialCharacters(String value) {
        // Menghindari karakter khusus seperti koma atau baris baru
        return value.replace(",", "\\,").replace("\n", "\\n");
    }

    @Override
    public int getCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getLakiCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE jk='Laki-Laki'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getPrCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE jk='Perempuan'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getD3Count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE prodi='DIII Statistika'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getSTCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE prodi='DIV Statistika'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getKSCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE prodi='DIV Komputasi Statistik'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getKotaCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE kota='Kota Yogyakarta'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getSlCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE kota='Kabupaten Sleman'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getBtCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE kota='Kabupaten Bantul'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getKpCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE kota='Kabupaten Kulon Progo'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int getGkCount() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE kota='Kabupaten Gunungkidul'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int get62Count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE angkatan='62'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int get63Count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE angkatan='63'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int get64Count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE angkatan='64'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int get65Count() {
        int count = 0;
        String query = "SELECT COUNT(*) FROM mahasiswa WHERE angkatan='65'";
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;}

    
}
