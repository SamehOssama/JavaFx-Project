/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		Pane pane = new Pane();

		ArrayList<Block> blocks = new ArrayList<Block>();
		ArrayList<MyLine> lines = new ArrayList<MyLine>();
		try {
			blocks = Parser.getBlocks();
			lines = Parser.getLines();
		} catch (Exception e) {
			e.printStackTrace();
		}

		int canvasX = -700;
		int canvasY = -100;

		for (Block block : blocks) {
			int[] parameters = block.getJavaFxParameters(canvasX, canvasY);
			String name = block.getName();

			if (block.getBlockType().equals("Sum")){
				Shapes.drawSum(pane, parameters[0], parameters[1], parameters[2], parameters[3], name, ((SumBlock)block).getInputNumber());
			}

			if (block.getBlockType().equals("Constant")){
				Shapes.drawConstant(pane, parameters[0], parameters[1], parameters[2], parameters[3], name);
			}

			if (block.getBlockType().equals("Saturate")){
				Shapes.drawSaturate(pane, parameters[0], parameters[1], parameters[2], parameters[3], name);
			}
			
			if (block.getBlockType().equals("Scope")){
				Shapes.drawScope(pane, parameters[0], parameters[1], parameters[2], parameters[3], name);
			}

			if (block.getBlockType().equals("UnitDelay")){
				Shapes.drawUnitDelay(pane, parameters[0], parameters[1], parameters[2], parameters[3], name);
			}
		}

		ArrayList<Shape> misc = new ArrayList<Shape>();	// Holds the concrete shaps to be drawn at last
		for (MyLine myLine : lines) {
			ArrayList<Coordinates> lineCoordinates = FxHelper.getLinePoints(myLine, blocks, null);
			for (Coordinates coordinates : lineCoordinates) {
				Pair dst = coordinates.getDestination();
				Pair src = coordinates.getSource();
				Pair dec = coordinates.getDecision();
				Line line1 = new Line(src.getX() + canvasX, src.getY() + canvasY, dst.getX() + canvasX, dst.getY() + canvasY);
				line1.setStrokeWidth(1);
				Line line2 = new Line(src.getX() + canvasX, src.getY() + canvasY, dst.getX() + canvasX, dst.getY() + canvasY);
				line2.setStrokeWidth(3);
				line2.setStroke(Color.DEEPSKYBLUE);
				line2.setStrokeLineCap(StrokeLineCap.BUTT);
				
				pane.getChildren().add(line2);
				misc.add(line1);

				if (dec.getX() == 1) {			// has branch -> draw circle
					Circle c = new Circle(dst.getX() + canvasX, dst.getY() + canvasY, 3);
					misc.add(c);
				} else if (dec.getY() == 1) {	// has end -> draw arrow

					Polygon polygon1 = new Polygon();
					double tip = 1;
					double tail = 5;
					int mult = 1;
					if(dst.getX() - src.getX() > 0) {
						mult = -1;
					}

					polygon1.getPoints().addAll(new Double[] {
							(double) (dst.getX() + canvasX - tip*mult), (double) (dst.getY() + canvasY),
							(double) (dst.getX() + canvasX + tail*mult), (double) (dst.getY() + canvasY + 4),
							(double) (dst.getX() + canvasX + tail*mult), (double) (dst.getY() + canvasY - 4)
					});
					polygon1.setFill(Color.BLACK);

					Polygon polygon2 = new Polygon();
					polygon2.getPoints().addAll(new Double[] {
							(double) (dst.getX() + canvasX - (tip + 1.5)*mult), (double) (dst.getY() + canvasY),
							(double) (dst.getX() + canvasX + (tail + 1)*mult), (double) (dst.getY() + canvasY + 6),
							(double) (dst.getX() + canvasX + (tail + 1)*mult), (double) (dst.getY() + canvasY - 6)
					});
					polygon2.setFill(Color.DEEPSKYBLUE);
					misc.add(polygon1);
					pane.getChildren().add(polygon2);
				}
			}
		}
		pane.getChildren().addAll(misc);

		// Zoom in
		Scale scale = new Scale();
		scale.setX(pane.getScaleX() * 2);
		scale.setY(pane.getScaleY() * 2);
		scale.setPivotX(pane.getScaleX());
		scale.setPivotY(pane.getScaleY());
		pane.getTransforms().add(scale);

		// Create a scene and place it in the stage
		Scene scene = new Scene(new BorderPane(pane), 1100, 650);

		primaryStage.setTitle("Advanced COmputer Programming Project"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
}
