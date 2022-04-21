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
        
        
        //sc.noFill();
        sc.ellipseMode(PApplet.CENTER);
        sc.ellipse(position.x, position.y, radius, radius);
    }
}
