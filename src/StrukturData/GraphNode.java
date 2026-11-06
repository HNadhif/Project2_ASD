package src.StrukturData;

public class GraphNode {
    public Matkul matkul;
    public DLL prasyarat;

    public GraphNode(Matkul m){
        matkul = m;
        prasyarat = new DLL();
    }

    public void addPrasyarat(Node n){
        prasyarat.addLast(n);
    }
}
