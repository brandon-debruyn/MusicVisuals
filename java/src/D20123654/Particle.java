package D20123654;

import processing.core.PApplet;
import processing.core.PVector;


public class Particle {

    // maintain vecotr magnitude and direction
    PVector position;
    PVector velocity;

    SceneHandler sc;
    MorphShape shape;

    // particle radius and angle
    float radius;
    float angle;
    
    public Particle(float x, float y, float r, SceneHandler sc, MorphShape shape) {
        this.shape = shape;
        this.sc = sc;

        this.position = new PVector(x, y);
        PVector dir = PVector.sub(shape.position, position);
        
        
        this.velocity = new PVector(1.5f * dir.normalize().x, 1.5f * dir.normalize().y);
        this.velocity.mult(5);
        
        
        this.radius = r;
    }

    public float getParticleKE_Xi(float m ) {
        return (0.5f * (m) * (velocity.x * velocity.x));
    }

    public float getParticleKE_Yi(float m) {
        return(0.5f * (m) * (velocity.y * velocity.y));
    }

    public void activate() {

        PVector dir = PVector.sub(shape.position, position);
        velocity.x = 1.5f * dir.normalize().x;
        velocity.y = 1.5f * dir.normalize().y;
        velocity.mult(5);
    }

    public void checkCollision() {
        
        PVector distVect = PVector.sub(shape.position, position);
        float distVectMag = distVect.mag();
        float pointOfCol = radius + shape.radius;

        // centre shape <-> particle collision handling
        if(distVectMag < pointOfCol) {
            PVector distVect2 = distVect.copy();
            float distCor = ((pointOfCol-distVectMag)/2.0f);
            PVector rotVect = distVect2.normalize().mult(distCor);
            position.sub(rotVect);

            // direction of dist vector between particle and sphere
            float angle = distVect.heading();

            float s = PApplet.sin(angle);
            float c = PApplet.cos(angle);


            velocity.x = 1.35f * (c * velocity.x + s * velocity.y); 
            velocity.y = 1.35f * (c * velocity.y - s * velocity.x);

            if(Math.abs(velocity.x) >= 90 || Math.abs(velocity.y) >= 90) {
                velocity.x = velocity.x * 0.10f;
                velocity.y = velocity.y * 0.10f;
            }
            else if(Math.abs(velocity.x) <= 5 && Math.abs(velocity.y) >= -5) {
                velocity.x = velocity.x * 2.10f;
                velocity.y = velocity.y * 2.10f;
            }

        }
        // border collision
        if (position.x > sc.width-radius) {

            position.x = sc.width-radius;
            velocity.x *= -1;
        } 
        else if (position.x < radius) {

            position.x = radius;
            velocity.x *= -1;
        } 
        else if (position.y > sc.height-radius) {

            position.y = sc.height-radius;
            velocity.y *= -1;

        } 
        else if (position.y < radius) {

            position.y = radius;
            velocity.y *= -1;

        }
        
    }

    public void updateV() {
        position.add(velocity);
    }

    public void display() {
        
        float c = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.06f, 0, 255);
        
        sc.fill(c, 255,255);
        sc.ellipseMode(PApplet.CENTER);
        float newRadius = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.1f, radius, radius * 6);
        sc.ellipse(position.x, position.y, newRadius, newRadius );
        
        sc.stroke(c, 255, 255);

        for(int i=0; i<sc.getAudioBuffer().size(); i++) {
            float lines = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.06f, 0, 100);
            sc.line(position.x, position.y, PApplet.cos(position.x +  lines),PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, sc.width + PApplet.cos(position.x +  lines), sc.width + PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, sc.width + PApplet.cos(position.x +  lines),  PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, PApplet.cos(position.x +  lines), sc.height + PApplet.cos(position.y + lines));
            
        }
        
    }

}
