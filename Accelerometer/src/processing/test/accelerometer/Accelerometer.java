package processing.test.accelerometer;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Accelerometer extends PApplet {

	AccelerometerManager accel;
	float ax;
	float ay;
	float az;
	float maxX;
	float maxY;
	float maxZ;
	float minX;
	float minY;
	float minZ;
	ArrayList<Float> sampleX;
	ArrayList<Float> sampleY;
	ArrayList<Float> sampleZ;

	public void setup() {
		accel = new AccelerometerManager(this);
		orientation(PORTRAIT);
		sampleX = new ArrayList<Float>();
		sampleY = new ArrayList<Float>();
		sampleZ = new ArrayList<Float>();
		minX = Float.MAX_VALUE;
		minY = Float.MAX_VALUE;
		minZ = Float.MAX_VALUE;
		maxY = Float.MIN_VALUE;
		maxZ = Float.MIN_VALUE;
		maxX = Float.MIN_VALUE;
		
		noLoop();
	}

	public void draw() {
		background(4, 5, 6);
		fill(255);
		textSize(70);
		textAlign(CENTER, CENTER);
		text("x: " + nf(ax, 1, 2) + "\n" + "y: " + nf(ay, 1, 2) + "\n" + "z: "
				+ nf(az, 1, 2), 0, 0, width, height);
		
		noFill();
		stroke(255, 0, 255);
		beginShape();
		vertex(0, height);
		for(int i = 0; i < sampleX.size(); i++){
			vertex(map(i, 0, sampleX.size(), 0, width),  map(sampleX.get(i), minX, maxX, height, 0));
		}
		vertex(width, height);
		endShape(CLOSE);
		
		noFill();
		stroke(255, 255, 0);
		beginShape();
		vertex(0, height);
		for(int i = 0; i < sampleY.size(); i++){
			vertex(map(i, 0, sampleY.size(), 0, width),  map(sampleY.get(i), minY, maxY, height, 0));
		}
		vertex(width, height);
		endShape(CLOSE);
		
		noFill();
		stroke(0, 255, 255);
		beginShape();
		vertex(0, height);
		for(int i = 0; i < sampleZ.size(); i++){
			vertex(map(i, 0, sampleZ.size(), 0, width),  map(sampleZ.get(i), minZ, maxZ, height, 0));
		}
		vertex(width, height);
		endShape(CLOSE);
	}

	public void resume() {
		if (accel != null) {
			accel.resume();
		}
	}

	public void pause() {
		if (accel != null) {
			accel.pause();
		}
	}

	public void shakeEvent(float force) {
		println("shake : " + force);
	}

	public void accelerationEvent(float x, float y, float z) {
		// println("acceleration: " + x + ", " + y + ", " + z);
		ax = x;
		ay = y;
		az = z;
		
		if (sampleX.size() > 200)
			sampleX.remove(0);
		if (sampleY.size() > 200)
			sampleY.remove(0);
		if (sampleZ.size() > 200)
			sampleZ.remove(0);


		if(ax > maxX)
			maxX = ax;
		if(ax < minX)
			minX = ax;
		
		if(ay > maxY)
			maxY = ay;
		if(ay < minY)
			minY = ay;
		
		if(az > maxZ)
			maxZ = az;
		if(az < minZ)
			minZ = az;
		
		sampleX.add(ax);
		sampleY.add(ay);
		sampleZ.add(az);


		redraw();
	}
}
