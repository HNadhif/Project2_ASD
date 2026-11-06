package src.StrukturData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    private static final String filepath1 = "dokumen/matakuliah.csv";
    private static final String filepath2 = "dokumen/prasyarat.csv";
    private static final String delimeter = ";";
    DLL vertices;

    public Graph(){
        vertices = new DLL();
    }

    public void loadData(){
        try (BufferedReader br = new BufferedReader(new FileReader(filepath1))){
            String header = br.readLine();

            String line;
            while((line = br.readLine()) != null){
                String data[] = line.split(delimeter);

                int sks = Integer.parseInt(data[2].trim());
                int semester = Integer.parseInt(data[3].trim());

                vertices.addLast(new Node(data[0].trim(), data[1].trim(), sks, semester, data[4].trim()));
            }
        } catch(IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }

    public void loadPrasyarat(){
        try (BufferedReader br = new BufferedReader(new FileReader(filepath2))){
            String header = br.readLine();

            String line;
            while((line = br.readLine()) != null){
                String data[] = line.split(delimeter);

                Node target = vertices.find(data[data.length - 1]);

                for(int i = 0; i<data.length - 1; i++){
                    Node nextNode = vertices.find(data[i]);
                    target.data.nodeG.addPrasyarat(nextNode);
                }
            }
        } catch(IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }
}
