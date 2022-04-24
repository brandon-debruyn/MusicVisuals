package D20123654;

import com.jogamp.graph.geom.Vertex;

import processing.core.PApplet;
import processing.core.PVector;


public class SolidsReformation {
    
    SceneHandler sc;

    float a = 20;
    float angle = 0;
    float[][] terrain;
    

    public SolidsReformation(SceneHandler sc) {
        this.sc = sc;
        this.terrain = new float[sc.width][sc.height];
    }

    public void display() {
        
        

        sc.background(0);
        sc.stroke(255,255,255);
        sc.noFill();

        sc.translate(sc.width/2, sc.height/2);
        sc.rotateX(PApplet.PI / 3);

        
        sc.translate(-sc.width / 2, -sc.height/2);
        /*
        x = u - u^3 / 3 + u v^2
        y = v - v^3 / 3 + v u^2
        z = u^2 - v^2
        -2 <= u <= 2, -2 <= v <= 2 
        */
        
        for(int i=0; i< sc.width / a;i ++) {
            
            float v = PApplet.map(i, 0, sc.width/a, -PApplet.PI, PApplet.PI);

            for(int j=0; j<sc.height /a ; j++) {
                float u = PApplet.map(j, 0, sc.height/a, -PApplet.PI, PApplet.PI);
                float o = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.06f, 1, 250);

                float x = 2 * o * PApplet.sin(3 * u) / 2 + PApplet.cos(v);
                float y = 2 * o *(PApplet.sin(u) + 2 * PApplet.sin(2 * u)) / (2 + PApplet.cos(v + 2 * PApplet.PI/3));
                float z = o * (PApplet.cos(u) - 2 * PApplet.cos(2 * u)) * (2 + PApplet.cos(v + 2 * PApplet.PI /3)) / 4;

                sc.point(x * a , y * a, z * a );
                

            }
            
        }
    }
}
