package src.StrukturData;

public class Matkul {
    public String kode;
    public String nama;
    public int sks;
    public int semester;
    public String jenis;       
    public boolean lulus;      
    public GraphNode nodeG;     

    public Matkul(String kode, String nama, int sks, int semester, String jenis) {
        this.kode = kode;
        this.nama = nama;
        this.sks = sks;
        this.semester = semester;
        this.jenis = jenis;
        this.lulus = false;    
        this.nodeG = new GraphNode(this);    
    }
}
