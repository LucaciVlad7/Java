package Domain;

public class Masina {
    private Integer idMasina;
    private String descriere;
    private Integer price;
    private Status status;

    public Integer getIdMasina() {
        return idMasina;
    }


    public Masina(Integer idMasina, String descriere, Integer price, Status status) {
        this.idMasina = idMasina;
        this.descriere = descriere;
        this.price = price;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Masina: " + descriere + '\'' +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
