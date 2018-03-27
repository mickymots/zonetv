package services

import scala.collection.mutable
import models.Node
/**
  * Created by amit on 26/03/18.
  */
class NodeService {

  var nodes: List[Node] = List()


  def addNode(input: Node): Boolean = {

     val node =   nodes.filter(x => x.id == input.id)
     node match {

       case Nil => {
         nodes = input :: nodes
         true
       }

       case _ => {
         println("at least one node with  given id already exists")
         false
       }
     }

  }

  def listNodes() = nodes

  def findChildNodes(id: Int, name: String) =  nodes.filter(node => node.parentId == id && node.name.startsWith(name))


}
