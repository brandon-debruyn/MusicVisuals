package D20123654;

import processing.core.PApplet;

public class SolidsReformation {
    
    SceneHandler sc;

    float a = 150;
    float angle = 0;
    float n = 0;
    float c = 4;
    float sphereR = 20;

    public SolidsReformation(SceneHandler sc) {
        this.sc = sc;
        
    }

    public void display() {        
        
        sc.calculateAverageAmplitude();
        sc.background(0);
        
        for(int i=0; i< sc.width / a;i ++) {
            
            sc.pushMatrix();
            
            sc.translate(sc.width / 2, sc.height/2);
            sc.lights(); 

            sc.stroke(sc.random(0, 255),255,255);
            sc.strokeWeight(2);
            sc.noFill();
                
            for(int j=0; j<sc.height /a ; j++) {
                
                float az = (n % 500) * 137.6f;
                float r = c * PApplet.sqrt(n);
                float x = r * PApplet.cos(az); 
                float y = r * PApplet.sin(az); 
                
                float rad = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 0, 170);
                
                sc.ellipse(x,  y, rad, rad);
                sc.rect(x, y, x + (rad / 10), y + (rad / 10));
                sc.line(x, y,  PApplet.cos(x + rad), PApplet.cos(y + rad));

                
                float n2 = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, -0.1f, 0.5f);

                if(n < -0.75f) {
                    n = -0.2f;
                }
                n += n2;
            }
            
            sc.popMatrix();
        }
        
        angle += 0.0055f;
        
        
    }
}
