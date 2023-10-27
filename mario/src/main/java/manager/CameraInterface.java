package manager;

public interface CameraInterface {
    double getX();
    double getY();
    void setX(double x);
    void setY(double y);
    void shakeCamera();
    void moveCam(double xAmount, double yAmount);
}
