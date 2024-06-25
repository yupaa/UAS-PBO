/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daoInterface;

import java.util.List;
import model.ModelMahasiswa;

/**
 *
 * @author 222212525 // Aulia Zulfa Kurniawan
 */
public interface InterfaceMahasiswa {
    public void insert(ModelMahasiswa mhs);
    public void update(ModelMahasiswa mhs);
    public void delete(int Nim);
    public boolean isNimExist(int nim);
    public void export(List<ModelMahasiswa> mahasiswas, String filePath); 
    int getCount();
    int getLakiCount();
    int getPrCount();
    int getD3Count();
    int getSTCount();
    int getKSCount();
    int getKotaCount();
    int getSlCount();
    int getBtCount();
    int getKpCount();
    int getGkCount();
    int get62Count();
    int get63Count();
    int get64Count();
    int get65Count();
   
    
    public List<ModelMahasiswa> getAll();
    public List<ModelMahasiswa> getCariNama(String nama);
}
