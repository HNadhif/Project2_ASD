package src.StrukturData;

public class GraphNode {
    public Matkul matkul;
    public Node[] prasyarat;

    public GraphNode(Matkul m){
        matkul = m;
        prasyarat = new Node[5];
    }

    public void addPrasyarat(Node n){
        for(int i=0; i<5; i++){
            if(prasyarat[i] == null){
                prasyarat[i] = n;
                break;
            }
        }
    }
}
