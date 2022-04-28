package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class MorphShape {
    
    PVector position;
    SceneHandler sc;

    float radius = 0, angle = 0;

    public MorphShape(float x, float y, float r, SceneHandler sc) {
        position = new PVector(x, y);
        radius = r;
        this.sc = sc;
    }

    public void display() {
        sc.calculateAverageAmplitude();
        float newRadius = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 70, 350);
        sc.noFill();
        sc.stroke(sc.frameCount % 255, 255,255);
        
        sc.pushMatrix();
        sc.translate(sc.width / 2, sc.height/2);
        sc.lights();

        sc.rotateX(angle);
        sc.rotateZ(angle);
        
        sc.sphere(newRadius);

        sc.popMatrix();

        angle += 0.02f;
        
    }
}
