package ba.unsa.etf.rpr.projekat;

public class Material {
    private int id;
    private String nameMaterial;
    private String subject;
    private String type;


    public Material(int id, String nameMaterial, String subject, String type) {
        this.id = id;
        this.nameMaterial = nameMaterial;
        this.subject = subject;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameMaterial() {
        return nameMaterial;
    }

    public void setNameMaterial(String nameMaterial) {
        this.nameMaterial = nameMaterial;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}