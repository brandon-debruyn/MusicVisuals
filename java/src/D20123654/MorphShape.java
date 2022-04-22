package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class MorphShape {
    
    PVector position;
    SceneHandler sc;

    float radius = 0;

    public MorphShape(float x, float y, float r, SceneHandler sc) {
        position = new PVector(x, y);
        radius = r;
        this.sc = sc;
    }


    
    public void display() {
        //sc.calculateAverageAmplitude();
        float c = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.05f, 0, 255);

        sc.fill(c, 255,255);
        //sc.stroke(c, 255,255);
        //sc.noStroke();
        sc.ellipseMode(PApplet.CENTER);
        sc.ellipse(position.x, position.y, radius, radius);
    }
}
