package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class VertexMayhem {

    SceneHandler sc;

    // constants and angles
    float cvScale = 20;
    float angle = 0;
    float a, n, m, c;
    int totalVertices = 175;
    float offset = 0;
    
    // two dimensional pvector matrices
    PVector[][] shapeMatrix;
    PVector[][] shapeMatrix2;
    int rows, cols;
    
    public VertexMayhem(SceneHandler sc) {
        this.sc = sc;
        
    }

    public void setupShape() {
        rows = (int) (sc.height / cvScale);
        cols = (int) (sc.width / cvScale);

        shapeMatrix = new PVector[totalVertices + 1][totalVertices + 1];
        
    }    

    /*
        based of Roger Bagula and Paul Bourkes Gray's Klein Bottle formulas
        http://paulbourke.net/geometry/toroidal/

        p.x = (a + cos(n*u/2.0) * sin(v) - sin(n*u/2.0) * sin(2*v)) * cos(m*u/2.0)
        p.y = (a + cos(n*u/2.0) * sin(v) - sin(n*u/2.0) * sin(2*v)) * sin(m*u/2.0)
        p.z = sin(n*u/2.0) * sin(v) + cos(n*u/2.0) * sin(2*v)
        0 <= u <= 4 π
        0 <= u <= 2 π 
    */
    public PVector grayKleinBottle(float u, float v, float a, float n, float m) {
        // proportionality for display
        float r = sc.width/2;
        float vortxScl = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 10, 45);

        // calculations of x, y,z coordinates of the Gray's Klein bottle
        float x = vortxScl * (a + PApplet.cos(n * u / 2.0f) * PApplet.sin(v) - PApplet.sin(n * u / 2.0f) * PApplet.sin(2 * v)) * PApplet.cos(m * u / 2.0f) + r ;
        float y = vortxScl * (a + PApplet.cos(n * u / 2.0f) * PApplet.sin(v) - PApplet.sin(n * u / 2.0f) * PApplet.sin(2 * v)) * PApplet.sin(m * u / 2.0f) + r ;
        float z = vortxScl * PApplet.sin(n * u / 2.0f) * PApplet.sin(v) + PApplet.cos(n * u / 2.0f) * PApplet.sin(2 * v) + r ;
        
        // return pvector
        return new PVector(x, y, z);
    }

    public PVector sinusoidalSpiral(float theta, float c) {
        float r = sc.width/2;
        float vortxScl = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 32, 35);
        
        float x = vortxScl * PApplet.cos(theta) + r;
        float y = vortxScl * PApplet.sin(theta) + r;
        float z = vortxScl * (c * theta) + r ;

        return new PVector(x, y, z);
    }

    public void display() {

        sc.calculateAverageAmplitude();

        for(int i=0; i<totalVertices;i ++) {
            float u = PApplet.map(i, 0, totalVertices, 0, PApplet.TWO_PI + PApplet.TWO_PI);
            for(int j=0; j<totalVertices;j ++) {
                float v = PApplet.map(j, 0, totalVertices, 0, PApplet.TWO_PI);
                
                // randomize constants relative to amplitude
                float a = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 1.5f, 7.5f);
                float m = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 1, 6f);
                float n = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 2.1f, 6f);

                // assign the PVector to the shape matrix 
                PVector vect = new PVector(0, 0, 0);
                vect = grayKleinBottle(u, v, a, n, m);
                shapeMatrix[i][j] = vect; 
        
            }
            
        }

        sc.background(0);
        sc.lights();
        sc.noFill();

        offset += 5;
        for(int i=0; i<totalVertices - 2; i++) {
            float hu = PApplet.map(i, 0, totalVertices, 0, 255*6);
            sc.stroke((hu + offset) % 255, 255, 255);
            sc.strokeWeight(0.7f);

            // begin custom shape and set Triangles to join vertices
            sc.beginShape(PApplet.TRIANGLES);
            for(int j=0; j<totalVertices; j++) {

                // first vertex
                PVector v1 = shapeMatrix[i][j];
                sc.vertex(v1.x, v1.y, v1.z);

                // second vertex
                PVector v2 = shapeMatrix[i + 1][j];
                sc.vertex(v2.x, v2.y, v2.z);

                // third vertex - added some depth to this vertex in the z dimension
                PVector v3 = shapeMatrix[i + 1][j];
                sc.vertex(v3.x, v3.y, v3.z / 2);

            }
            // end custom shape
            sc.endShape();
        }
    }
    
}
