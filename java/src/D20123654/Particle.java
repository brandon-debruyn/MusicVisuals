package D20123654;

import processing.core.PApplet;
import processing.core.PVector;


public abstract class Particle {

    // maintain vecotr magnitude and direction
    protected PVector position;
    protected PVector velocity;

    protected SceneHandler sc;
    protected MorphShape shape;

    // particle radius and angle
    protected float radius;
    protected float angle;
    
    
    public Particle(float x, float y, float r, SceneHandler sc, MorphShape shape) {
        this.sc = sc;
        this.shape = shape;
        this.position = new PVector(x,y);
        this.radius = r;
    }


    protected float getParticleKE_X(float m ) {
        return (0.5f * (m) * (velocity.x * velocity.x));
    }

    protected float getParticleKE_Y(float m) {
        return(0.5f * (m) * (velocity.y * velocity.y));
    }

    // initialize vector to start with direction of shape and initial velocity
    public void activate() {

        PVector dir = PVector.sub(shape.position, position);
        velocity.x = 1.5f * dir.normalize().x;
        velocity.y = 1.5f * dir.normalize().y;
        velocity.mult(5);
    }

    // check colision between shape body and window border
    public void checkCollision() {
        
        // vector subtraction to determine position of shape relative to particle
        PVector distVect = PVector.sub(shape.position, position);

        // calculate the magnitude of this vector
        float distVectMag = distVect.mag();

        // consider the radii of both objects
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

    public abstract void update(); 

    public abstract void display();

}
