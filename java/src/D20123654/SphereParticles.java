package D20123654;

import java.util.Vector;

import javax.print.attribute.standard.MediaSize.Other;

import processing.core.PApplet;
import processing.core.PVector;

public class SphereParticles {

    // vectors by definition have magnitude and direction
    PVector position;
    PVector velocity;

    SceneHandler sc;
    MorphShape shape;

    float radius;
    
    public SphereParticles(float x, float y, float r, SceneHandler sc, MorphShape shape) {
        this.shape = shape;
        this.sc = sc;

        position = new PVector(x, y);
        PVector dir = PVector.sub(shape.position, position);
        
        velocity = new PVector(1.5f * dir.normalize().x, 1.5f * dir.normalize().y);
        velocity.mult(5);

        radius = r;
    }

    public float getParticleKE_Xi() {
        return (0.5f * (1) * (velocity.x));
    }

    public float getParticleKE_Yi() {
        return(0.5f * (1) * (velocity.y));
    }

    public void checkCollision() {
        
        PVector distVect = PVector.sub(shape.position, position);
        float distVectMag = distVect.mag();
        float pointOfCol = radius + shape.radius;

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
        
        //sc.noFill();
        sc.ellipseMode(PApplet.CENTER);
        sc.ellipse(position.x, position.y, radius, radius );
    }

}
