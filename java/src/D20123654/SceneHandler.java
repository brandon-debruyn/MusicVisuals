package D20123654;

import java.util.ArrayList;

import ie.tudublin.Visual;

public class SceneHandler extends Visual {

    // menu vars
    int mode = 1;
    boolean paused = false;

    // particle radius 
    float particleR = 25;

    ArrayList<LightsParticle> particles = new ArrayList<LightsParticle>();
    MorphShape shape;
    JuliaGen terr;
    SolidsReformation solid;
    VertexMayhem vertexMayhem;
    
    public void settings() {
        size(900, 900, P3D);
    }

    public void setup() {
        
        // load audio
        startMinim();
        loadAudio("myMusic.mp3");

        colorMode(HSB);

        // create objects
        terr = new JuliaGen(this);
        shape = new MorphShape(width / 2, height / 2, this);
        solid = new SolidsReformation(this);
        vertexMayhem = new VertexMayhem(this);
    }

    public void mousePressed() {

        // spawn particle on click
        particles.add(new LightsParticle(mouseX, mouseY, particleR, this, shape));


    }
    
    // handle scenes
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

         }
    }

    public void draw() {
        
        // switch statement for scenes
        switch(mode)
        {
            case 1: {
                getAudioPlayer().play();

                background(0);
                
                calculateAverageAmplitude();
                for(int j=0; j<particles.size(); j++) {
                    particles.get(j).update();
                    particles.get(j).display();
                    particles.get(j).checkCollision();
                    
                }

                float newRadius = map(getSmoothedAmplitude(), 0, 0.6f, 70, 255);
                shape.radius = newRadius;
                shape.display();

                
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
                vertexMayhem.setupShape();
                vertexMayhem.display();
                break;
            }
        }

    }
}
