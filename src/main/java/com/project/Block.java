package com.project;

public class Block {

	private String blockType;
	private String name;
	private int sid;
	private int zOrder;
	private String position;

	public Block() {

	}

	public Block(String blockType, String name, int sid, int zOrder, String position) {
		this.blockType = blockType;
		this.name = name;
		this.sid = sid;
		this.zOrder = zOrder;
		this.position = position;
	}

	public String getBlockType() {
		return blockType;
	}

	public String getName() {
		return name;
	}
	
	public int getSid() {
		return sid;
	}
	
	public int getzOrder() {
		return zOrder;
	}
	
	public String getPosition() {
		return position;
	}

	public void setBlockType(String blockType) {
		this.blockType = blockType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setzOrder(int zOrder) {
		this.zOrder = zOrder;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int[] getJavaFxParameters(int canvasX, int canvasY) {
		int[] original_parameters = new int[4];
		int[] new_parameters = new int[4];

		String[] strings = this.position.substring(1, this.position.length() - 1).split(", ");
		for (int i = 0; i < strings.length; i++) {
			original_parameters[i] = Integer.parseInt(strings[i]);
		}

		// [left top right bottom] => all measuremnets are taken from the origin
		// Which is the top left point of the canvas
		// ( +ve numb = distance down or right from the origin)
		// ( -ve numb = distance up or left from the origin)

		new_parameters[0] = (canvasX + original_parameters[0]); // X upper left corner = L
		new_parameters[1] = (canvasY + original_parameters[1]); // Y upper left corner = T
		new_parameters[2] = (original_parameters[2] - original_parameters[0]); // W = R - L
		new_parameters[3] = (original_parameters[3] - original_parameters[1]); // H = B - T

		return new_parameters;
	}

	@Override
	public String toString() {
		return String.format(
			"<Block BlockType=\"%s\" Name=\"%s\" SID=\"%d\">%n" +
				"  <P Name=\"Position\">%s</P>%n" +
				"  <P Name=\"ZOrder\">%d</P>%n" +
			"</Block>",
			blockType, name, sid, position, zOrder
		);
	}
}

class SumBlock extends Block {

	private String ports;
	private int inputNumber;

	public SumBlock(){

	}

	public SumBlock(Block block) {
		super(block.getBlockType(), block.getName(), block.getSid(), block.getzOrder(), block.getPosition());
	}

	public SumBlock(String blockType, String name, int sid, int zOrder, String position, String ports, int inputNumber) {
		super(blockType, name, sid, zOrder, position);
		this.ports = ports;
		this.inputNumber = inputNumber;
	}

	public String getPorts() {
		return ports;
	}

	public int getInputNumber() {
		return inputNumber;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public void setInputNumber(int inputNumber) {
		this.inputNumber = inputNumber;
	}

	@Override
	public String toString() {
		return String.format(
			"<Block BlockType=\"%s\" Name=\"%s\" SID=\"%d\">%n" +
			"  <P Name=\"Ports\">%s</P>%n" +
			"  <P Name=\"Position\">%s</P>%n" +
			"  <P Name=\"ZOrder\">%d</P>%n" +
			"  <P Name=\"Inputs\">%s</P>%n" +
			"</Block>",
			this.getBlockType(), this.getName(), this.getSid(), this.getPorts(), this.getPosition(), this.getzOrder(), this.getInputNumber()
		);
	}
}
