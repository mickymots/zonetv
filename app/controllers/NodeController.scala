package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

import play.api.libs.json._ // JSON library
import play.api.libs.json.Reads._ // Custom validation helpers
import play.api.libs.functional.syntax._ // Combinator syntax

import converter.NodeConverter
import services.NodeService
import models.Node
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class NodeController @Inject()(cc: ControllerComponents, nodeService: NodeService) extends AbstractController(cc)
  {

    implicit val nodeWrites = new Writes[Node] {
      def writes(node: Node) = Json.obj(
        "id" -> node.id,
        "name" -> node.name,
        "parentId" -> node.parentId
      )
    }

    val nodeReadsBuilder =
      (JsPath \ "id").read[Int] and
        (JsPath \ "name").read[String] and
          (JsPath \ "parentId").read[Int]

    implicit val nodeReads = nodeReadsBuilder.apply(Node.apply _)


    /**
      * This method adds a new node to the application
      * @return
      */
  def addNode() = Action(parse.json) { implicit request =>
    //Get request body
    val nodeJson = request.body

    //1. parse the request body json to Node model
    //2. match the parse result
    //3. if success call service
    //4. else return 400 Bad Request response code with message

    nodeJson.validate[Node] match {
      case s: JsSuccess[Node] => {
        val node: Node = s.get

        val success = nodeService addNode node
        if(success)
          Ok("node added")
        else
          Ok("duplicate Node not added")
      }
      case e: JsError => {
        println("Not a valid node json")
        BadRequest("Bad request - Invalid input JSON")
      }
    }

  }

    /**
      * This method list all the nodes of Application
      * */
    def listNodes() = Action { implicit request =>
      Ok(Json.toJson(nodeService listNodes))
    }

    /**
      * This nodes finds the child nodes of a given node and returns them as a response
      * @return
      */
    def findChildNodes(id: Int, name: String) = Action { implicit request =>
      println(s"id = $id, name=$name")
      Ok(Json.toJson(nodeService.findChildNodes(id, name)))
    }


}

