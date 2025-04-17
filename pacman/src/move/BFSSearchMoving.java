package src.move;

import ch.aplu.jgamegrid.Location;
import src.Game;
import src.PacActor;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class BFSSearchMoving implements MovingStrategy{
	@Override
	public Location findNext(PacActor pacman, int[][] map, Game game) {
		Location closestPill = pacman.closestPillLocation();
		Location current = pacman.getLocation();
		List<Point> paths = findPath(map, new Point(current.getX(), current.getY()),
				new Point(closestPill.getX(), closestPill.getY()), game);
		if (paths.size() > 0) {
			Point p =  (paths.size()==1) ? paths.get(0) : paths.get(1);
			Location next = new Location(p.x, p.y);
			if (current.x - p.x > 0)
				pacman.setDirection(Location.CompassDirection.WEST);
			else if (current.x - p.x < 0)
				pacman.setDirection(Location.CompassDirection.EAST);
			else if (current.y - p.y > 0)
				pacman.setDirection(Location.CompassDirection.NORTH);
			else if (current.y - p.y < 0)
				pacman.setDirection(Location.CompassDirection.SOUTH);
			else
				pacman.setDirection(Location.CompassDirection.WEST);

			return next;
		}
		return null;
	}

	public static List<Point> findPath(int[][] map, Point start, Point goal, Game game) {
		StarNode startNode = new StarNode(start);
		StarNode goalNode = new StarNode(goal);
		// open list
		LinkedList<StarNode> open = new LinkedList<StarNode>();
		LinkedList<StarNode> close = new LinkedList<StarNode>();
		// init
		startNode.searchParent = null;
		open.add(startNode);
		close.add(startNode);

		while (!open.isEmpty()) {

			StarNode node = open.removeFirst();
			if (node.equals(goalNode)) {
				return construct(node);
			} else {
				for (StarNode n : node.getNeighbors()) {
					Location location = game.jumpItem(new Location(n.getLocation().x, n.getLocation().y));
					n = new StarNode(new Point(location.getX(), location.getY()));
					if (!open.contains(n) && !close.contains(n) && n.isHit(map)) {
						n.searchParent = node;
						open.add(n);
						close.add(n);
					}
				}
			}
		}
		return null;
	}

	public static List<Point> construct(StarNode node) {

		LinkedList<Point> path = new LinkedList<Point>();
		while (node != null) {
			path.addFirst(node.location);
			node = node.searchParent;
		}
		return path;
	}
}
