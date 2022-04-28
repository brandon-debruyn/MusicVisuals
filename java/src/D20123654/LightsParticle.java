package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class LightsParticle extends Particle {
    
    float angle, offset;

    public LightsParticle(float x, float y, float r, SceneHandler sc, MorphShape shape) {
        super(x, y, r, sc, shape);

        PVector dir = PVector.sub(shape.position, position);
        this.velocity = new PVector(1.5f * dir.normalize().x, 1.5f * dir.normalize().y);
        this.velocity.mult(4);

    }

    @Override
    public void update() {
        position.add(velocity);
    }

    @Override
    public void display() {
        
        sc.calculateAverageAmplitude();

        // select colour based on smoothed music amplitude
        float c = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.6f, 0, 255);
        sc.noFill();
        sc.stroke(c, 255,255);
        
        // select radius based on smoothed music amplitude
        float newRadius = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.7f, radius, radius * 3);
        
        // push transformation matrix onto stack
        sc.pushMatrix();

        // translate particle relative to it's current x and y position
        sc.translate(position.x, position.y, 0);

        // rotate the particle
        sc.rotate(angle);

        // create object as sphere with cube surrounding it
        sc.sphere(newRadius);
        sc.box(2 * newRadius);
        
        // pop transformation matrix from the stack
        sc.popMatrix();

        // increment angle of rotation
        angle += 0.3f;
        
        // create offset for rainbow colours
        offset += 5;
        for(int i=0; i<sc.getAudioBuffer().size(); i++) {
            // define a hu to map audio buffer for colours
            float hu = PApplet.map(i, 0, sc.getAudioBuffer().size(), 0, 255*6);
            sc.stroke((hu + offset) % 255, 255, 255);

            // create lights at the corners of the screen pointing at particle
            float lines = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.7f, 0, 100);
            sc.line(position.x, position.y, PApplet.cos(position.x +  lines),PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, sc.width + PApplet.cos(position.x +  lines), sc.width + PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, sc.width + PApplet.cos(position.x +  lines),  PApplet.cos(position.y + lines));
            sc.line(position.x, position.y, PApplet.cos(position.x +  lines), sc.height + PApplet.cos(position.y + lines));
            
        }
        
    }
}
