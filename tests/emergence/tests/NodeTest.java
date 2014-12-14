package emergence.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ontology.Types.ACTIONS;

import org.junit.BeforeClass;
import org.junit.Test;

import core.game.Game;
import core.game.StateObservation;
import emergence.nodes.GenericNode;
import emergence.nodes.Node;
import emergence.util.LevelLoader;

public class NodeTest {

	
	private static StateObservation stateObs = null;

	@BeforeClass
	public static void setUp() {
		Game g = LevelLoader.load("firestorms0");
		stateObs = g.getObservation();
	}
	
	
	@Test
	public void getAllChildrenTest() {
		Node root = new Node(stateObs);
		Set<GenericNode<Object>> children = root.getAllChildren();
		
		assertTrue(root.getLastAction() == ACTIONS.ACTION_NIL);
		assertTrue(children.size() == stateObs.getAvailableActions().size());
		
		for (GenericNode<Object> node : children) {
			assertTrue(node.getLevel() == 1);
		}
	}
	
	
	@Test
	public void iteratorTest() {
		Node root = new Node(stateObs);
		Set<GenericNode<Object>> children = root.getAllChildren();
		
		Set<GenericNode<Object>> itChildren = new HashSet<GenericNode<Object>>();
		Iterator<GenericNode<Object>> it = root.iterator();
		
		while (it.hasNext()) {
			itChildren.add(it.next());
		}
		
		assertEquals(children.size(), itChildren.size());
	}
	
	

}
