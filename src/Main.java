package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import src.StrukturData.Graph;

public class Main {
    private static final String FOLDER_MAHASISWA = "dokumen/mahasiswa";

    public static void main(String[] args) {
        Graph graph = new Graph();
        File folder = new File(FOLDER_MAHASISWA);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("Tidak ada file mahasiswa di folder " + FOLDER_MAHASISWA);
            return;
        }

        // Buat ngurutin nama file biar rapi di program
        Arrays.sort(files);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Pilih Data Mahasiswa ===");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + ". " + files[i].getName());
            }
            System.out.println("0. Keluar");
            System.out.print("Pilih nomor: ");

            int pilihan = scanner.nextInt();
            if (pilihan == 0) {
                System.out.println("Program selesai.");
                break;
            }
            if (pilihan < 1 || pilihan > files.length) {
                System.out.println("Pilihan tidak valid.");
                continue;
            }

            File fileDipilih = files[pilihan - 1];
            String semesterBerikutnya = loadMahasiswa(graph, fileDipilih.getPath());

            System.out.println("\nFile: " + fileDipilih.getName());
            System.out.println("Semester berikutnya: " + semesterBerikutnya);

            // Nanti tugas nomer 2 disini
        }
        scanner.close();
    }

    private static String loadMahasiswa(Graph graph, String filepath) {
        String semesterBerikutnya = "";
        String[] daftarLulus = new String[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length < 2) {
                    continue;
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                if (key.equalsIgnoreCase("semester_berikutnya")) {
                    semesterBerikutnya = value;
                } else if (key.equalsIgnoreCase("lulus")) {
                    if (!value.isEmpty()) {
                        daftarLulus = value.split(",");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file mahasiswa: " + e.getMessage());
        }

        graph.setLulus(daftarLulus);
        return semesterBerikutnya;
    }
}