package Domain;

public class Table {
    private int id_table;

    public Table(int id_table) {
        this.id_table = id_table;
    }

    public int getId_table() {
        return id_table;
    }

    @Override
    public String toString() {
        return "Table number: " + id_table;
    }
}
