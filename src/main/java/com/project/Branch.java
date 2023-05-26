package com.project;

public class Branch {

    private int zOrder;
    private String destination;
    private String points;

    public Branch() {

    }

    public Branch(int zOrder, String destination, String points) {
        this.destination = destination;
        this.points = points;
    }

    public int getzOrder() {
        return zOrder;
    }

    public String getDestination() {
        return destination;
    }

    public String getPoints() {
        return points;
    }

    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
	public String toString() {
		return String.format(
			"  <Branch>%n" +
				"    <P Name=\"ZOrder\">%d</P>%n" +
				"    <P Name=\"Points\">%s</P>%n" +
				"    <P Name=\"Dst\">%s</P>%n" +
			"  </Branch>\n",
			zOrder, points, destination, zOrder
		);
	}
}
