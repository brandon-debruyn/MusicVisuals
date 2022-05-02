# Music Visualiser Project

Name: Brandon Lee De Bruyn

Student Number: D20123654

## Instructions
- Fork this repository and use it a starter project for your assignment
- Create a new package named your student number and put all your code in this package.
- You should start by creating a subclass of ie.tudublin.Visual
- There is an example visualiser called MyVisual in the example package
- Check out the WaveForm and AudioBandsVisual for examples of how to call the Processing functions from other classes that are not subclasses of PApplet

# Description of the assignment
This is an Assignment for Object Orientated Programming in Year 2 semester 2 written in Java using the [Processing Library](https://processing.org/). This is the final project for the module and is all about visual and audio responsiveness. I selected the song Victor [Consciousness by Ruiz & Alex Stein](https://www.youtube.com/watch?v=9GETplRhgak) as it has a variety of different beats and sound progression.

# Youtubue Video

[![YouTube](http://img.youtube.com/vi/8UcJ3o1uBco/0.jpg)](https://youtu.be/8UcJ3o1uBco)

# Instructions

| Keys | Actions |
|-----------|-----------|
| SPACE | Pause and Play music |
| Left Click | (on scene MorphShape with Light Particles) spawn particles |
| R | Restarts the music |
| ----------|-----------|
| Keys | Scenes |
|-----------|-----------|
| 1 | MorphShape with Light Particles |
| 2 | Responsive Julia Sets |
| 3 | Forms Mayhem |
| 4 | Gray's Klein Bottle |

# How it works

## The SceneHandler
The SceneHandler acts as the main controller for the scenes in the project. It allows the user to switch between all the different visualizations and also controlls the music settings. 

Scene Switching and music is handled with a basic switch/if statement
```
public void keyPressed()
{
	// handle scenes and music
	if (key >= '0' && key <= '4') {
		mode = key - '0';
	}
	switch(key)
	{
		// if space is pressed
		case ' ':
		{
			// play the music
			if(paused)
			{
				getAudioPlayer().play();
				paused = false;
			}
			// pause the music
			else
			{
				getAudioPlayer().pause();
				paused = true;
			}
			break;
		}
		// if r is pressed
		case 'r':
		{
			// reset the music
			getAudioPlayer().cue(0);
			
			// play it automatically if paused is false
			if(!paused) {
				getAudioPlayer().play();
			}
			// otherwise pause it
			else {
				getAudioPlayer().pause();
			}
			
		}
	}
}
```

Drawing all the different scenes based on the scene mode is also handled with a switch statement
```
public void draw() {
        
	// switch statement for scene selection
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
			
			terr.display();
			break;
		}
		case 3: {
			
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
```

Spawning particles in the MorphShape with Light Particles scene makes use of the MousePressed event function, everytime the user clicks a new particle object is created and added to an ArrayList
```
public void mousePressed() {

	// spawn particle on click
	particles.add(new LightsParticle(mouseX, mouseY, particleR, this, shape));
}
```

## The MorphShape class
The transformation matrix and rotation allows the 3D objects to render and rotate approriately. I also used the music amplitude to give a responsiveness effect to the shape.
```
 public void display() {

	// create radius based on amplitude of music
	sc.calculateAverageAmplitude();
	float newRadius = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 70, 150);

	sc.noFill();
	sc.stroke(sc.frameCount % 255, 255,255);
	
	// push the transformation matrix onto the stack
	sc.pushMatrix();
	// translate the object in the x and y direction
	sc.translate(sc.width / 2, sc.height/2);
	sc.lights();

	// rotate object about the x and z axis
	sc.rotateX(angle);
	sc.rotateZ(angle);
	
	// create sphere + cube around it
	sc.sphere(newRadius);
	sc.box(2 * newRadius);
	
	// pop transformation matrix from the stack
	sc.popMatrix();

	// increment rotation angle
	angle += 0.02f;
	
}
```
## The Particle class super class for LightsParticle class
The particle class is the super class and contains the collision detection and boiler plate particle data. The collision detection system is my own work and I made use of the MorphShape position vector and the particles own vector to calculate the difference vector between them. I also ensure that the particle does not exceed an extraordinary velocity by limiting it. The last section of the method includes window collision detection.

```
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

		velocity.x = 1.15f * (c * velocity.x + s * velocity.y); 
		velocity.y = 1.15f * (c * velocity.y - s * velocity.x);

		// handle max and min velocity limits
		if(Math.abs(velocity.x) >= 90 || Math.abs(velocity.y) >= 90) {
			velocity.x = velocity.x * 0.10f;
			velocity.y = velocity.y * 0.10f;
		}
		else if(Math.abs(velocity.x) <= 10 && Math.abs(velocity.y) >= -10) {
			velocity.x = velocity.x * 2.10f;
			velocity.y = velocity.y * 2.10f;
		}
	}

	// check border collision and flip velocity
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
```

## The LightsParticle class
```
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
        
```
## Julia Set fractal
This is one of my favourite scenes of the assignment. I based the algorithm for designing the julia sets from the pseudocode provided by [WikiPedia here](https://en.wikipedia.org/wiki/Julia_set).The pseudocode is provided in the initial section of the display method, and is a combination of the pseudocode for mandelbrot and Julia sets. 
The julia set only performs 25 iterations due to computational limits but does come out really pretty. 

I specifically chose the constants as defined by this picture from wikipedia because it seemed more interesting:
![Julia set for z^2 + 0.7885*exp(eia)](images/julia.png)

Of course the Julia sets are based on complex polynomials, which implies the usage of imaginary and real number values. It was incredibly difficult to find approriate scales to translate it to cartesian x and y, and I spent a lot of time fine tuning untill it came out pleasingly. The usage of PVectors made it more easier and readable.

```
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
```

## Vertex Mayhem
Based on the Gray's [Klein Bottle formula's](http://paulbourke.net/geometry/toroidal/)

![Gray's Klein Bottle Toroidal Formula](images/gray_formula.png)

Although the implementation of this was not as complex as the Julia sets implemenation, it also did require a large amount of scaling and fine tuning. I made use of a seperate method to calculate the x y and z components and return it as a pvector.

```
public PVector grayKleinBottle(float u, float v, float a, float n, float m) {
	// proportionality for display
	float r = sc.width/2;
	float vortxScl = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 10, 45);

	// calculations of x, y,z coordinates of the Gray's Klein bottle
	float x = vortxScl * (a + PApplet.cos(n * u / 2.0f) * PApplet.sin(v) - PApplet.sin(n * u / 2.0f) * PApplet.sin(2 * v)) * PApplet.cos(m * u / 2.0f) + r ;
	float y = vortxScl * (a + PApplet.cos(n * u / 2.0f) * PApplet.sin(v) - PApplet.sin(n * u / 2.0f) * PApplet.sin(2 * v)) * PApplet.sin(m * u / 2.0f) + r ;
	float z = vortxScl * PApplet.sin(n * u / 2.0f) * PApplet.sin(v) + PApplet.cos(n * u / 2.0f) * PApplet.sin(2 * v) + r ;
	
	// return pvector
	return new PVector(x, y, z);
}
```
I then created a two dimensional PVector shapeMatrix to store all of the vertex components of the Toroid. After the Shape Matrix is filled, I draw the vertices with the startShape(), vertex() and endShape() methods.
```
public void display() {

        sc.calculateAverageAmplitude();

        for(int i=0; i<totalVertices;i ++) {
            float u = PApplet.map(i, 0, totalVertices, 0, PApplet.TWO_PI + PApplet.TWO_PI);
            for(int j=0; j<totalVertices;j ++) {
                float v = PApplet.map(j, 0, totalVertices, 0, PApplet.TWO_PI);
                
                // randomize constants relative to amplitude
                float a = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 1.5f, 7.5f);
                float m = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 1, 6f);
                float n = PApplet.map(sc.getSmoothedAmplitude(), 0, 0.8f, 2.1f, 6f);

                // assign the PVector to the shape matrix 
                PVector vect = new PVector(0, 0, 0);
                vect = grayKleinBottle(u, v, a, n, m);
                shapeMatrix[i][j] = vect; 
        
            }
            
        }
| ------------------------------------------------------ >
		offset += 5;
        for(int i=0; i<totalVertices - 2; i++) {
            float hu = PApplet.map(i, 0, totalVertices, 0, 255*6);
            sc.stroke((hu + offset) % 255, 255, 255);
            sc.strokeWeight(0.7f);

            // begin custom shape and set Triangles to join vertices
            sc.beginShape(PApplet.TRIANGLES);
            for(int j=0; j<totalVertices; j++) {

                // first vertex
                PVector v1 = shapeMatrix[i][j];
                sc.vertex(v1.x, v1.y, v1.z);

                // second vertex
                PVector v2 = shapeMatrix[i + 1][j];
                sc.vertex(v2.x, v2.y, v2.z);

                // third vertex - added some depth to this vertex in the z dimension
                PVector v3 = shapeMatrix[i + 1][j];
                sc.vertex(v3.x, v3.y, v3.z / 2);

            }
            // end custom shape
            sc.endShape();
        }
```

