package tarea.simulacion.tema4.MonteCarlo;

public class Pelota {

    private final double GRAVEDAD = 300;
    private final double COEFICIENTE_REBOTE = 0.8;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radio;

    public Pelota(double x, double y, double radio) {
        this.x = x;
        this.y = y;
        this.radio = radio;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadio() {
        return radio;
    }

    public void update(double deltaTime) {
        this.vy += GRAVEDAD * deltaTime;

        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void verificarColision(double clavox, double clavoy, double radioClavo) {
        double dx = x - clavox;
        double dy = y - clavoy;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        double distanciaMinima = radio + radioClavo;

        if (distancia < distanciaMinima && distancia > 0.1) {
            // Normaliza la dirección del rebote
            double normalX = dx / distancia;
            double normalY = dy / distancia;

            // Producto punto: velocidad en dirección de la normal
            double dotProduct = vx * normalX + vy * normalY;

            // Solo rebota si se acerca (dotProduct negativo = se mueve hacia el clavo)
            if (dotProduct < 0) {
                // Refleja la velocidad
                vx = (vx - 2 * dotProduct * normalX) * COEFICIENTE_REBOTE;
                vy = (vy - 2 * dotProduct * normalY) * COEFICIENTE_REBOTE;
            }

            // Empuja la pelota afuera del clavo
            double overlap = distanciaMinima - distancia;
            x += normalX * overlap;
            y += normalY * overlap;
        }
    }

    public void darVelocidadInicial() {
        // Velocidad horizontal aleatoria (-50 a +50)
        this.vx = (Math.random() - 0.5) * 100;
        this.vy = 0;  // Empieza cayendo
    }
}
