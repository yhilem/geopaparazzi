

/*
 * The JTS Topology Suite is a collection of Java classes that
 * implement the fundamental operations required to validate a given
 * geo-spatial data set to a known topological specification.
 *
 * Copyright (C) 2001 Vivid Solutions
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, contact:
 *
 *     Vivid Solutions
 *     Suite #1A
 *     2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www.vividsolutions.com
 */
package org.locationtech.jts.operation.overlay;

import org.locationtech.jts.algorithm.LineIntersector;
import org.locationtech.jts.geomgraph.Edge;
import org.locationtech.jts.geomgraph.index.EdgeSetIntersector;
import org.locationtech.jts.geomgraph.index.SegmentIntersector;
import org.locationtech.jts.geomgraph.index.SimpleMCSweepLineIntersector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Nodes a set of edges.
 * Takes one or more sets of edges and constructs a
 * new set of edges consisting of all the split edges created by
 * noding the input edges together
 * @version 1.7
 */
public class EdgeSetNoder {

  private LineIntersector li;
  private List inputEdges = new ArrayList();

  public EdgeSetNoder(LineIntersector li) {
    this.li = li;
  }

  public void addEdges(List edges)
  {
    inputEdges.addAll(edges);
  }

  public List getNodedEdges()
  {
    EdgeSetIntersector esi = new SimpleMCSweepLineIntersector();
    SegmentIntersector si = new SegmentIntersector(li, true, false);
    esi.computeIntersections(inputEdges, si, true);
//Debug.println("has proper int = " + si.hasProperIntersection());

    List splitEdges = new ArrayList();
    for (Iterator i = inputEdges.iterator(); i.hasNext(); ) {
      Edge e = (Edge) i.next();
      e.getEdgeIntersectionList().addSplitEdges(splitEdges);
    }
    return splitEdges;
  }
}