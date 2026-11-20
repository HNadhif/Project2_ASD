package src.StrukturData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph {
    private static final String filepath1 = "dokumen/matakuliah.csv";
    private static final String filepath2 = "dokumen/prasyarat.csv";
    private static final String delimeter = ";";
    int size = 0;
    DLL[] vertex;

    public Graph(){
        load();
    }

    private void load(){
        // Hitung banyak Mapel
        try (BufferedReader br = new BufferedReader(new FileReader(filepath1))){
            String header = br.readLine();

            String line;
            while((line = br.readLine()) != null){
                size++;
            }
        } catch(IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }

        vertex = new DLL[size];

        // Inisialisasi Mapel
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath1))){
            String header = br.readLine();

            String line;
            while((line = br.readLine()) != null){
                String data[] = line.split(delimeter);

                int sks = Integer.parseInt(data[2].trim());
                int semester = Integer.parseInt(data[3].trim());


                vertex[index++].addLast(new Node(data[0].trim(), data[1].trim(), sks, semester, data[4].trim()));
            }
        } catch(IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }

        // Inisialisasi Prasyarat Mapelnya
        try (BufferedReader br = new BufferedReader(new FileReader(filepath2))) {
            String header = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(delimeter);
                if (data.length < 2) continue;

                String target = data[data.length - 1].trim();
                int indexTarget = -1;

                // Cari Target
                for (int i = 0; i < size; i++) {
                    if (vertex[i].head.data.kode.equals(target)) {
                        indexTarget = i;
                        break;
                    }
                }

                // Skip jika target gk ada
                if (indexTarget == -1) {
                    continue;
                }

                // Loop source jg
                for (int j = 0; j < data.length - 1; j++) {
                    String source = data[j].trim();
                    if (source.isEmpty()) continue;

                    int indexSource = -1;
                    for (int i = 0; i < size; i++) {
                        if (vertex[i].head.data.kode.equals(source)) {
                            indexSource = i;
                            break;
                        }
                    }

                    // Hubung source ke target
                    if (indexSource != -1) {
                        Matkul m = vertex[indexSource].getMatkulHead();
                        vertex[indexTarget].addLast(new Node(m));
                    } else {
                        continue;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }
}
