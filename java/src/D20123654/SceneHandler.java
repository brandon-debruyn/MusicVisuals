package D20123654;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;

public class SceneHandler extends Visual {

    int mode = 0;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    int num_particles = 15;
    float radius = 400;

    float halfH = height / 2;

    SphereParticles[] particles = new SphereParticles[num_particles];
    MorphShape shape;
    
    public void settings() {
        size(800, 800, P3D);

    }

    public void setup() {
    
        startMinim();
        loadAudio("myMusic.mp3");

        colorMode(HSB);
    
        //noCursor();
        
        lerpedBuffer = new float[width];
        int posx = 120;

        shape = new MorphShape(width / 2, height / 2, radius, this);

        for(int i=0; i<num_particles; i++) {
            if(i <= 8) {
                particles[i] = new SphereParticles(posx, 0.0f, 40, this, shape);
                posx += 60;
            }
            else {
                
                particles[i] = new SphereParticles(posx, height, 40, this, shape);
                posx -= 120;
            }
        }
        
        
    }
    
    public void keyPressed()
    {
        if (key == ' ')
        {
            getAudioPlayer().cue(0);
            getAudioPlayer().play();
        }
    }

    

    public void draw() {
        //(\sqrt{(\sqrt{(x*\sin(b)+a*\cos(b))^{2}+(y*\cos(d)-c*\sin(d))^{2}}-4)^{2}+(y*\sin(d)+c*\cos(d))^{2}}-2)^{2}+(x*\cos(b)-a*\sin(b))^{2}=1
        background(0);

        getFFT().window(FFT.HAMMING);
        getFFT().forward(getAudioBuffer());


        int k = 0;

        for(int i=0; i<num_particles; i++) {
            particles[i].updateV();
            particles[i].display();
            particles[i].checkCollision();
        }

        calculateAverageAmplitude();
        float c = map(getSmoothedAmplitude(), 0, 0.08f, 0, 255);

        for(int i=0; i<getAudioBuffer().size(); i++) {
            
            
            //noFill();
            //noStroke();
            fill(c, 255,255);

            float newRadius = map(getSmoothedAmplitude(), 0, 0.1f, 100, 210);
            shape.radius = newRadius;
            shape.display();
        }

        calculateFrequencyBands();

        for(int i=0; i<getSmoothedBands().length; i++) {
            if(getSmoothedBands()[i] > 45) {
                
            }
        }
        

    }
}
