package android.fortunaalya.resto;

public class GetSetReservation {
    private String id;
    private String no_meja;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private String kapasitas;
    private String kondisi;

    public GetSetReservation(String id, String no_meja, String tanggal_mulai, String tanggal_selesai, String kapasitas, String kondisi) {
        this.id = id;
        this.no_meja = no_meja;
        this.tanggal_mulai = tanggal_mulai;
        this.tanggal_selesai = tanggal_selesai;
        this.kapasitas = kapasitas;
        this.kondisi = kondisi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo_meja() {
        return no_meja;
    }

    public void setNo_meja(String no_meja) {
        this.no_meja = no_meja;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }
}
