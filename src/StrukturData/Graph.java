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

    public void resetLulus() {
        for (int i = 0; i < size; i++) {
            if (vertex[i] != null && !vertex[i].isEmpty()) {
                vertex[i].head.data.lulus = false;
            }
        }
    }

    public void setLulus(String[] daftarKode) {
        // Set semua head di array jadi false dulu
        resetLulus();

        if (daftarKode == null) {
            return;
        }

        for (String kode : daftarKode) {
            String target = kode.trim();
            if (target.isEmpty()) {
                continue;
            }

            // Urut/loop array vertex untuk mencari kode yang cocok
            for (int i = 0; i < size; i++) {
                if (vertex[i] != null && !vertex[i].isEmpty()
                        && vertex[i].head.data.kode.equalsIgnoreCase(target)) {
                    vertex[i].head.data.lulus = true;
                    break;
                }
            }
        }
    }

    private boolean sesuaiSemesterBerikutnya(Matkul m, String semesterBerikutnya) {
        if (semesterBerikutnya == null || semesterBerikutnya.trim().isEmpty()) {
            return true;
        }
        String target = semesterBerikutnya.trim();
        return m.jenis.equalsIgnoreCase(target) || m.jenis.equalsIgnoreCase("Keduanya");
    }

    private boolean semuaPrasyaratLulus(DLL list) {
        Node curr = list.head.next;
        while (curr != null) {
            if (!curr.data.lulus) {
                return false;
            }
            curr = curr.next;
        }
        return true;
    }

    private String getPrasyaratBelumLulus(DLL list) {
        StringBuilder sb = new StringBuilder();
        Node curr = list.head.next;
        while (curr != null) {
            if (!curr.data.lulus) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(curr.data.kode);
            }
            curr = curr.next;
        }
        return sb.toString();
    }

    public void cekMatkulBisaDiambil(String semesterBerikutnya) {
        int semesterTertinggi = 0;
        for (int i = 0; i < size; i++) {
            if (vertex[i] != null && !vertex[i].isEmpty() && vertex[i].head.data.lulus) {
                if (vertex[i].head.data.semester > semesterTertinggi) {
                    semesterTertinggi = vertex[i].head.data.semester;
                }
            }
        }

        System.out.println("Semester tertinggi yang sudah lulus: " + semesterTertinggi);

        System.out.println("\n--- Mata Kuliah yang BISA Diambil ---");
        boolean adaBisa = false;
        for (int i = 0; i < size; i++) {
            if (vertex[i] == null || vertex[i].isEmpty()) {
                continue;
            }
            Matkul m = vertex[i].head.data;
            if (!m.lulus && sesuaiSemesterBerikutnya(m, semesterBerikutnya) && semuaPrasyaratLulus(vertex[i])) {
                System.out.printf("- [%s] %s (%d SKS, Sem %d, %s)%n",
                        m.kode, m.nama, m.sks, m.semester, m.jenis);
                adaBisa = true;
            }
        }
        if (!adaBisa) {
            System.out.println("(Tidak ada mata kuliah yang bisa diambil)");
        }

        System.out.println("\n--- Mata Kuliah yang TIDAK BISA Diambil ---");
        boolean adaTidakBisa = false;
        for (int i = 0; i < size; i++) {
            if (vertex[i] == null || vertex[i].isEmpty()) {
                continue;
            }
            Matkul m = vertex[i].head.data;
            if (!m.lulus && sesuaiSemesterBerikutnya(m, semesterBerikutnya) && !semuaPrasyaratLulus(vertex[i])) {
                System.out.printf("- [%s] %s (%d SKS, Sem %d, %s) -> Belum lulus prasyarat: %s%n",
                        m.kode, m.nama, m.sks, m.semester, m.jenis, getPrasyaratBelumLulus(vertex[i]));
                adaTidakBisa = true;
            }
        }
        if (!adaTidakBisa) {
            System.out.println("(Semua mata kuliah yang ditawarkan sudah memenuhi prasyarat)");
        }
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

                vertex[index] = new DLL();
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
