package manager;

public class Camera implements CameraInterface {

    private double x, y;
    private int frameNumber;
    private boolean shaking;

    public Camera(){
        this.x = 0;
        this.y = 0;
        this.frameNumber = 25;
        this.shaking = false;
    }
    @Override
    public double getX() {
        return x;
    }
    @Override
    public void setX(double x) {
        this.x = x;
    }
    @Override
    public double getY() {
        return y;
    }
    @Override
    public void setY(double y) {
        this.y = y;
    }
    @Override
    public void shakeCamera() {
        shaking = true;
        frameNumber = 60;
    }
    @Override
    public void moveCam(double xAmount, double yAmount){
        if(shaking && frameNumber > 0){
            int direction = (frameNumber%2 == 0)? 1 : -1;
            x = x + 4 * direction;
            frameNumber--;
        }
        else{
            x = x + xAmount;
            y = y + yAmount;
        }

        if(frameNumber < 0)
            shaking = false;
    }
}
