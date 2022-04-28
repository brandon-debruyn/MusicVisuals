package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class MorphShape {
    
    PVector position;
    SceneHandler sc;

    // initial radius and rotation angle
    float radius = 400, angle = 0;

    public MorphShape(float x, float y, SceneHandler sc) {
        this.position = new PVector(x, y);
        this.sc = sc;
    }

    public void display() {

        // create radius based on amplitude of music
        sc.calculateAverageAmplitude();
        float newRadius = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 70, 150);

        sc.noFill();
        sc.stroke(sc.frameCount % 255, 255,255);
        
        // push the transformation matrix onto the stack
        sc.pushMatrix();
        // translate the object in the x and y direction
        sc.translate(sc.width / 2, sc.height/2);
        sc.lights();

        // rotate object about the x and z axis
        sc.rotateX(angle);
        sc.rotateZ(angle);
        
        // create sphere + cube around it
        sc.sphere(newRadius);
        sc.box(2 * newRadius);
        
        // pop transformation matrix from the stack
        sc.popMatrix();

        // increment rotation angle
        angle += 0.02f;
        
    }
}
