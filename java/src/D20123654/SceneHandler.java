package D20123654;

import ie.tudublin.Visual;
import ddf.minim.AudioBuffer;
import ddf.minim.analysis.FFT;

public class SceneHandler extends Visual {

    int mode = 1;
    boolean paused = false;

    float[] lerpedBuffer;
    float y = 0;
    float smoothedY = 0;
    float smoothedAmplitude = 0;
    int num_particles = 10;
    float radius = 400;
    float particleR = 12;

    float halfH = height / 2;

    LightsParticle[] particles = new LightsParticle[num_particles];
    MorphShape shape;
    JuliaGen terr;
    SolidsReformation solid;
    
    public void settings() {
        size(800, 800, P3D);

    }

    public void setup() {
    
        startMinim();
        loadAudio("myMusic.mp3");

        colorMode(HSB);
        terr = new JuliaGen(this);
        
        //noCursor();
        
        lerpedBuffer = new float[width];
        int posx = width / num_particles;
        
        shape = new MorphShape(width / 2, height / 2, radius, this);
        solid = new SolidsReformation(this);
        
        for(int i=0; i<num_particles; i++) {
            if(i <= num_particles / 2) {
                particles[i] = new LightsParticle(posx, 0.0f, particleR, this, shape);
                posx += 2 * (width / num_particles);
            }
            else {
                
                particles[i] = new LightsParticle(posx, height, particleR, this, shape);
                posx -= 2 * (width / num_particles);
            }
        }
        

        
        
        
    }
    
    public void keyPressed()
    {
        if (key >= '0' && key <= '4') {
			mode = key - '0';
		}
        switch(key)
        {
            case ' ':
            {
                if(paused)
                {
                    getAudioPlayer().play();
                    paused = false;
                }
                else
                {
                    getAudioPlayer().pause();
                    paused = true;
                }
                break;
            }

            case '1':
            {
                getAudioPlayer().cue(0);
                getAudioPlayer().play();
                break;

            }

            case '2':
            {
                getAudioPlayer().cue(22500);
                getAudioPlayer().play();
                break;
            }

            case '3':
            {
                getAudioPlayer().cue(112000);
                getAudioPlayer().play();
                break;
            }

            case '4':
            {
                getAudioPlayer().cue(162000);
                getAudioPlayer().play();
                break;
            }
            
         }
    }

    int p = 0;

    public void draw() {
        //(\sqrt{(\sqrt{(x*\sin(b)+a*\cos(b))^{2}+(y*\cos(d)-c*\sin(d))^{2}}-4)^{2}+(y*\sin(d)+c*\cos(d))^{2}}-2)^{2}+(x*\cos(b)-a*\sin(b))^{2}=1
        
        
        getFFT().window(FFT.HAMMING);
        getFFT().forward(getAudioBuffer());
       
        
        switch(mode)
        {
            case 1: {
                getAudioPlayer().play();

                background(0);
                
                calculateAverageAmplitude();
                float newRadius = map(getSmoothedAmplitude(), 0, 0.6f, 70, 255);
                    shape.radius = newRadius;
                    shape.display();
               

                calculateFrequencyBands();

                for(int j=0; j<num_particles; j++) {
                    particles[j].update();
                    particles[j].display();
                    particles[j].checkCollision();
                    
                }
                break;
            }
            case 2: {
                // background(0);
                terr.display();
                break;
            }
            case 3: {
                //background(0);
                solid.display();
                break;
            }
            case 4: {
                
                break;
            }
        }

    }
}
