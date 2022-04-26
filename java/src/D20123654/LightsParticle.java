package D20123654;

import processing.core.PApplet;
import processing.core.PVector;

public class LightsParticle extends Particle {
    

    public LightsParticle(float x, float y, float r, SceneHandler sc, MorphShape shape) {
        super(x, y, r, sc, shape);

        PVector dir = PVector.sub(shape.position, position);
        this.velocity = new PVector(1.5f * dir.normalize().x, 1.5f * dir.normalize().y);
        this.velocity.mult(5);

    }

    @Override
    public void update() {
        position.add(velocity);
    }

    @Override
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
