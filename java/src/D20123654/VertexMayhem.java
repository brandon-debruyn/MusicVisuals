package D20123654;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class VertexMayhem {

    SceneHandler sc;
    float cvScale = 20;
    float angle = 0;
    
    float[][] shapeMatrix;
    int rows, cols;
    

    public VertexMayhem(SceneHandler sc) {
        this.sc = sc;
        
    }

    public void setupShape() {
        rows = (int) (sc.height / cvScale);
        cols = (int) (sc.width / cvScale);

        shapeMatrix = new float[rows + 1][cols + 1];
    }    

    public void display() {

        sc.calculateAverageAmplitude();
        
        for(int i=0; i<rows;i ++) {
            
            for(int j=0; j<cols;j ++) {


                shapeMatrix[i][j] = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 0, 200);
                
            }
            
        }

        sc.background(0);
        
        sc.stroke(sc.frameCount % 255, 255, 255);
        
        
        sc.pushMatrix();
        sc.translate(sc.width/2, sc.height/2);
        
        sc.lights();
        sc.rotateX(angle);
        sc.rotateZ(angle);
        for(int i=0; i<rows-1; i++) {
            sc.beginShape(PApplet.TRIANGLE_STRIP);
            for(int j=0; j<cols; j++) {
                
                sc.vertex(j * cvScale, i * cvScale, shapeMatrix[i][j]);
                sc.vertex(j * cvScale, (i + 1) * cvScale, shapeMatrix[i][j + 1]);
                
            }
            sc.endShape();
        }
        sc.popMatrix();
        
        angle += 0.01f;
    }
    
}
