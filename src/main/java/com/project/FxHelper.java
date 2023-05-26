package com.project;

import java.util.ArrayList;

public class FxHelper {
	private static Block getBlockWithID(ArrayList<Block> blocks, int id) {
		for (Block block : blocks) {
			if(block.getSid() == id) return block;
		}

		return null;
	}

	private static Pair getPortLocation(ArrayList<Block> blocks, String pointData, String delimeter, int currentX) {	// Might need to check the side of the in / out or get the nearest
		String[] sourceStr = pointData.split(delimeter);
		int id = Integer.parseInt(sourceStr[0]);
		int portNumber = Integer.parseInt(sourceStr[1]);
		Block block = FxHelper.getBlockWithID(blocks, id);
		int[] parameters = block.getJavaFxParameters(0, 0);
		Pair point;

		int portHeight;
		if (block instanceof SumBlock && ((SumBlock) block).getInputNumber() > 1 && !delimeter.contains("out")) {
			portHeight = (parameters[1] + 6 + (int)Math.floor((parameters[3] - 6*2) * (portNumber - 1) / (((SumBlock)block).getInputNumber() - 1)));
		} else {
			portHeight = (parameters[1] + (int)Math.floor(parameters[3] / 2));
		}
		
		if (Math.abs(parameters[0] - currentX) < Math.abs(parameters[0] + parameters[2] - currentX)) {	// check which side is the line closer to?
			point = new Pair(parameters[0] , portHeight);
		} else {	// Then it is right
			point = new Pair(parameters[0] + parameters[2] , portHeight);
		}

		return point;
	}

	public static ArrayList<Coordinates> getLinePoints(MyLine line, ArrayList<Block> blocks, Pair source) {
		Branch[] branches = line.getBranches();
		
		ArrayList<Coordinates> linePoints = new ArrayList<Coordinates>();

		if(source == null) {	// Only works during the first run
			int sideX = 0;
			int sourceBlockID = Integer.parseInt(line.getSource().split("#out:")[0]);
			int[] sourceParam = getBlockWithID(blocks, sourceBlockID).getJavaFxParameters(0, 0);

			if (line.getPoints() != null) {
				int relativeX = Integer.parseInt(line.getPointsString().split(", |; ")[0]);
				if (relativeX > 0){
					sideX = sourceParam[0] + sourceParam[2];
				} else {
					sideX = sourceParam[0];
				}
			} else if (line.getDestination() != null) {
				int destinationBlockID = Integer.parseInt(line.getDestination().split("#in:")[0]);
				int destinationLeftX = getBlockWithID(blocks, destinationBlockID).getJavaFxParameters(0, 0)[0];
				if ( Math.abs(sourceParam[0] - destinationLeftX) > Math.abs(sourceParam[0] + sourceParam[2] - destinationLeftX)) {
					sideX = sourceParam[0] + sourceParam[2];
				} else {
					sideX = sourceParam[0];
				}
			}

			source = FxHelper.getPortLocation(blocks, line.getSource(), "#out:", sideX);
		}

		if (line.getPoints() != null) {
			String pointsArr[] = line.getPointsString().split(", |; ");
			
			for (int i = 0; i < pointsArr.length/2+1; i+= 2) {
				int newX = source.getX() + Integer.parseInt(pointsArr[i]);
				int newY = source.getY() + Integer.parseInt(pointsArr[i+1]);
				Pair destination = new Pair(newX, newY);
				
				if (branches != null && i == pointsArr.length / 2 - 1) {
					linePoints.add(new Coordinates(source, destination, new Pair(1, 0)));
				} else {
					linePoints.add(new Coordinates(source, destination));
				}
				source = new Pair(destination);
			}
		}

		// Get the source and destination from port positions.
		if (line.getDestination() != null) {
			Pair destination = FxHelper.getPortLocation(blocks, line.getDestination(), "#in:", source.getX());
			linePoints.add(new Coordinates(source, destination, new Pair(0, 1)));
		}

		if (branches == null) return linePoints;

		for (Branch branch : branches) {
			linePoints.addAll(getLinePoints(new MyLine(branch), blocks, source));
		}

		return linePoints;
	}
}

class Pair {
	private int x;
	private int y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pair(Pair pair) {
		this.x = pair.getX();
		this.y = pair.getY();
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)%n", this.x, this.y);
	}
}

class Coordinates {
	private Pair source;
	private Pair destination;
	private Pair decision;

	public Coordinates(Pair source, Pair destination, Pair decision) {
		this.source = source;
		this.destination = destination;
		this.decision = decision;
	}



	public Coordinates(Pair source, Pair destination) {
		this(source, destination, new Pair(0, 0));
	}



	public Pair getSource() {
		return source;
	}

	public Pair getDestination() {
		return destination;
	}

	public Pair getDecision() {
		return decision;
	}

	public void setSource(Pair source) {
		this.source = source;
	}

	public void setDestination(Pair destination) {
		this.destination = destination;
	}

	public void setDecision(Pair decision) {
		this.decision = decision;
	}

	@Override
	public String toString() {
		return String.format("[ (%d, %d), (%d, %d), (%d, %d) ]%n",
			this.source.getX(),
			this.source.getY(),
			this.destination.getX(),
			this.destination.getY(),
			this.decision.getX(),
			this.decision.getY()
		);
	}
}
