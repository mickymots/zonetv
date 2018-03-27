package converter


import play.api.libs.json._
import models.Node
/**
  * Created by amit on 26/03/18.
  */
trait NodeConverter {

    implicit val nodeWrites = new Writes[Node] {
      def writes(node: Node) = Json.obj(
        "id" -> node.id
      )
    }

}
