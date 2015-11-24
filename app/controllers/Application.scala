package controllers

import com.google.inject.Inject
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.i18n.{MessagesApi, I18nSupport}
import models.Task

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }
}
