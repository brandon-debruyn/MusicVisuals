package ie.tudublin;

import D20123654.SceneHandler;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new MyVisual());		
	}

	public void sceneHandler() {
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new SceneHandler());
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		//main.startUI();			

		main.sceneHandler();
	}
}