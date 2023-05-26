package com.project;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Shapes {

	private static double innerStroke = 1.2;	// Black
	private static double outerStroke = 3;	// Blue
	
	public static void drawSum(Pane group, int x, int y, int w, int h, String name, int noi) {
		Rectangle r1 = Shapes.createRectangle(x, y, w, h, Color.DEEPSKYBLUE, outerStroke);

		Rectangle r2 = Shapes.createRectangle(x, y, w, h, Color.BLACK, innerStroke);
		r2.setStrokeType(StrokeType.INSIDE);

		Text[] text = new Text[noi];
		
		for(int i = 0 ; i < noi ; i++){
			text[i] = new Text("+");
			text[i].setFont(Font.font("Verdana", FontWeight.BOLD, 8));
			text[i].setFill(Color.BLACK);
			text[i].setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
			text[i].setX(x + 3);
			text[i].setY(y + Math.ceil(text[i].getLayoutBounds().getMaxY()) + 6  + (h - 6*2) * i / (noi - 1));
		}
		
		Text t = setName(x, y, w, h, name);

		group.getChildren().addAll(r1, r2, t);
		group.getChildren().addAll(text);
	}

	public static void drawConstant(Pane group, int x, int y, int w, int h, String name) {
		Rectangle r1 = Shapes.createRectangle(x, y, w, h, Color.DEEPSKYBLUE, outerStroke);

		Rectangle r2 = Shapes.createRectangle(x, y, w, h, Color.BLACK, innerStroke);
		r2.setStrokeType(StrokeType.INSIDE);
		
		Rectangle r3 = new Rectangle(w + 12, h + 12);
		r3.setX(x - 6);
		r3.setY(y - 6);
		r3.setStroke(Color.DEEPSKYBLUE);
		r3.setStrokeWidth(2);
		r3.setFill(Color.WHITE);

		Rectangle r4 = new Rectangle(w, h + 12);
		r4.setX(x);
		r4.setY(y - 6);
		r4.setStroke(Color.WHITE);
		r4.setStrokeWidth(4);
		r4.setFill(Color.WHITE);

		Rectangle r5 = new Rectangle(w + 12, h);
		r5.setX(x - 6);
		r5.setY(y);
		r5.setStroke(Color.WHITE);
		r5.setStrokeWidth(4);
		r5.setFill(Color.WHITE);

		Text text1 = new Text("1");
		text1.setFont(Font.font("Courier", FontWeight.NORMAL, 15));
		text1.setFill(Color.BLACK);
		text1.setX(x + w / 2 - Math.ceil(text1.getLayoutBounds().getMaxX() / 2));
		text1.setY(y + h / 2 + Math.ceil(text1.getLayoutBounds().getMaxY()));

		Text t = setName(x, y, w, h, name);

		group.getChildren().addAll(r3, r4, r5, r1, r2, text1, t);
	}

	public static void drawScope(Pane group, int x, int y, int w, int h, String name) {
		Rectangle r1 = Shapes.createRectangle(x, y, w, h, Color.DEEPSKYBLUE, outerStroke);

		Rectangle r2 = Shapes.createRectangle(x, y, w, h, Color.BLACK, innerStroke);
		r2.setStrokeType(StrokeType.INSIDE);

		Rectangle r3 = new Rectangle((x + w * 1 / 6), (y + w * 1 / 6), (w * 2 / 3), (h * 7 / 16));
		r3.setStroke(Color.BLACK);
		r3.setFill(Color.WHITE);
		r3.setArcWidth(3);
		r3.setArcHeight(7);

		Text t = setName(x, y, w, h, name);

		group.getChildren().addAll(r1, r2, r3, t);
	}

	public static void drawUnitDelay(Pane group, int x, int y, int w, int h, String name) {
		Rectangle r1 = Shapes.createRectangle(x, y, w, h, Color.DEEPSKYBLUE, outerStroke);

		Rectangle r2 = Shapes.createRectangle(x, y, w, h, Color.BLACK, innerStroke);
		r2.setStrokeType(StrokeType.INSIDE);

		Text text1 = new Text("1");
		text1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
		text1.setFill(Color.BLACK);
		text1.setX(x + w / 2 - Math.ceil(text1.getLayoutBounds().getMaxX()) / 2 + 1);		// this formula to center "1" in the rectangle, 3.5 is the font width/2
		text1.setY(y + Math.ceil(text1.getLayoutBounds().getMaxY())*3 + 5);

		Text text2 = new Text("_");
		text2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
		text2.setFill(Color.BLACK);
		text2.setX(x + w / 2 - Math.ceil(text2.getLayoutBounds().getMaxX()) / 2 + 1);		// this formula to center "_" in the rectangle, 3.5 is the font width/2
		text2.setY(y + 5 + (h - 2*5) * 1 / 2);

		Text text3 = new Text("z");
		text3.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
		text3.setFill(Color.BLACK);
		text3.setX(x + w / 2 - Math.ceil(text3.getLayoutBounds().getMaxX()) / 2 + 1);		// this formula to center "z" in the rectangle, 3.5 is the font width/2
		text3.setY(y  + 5 + (h - 2*5));

		Text t = setName(x, y, w, h, name);

		group.getChildren().addAll(r1, r2, text1, text2, text3, t);
	}

	public static void drawSaturate(Pane group, int x, int y, int w, int h, String name) {
		Rectangle r1 = Shapes.createRectangle(x, y, w, h, Color.DEEPSKYBLUE, outerStroke);

		Rectangle r2 = Shapes.createRectangle(x, y, w, h, Color.BLACK, innerStroke);
		r2.setStrokeType(StrokeType.INSIDE);

		// Top line
		Line l1 = new Line();
		l1.setStartX(x + w/2 + w/4); 
		l1.setStartY(y + 8); 
		l1.setEndX(x + w - 3); 
		l1.setEndY(y + 8); 
		l1.setStroke(Color.BLACK);
		l1.setStrokeWidth(1);

		// Bottom line
		Line l2 = new Line(); 
		l2.setStartX(x + w/2 - w/4); 
		l2.setStartY(y + h - 8); 
		l2.setEndX(x + 3); 
		l2.setEndY(y + h - 8); 
		l2.setStroke(Color.BLACK);
		l2.setStrokeWidth(1);

		// y - axis
		Line l3 = new Line(); 
		l3.setStartX(x + w/2); 
		l3.setStartY(y + 4); 
		l3.setEndX(x + w/2); 
		l3.setEndY((y + h) - 4); 
		l3.setStroke(Color.BLACK);
		l3.setStrokeWidth(0.5);

		// x - axis
		Line l4 = new Line();            
		l4.setStartX(x + 4); 
		l4.setStartY(y + h/2); 
		l4.setEndX(x + w - 4); 
		l4.setEndY(y + h/2); 
		l4.setStroke(Color.BLACK);
		l4.setStrokeWidth(0.5);

		// tillted line
		Line l5 = new Line(); 
		l5.setStartX(x + w/2 + w/4); 
		l5.setStartY(y + 8); 
		l5.setEndX(x + w/2 - w/4); 
		l5.setEndY(y + h - 8);
		l5.setStroke(Color.BLACK);
		l5.setStrokeWidth(1);

		Text t = setName(x, y, w, h, name);

		group.getChildren().addAll(r1,r2,t,l3,l4,l1,l2,l5);
	}

	private static Text setName(int x, int y, int w, int h, String name) {
		Text t = new Text(name);
		t.setX(x + w / 2 - Math.ceil(t.getLayoutBounds().getMaxX()) / 2 + 1);
		t.setY(y + h + 15);
		t.setFill(Color.DEEPSKYBLUE);
		t.setFont(Font.font("Verdana", FontWeight.NORMAL, 10));
		return t;
	}

	private static Rectangle createRectangle(int x, int y, int w, int h, Color strokeColor, double strokeWidth) {
		Rectangle r = new Rectangle(x, y, w, h);
		r.setStroke(strokeColor);
		r.setStrokeWidth(strokeWidth);
		r.setFill(Color.WHITE);
		return r;
	}
}