package mx.itson.cruddemo;

public class Prueba2 {
    String idRegistroPrueba, nombre, fecha, distancia, vueltas;

    public Prueba2(String idRegistroPrueba, String nombre, String fecha,
                   String distancia, String vueltas){
        this.idRegistroPrueba = idRegistroPrueba;
        this.nombre = nombre;
        this.fecha = fecha;
        this.distancia = distancia;
        this.vueltas = vueltas;
    }

    public String getIdRegistroPrueba() {
        return idRegistroPrueba;
    }

    public void setIdRegistroPrueba(String idRegistroPrueba) {
        this.idRegistroPrueba = idRegistroPrueba;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getVueltas() {
        return vueltas;
    }

    public void setVueltas(String vueltas) {
        this.vueltas = vueltas;
    }
}
