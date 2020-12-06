package android.fortunaalya.resto;

public class GetSetMenuCheckout {
    private String id;
    private String nama_makanan;
    private String harga;
    private String jumlah;
    private String total;

    public GetSetMenuCheckout(String id, String nama_makanan, String harga, String jumlah, String total) {
        this.id = id;
        this.nama_makanan = nama_makanan;
        this.harga = harga;
        this.jumlah = jumlah;
        this.total = total;
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

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
