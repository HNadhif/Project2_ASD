package src.StrukturData;

public class Node {
    public Matkul data;
    public Node next;
    public Node prev;

    public Node(String kode, String nama, int sks, int semester, String jenis) {
        this.data = new Matkul(kode, nama, sks, semester, jenis);
        this.next = null;
        this.prev = null;
    }
}