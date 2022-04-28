package D20123654;

import processing.core.PApplet;

public class JuliaGen {

    SceneHandler sc;
    float angle = 0;
    int maxIterations = 35;
    
    // f(zeta(phi)) = z^2 + c
    // zeta x, zeta y (x=> real, y=>imaginary)
    float zx0, zy0;

    // const (x=> real, y=>imaginary)
    float cx, cy;

    public JuliaGen(SceneHandler sc) {
        
        this.sc = sc;
    }

    public void display() {
        /*
            pixels[] => x (0, width) , y (0, height) => x + y * width

            pseudocode from wikipedia:

            R = escape radius  # choose R > 0 such that R**2 - R >= sqrt(cx**2 + cy**2)

            for each pixel (x, y) on the screen, do:   
            {
                zx = scaled x coordinate of pixel # (scale to be between -R and R)
                # zx represents the real part of z.
                zy = scaled y coordinate of pixel # (scale to be between -R and R)
                # zy represents the imaginary part of z.

                iteration = 0
                max_iteration = 1000
            
                while (zx * zx + zy * zy < R**2  AND  iteration < max_iteration) 
                {
                    xtemp = zx * zx - zy * zy
                    zy = 2 * zx * zy  + cy 
                    zx = xtemp + cx
                
                    iteration = iteration + 1 
                }
            
                if (iteration == max_iteration)
                    return black;
                else
                    return iteration;
            }
        */
        sc.background(0);

        /*
            Julia set for z^2 + 0.7885*exp(eia) 
        */
        cx = 0.7885f * PApplet.cos(angle );
        cy = PApplet.sin(angle );

        sc.calculateAverageAmplitude();
        float f = PApplet.map(200 * sc.getSmoothedAmplitude(), 0, 10.0f, 0.0f, 0.018f);

        angle += f;
        
        sc.loadPixels();
        
        for(int i=0; i<sc.width; i++) {
            for(int j=0; j<sc.height; j++) {
                /*
                    zx = scaled x coordinate of pixel
                    zy = scaled y coordinate of pixel 
                */
                
                float zx = (float) (1.5 * (i - sc.width / 2) / (0.5  * sc.width));
                float zy =  (float) ((j - sc.height / 2) / (0.5  * sc.height));
                
                
                float x = 0;
                float y = 0;
                int iteration = 0;

                // while zx^2 + zy^2 where zx^2 = (n)^2 for n << 2 and zy^2 = (real(m) * sqrt(i))^2 => (real(m)^2 * -1)
                while(x*x + y * y <= 2 * 2 && iteration < maxIterations) {
                    // temp zeta vars incase divergence boundary
                    zx0 = zx;
                    zy0 = zy;

                    // update temp zeta vars
                    zx = zx0 *zx0 - zy0*zy0 + cx; 
                    zy = 2 * zx0 * zy0 + cy;

                    // check for divergence boundary
                    if((zx * zx + zy * zy) > 4) break;

                    // iteration completed
                    iteration++;
                }

                
                float c = sc.color((iteration * 20) % 256 , 255, 255);
                sc.pixels[i + j * sc.width] = (int) c;  
                       
                sc.updatePixels();   
                            
            }         
        }   
 
    }
}
