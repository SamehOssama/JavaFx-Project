package com.project;

public class MyLine {

    private int zOrder;
	private String source;
	private String points;
	private String destination;
	Branch[] branches;

	public MyLine() {

	}

	public MyLine(Branch branch) {
		this(branch.getzOrder(), null, branch.getPoints(), branch.getDestination(), null);
	}
	
	public MyLine(int zOrder, String source, String points, String destination, Branch[] branch) {
		this.zOrder = zOrder;
		this.source = source;
		this.points = points;
		this.destination = destination;
		this.branches = branch;
	}

	public int getzOrder() {
		return zOrder;
	}

	public String getSource() {
		return source;
	}

	public String getPoints() {
		return points;
	}

	public String getDestination() {
		return destination;
	}

	public Branch[] getBranches() {
		return branches;
	}

	public void setzOrder(int zOrder) {
		this.zOrder = zOrder;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setBranches(Branch[] branches) {
		this.branches = branches;
	}

	public String getPointsString(){
		return this.points.substring(1, this.points.length()-1);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder(String.format(
			"<Line>%n" +
				"  <P Name=\"ZOrder\">%d</P>%n" +
				"  <P Name=\"Points\">%s</P>%n" +
				"  <P Name=\"Src\">%s</P>%n",
			zOrder, points, source
		));

		if (branches == null) {
			string.append(String.format("  <P Name=\"Dst\">%s</P>%n", destination));
		} else {
			for (Branch branch : branches) {
				string.append(branch.toString());
			}
		}

		string.append("</Line>");

		return string.toString();
	}
}
