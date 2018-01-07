package blog


import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.ApplicationCallPipeline
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.auth.*
import org.jetbrains.ktor.auth.AuthenticationPipeline.Companion.RequestAuthentication
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.http.Cookie
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.request.tryReceive
import org.jetbrains.ktor.request.uri
import org.jetbrains.ktor.response.header
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.post
import org.jetbrains.ktor.routing.route
import org.jetbrains.ktor.util.NoopContinuation.context
import org.jetbrains.ktor.util.ValuesMap

var usr = ""
var pswd = ""

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("BlogAppKt"), module = Application::module).start()
}

fun Application.module() {
    install(DefaultHeaders)
    install(CallLogging)
    install(CustomHeader) { // Install a custom feature
        headerName = "Hello" // configuration
        headerValue = "World"
    }
/*    intercept(ApplicationCallPipeline.Call) {
      //  call.response.cookies. ("se_id",2)
     val x = call.request.cookies.get("se_id")!!
        call.response.cookies.append(name = "se_id1", value = x, path = "/")

       // call.item = 1
       // call.response.cookies .header("hellow", "man")

    }
*/
    install(Routing) {
        route("/login") {

    //        intercept(AuthenticationPipeline.RequestAuthentication ) { context ->
    //            {
//
  //              }
   //         }

        //    authentication {
                println("test")
            //    basicAuthentication("Hasan") { credentials ->
         /*       formAuthentication(){ up: UserPasswordCredential ->
                    when {
                        up.password == "ppp" -> UserIdPrincipal(up.name)
                        else -> null
                    }
                }
            }
*/
            get {
                val principal = call.authentication.principal<UserIdPrincipal>()
                if (principal != null) {
                    call.respondText("Hello, ")
                 //   call.respondText("Success, ${call.principal<UserIdPrincipal>()?.name}")
                } else {
                    call.respondText("sorry, ${call.request.cookies.get("se_id")!!}")
                }
            }

        }
        route("/") {
                    val html = createHTML().html {
                        body {
                            form(action = "/",
                                    encType = FormEncType.applicationXWwwFormUrlEncoded,
                                    method = FormMethod.post) {
                                p {
                                    +"user:"
                                    textInput(name = "user") {
                                    }
                                }

                                p {
                                    +"password:"
                                    passwordInput(name = "password")
                                }

                                p {
                                    submitInput() { value = "Login" }
                                }
                            }
                        }
                    }
            get {
                call.respondText(html, ContentType.Text.Html)
            }

            post {
                val post = call.receive<ValuesMap>()
                usr = post!!["name"].toString()
                pswd = post!!["password"].toString()
              //  context .usr = usr
                call.respondRedirect("/login")
                val x = call.request.cookies.get("se_id")!!
                call.response.cookies.append(name = "se_id1", value = x, path = "/")
            }
        }
    }
}