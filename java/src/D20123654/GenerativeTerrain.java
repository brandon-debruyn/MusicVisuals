package D20123654;

import processing.core.PApplet;

public class GenerativeTerrain {

    SceneHandler sc;
    float angle = 0;
    int maxIterations = 500;
    
    float cx = -0.7f;
    float cy = 0.27015f;

    float moveX = 0.01f;
    float moveY = 0.01f;

    double zx0, zy0;

    
    public GenerativeTerrain(SceneHandler sc) {
        
        this.sc = sc;
    }

    

    public void display() {
        // pixels[] => x (0, width) , y (0, height) => x + y * width
        sc.background(0);

        angle += 0.1f;

        sc.loadPixels();
        float resZoom;
        sc.calculateAverageAmplitude();
        for(int i=0; i<sc.width; i++) {
            for(int j=0; j<sc.height; j++) {
                resZoom = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.06f, 30, 55);
                
                double zx = (1.5 * (i - sc.width / 2) / (0.5  * resZoom * sc.width) + moveX);
                double zy = ((j - sc.height / 2) / (0.5  * resZoom * sc.height) + moveY);
                
                
                double x = 0;
                double y = 0;
                int iteration = 0;
                int maxIter = 500;

                while(x*x + y * y <= 2 * 2 && iteration < maxIter) {
                    zx0 = zx;
                    zy0 = zy;

                    zx = zx0 *zx0 - zy0*zy0 + cx;
                    zy = 2 * zx0 * zy0 + cy;

                    if((zx * zx + zy * zy) > 4) break;

                    /*
                    double xtemp = x*x - y*y + zx;
                    y = 2 * x * y + zy;
                    x = xtemp;
                    */
                    iteration++;
                }

                float c = sc.color(iteration % 256 , 255, 255);
                sc.pixels[i + j * sc.width] = (int) c;
                
            }
            sc.updatePixels();
        }

        sc.updatePixels();

       
    }
}
