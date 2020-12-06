package android.fortunaalya.resto;

public class GetSetMenu {
    private String id;
    private String nama_makanan;
    private String harga;

    public GetSetMenu(String id, String nama_makanan, String harga) {
        this.id = id;
        this.nama_makanan = nama_makanan;
        this.harga = harga;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
