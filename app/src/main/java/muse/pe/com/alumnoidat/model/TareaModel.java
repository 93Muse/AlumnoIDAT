package muse.pe.com.alumnoidat.model;

public class TareaModel {

    private int id;
    private String titulo, descripcion, curso, profesor, vencimiento;

    public TareaModel() {
    }

    public TareaModel(String titulo, String curso) {
        this.titulo = titulo;
        this.curso = curso;
    }

    public TareaModel(int id, String titulo, String descripcion, String curso, String profesor, String vencimiento) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.curso = curso;
        this.profesor = profesor;
        this.vencimiento = vencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }
}
