import io.javalin.Javalin
import org.apache.commons.mail.*

fun main(args: Array<String>) {

    val app = Javalin.create()
            .port(7000)
            .start()

    app.get("/") { ctx ->
        ctx.html("""
                <form action="/contact-us" method="post">
                    <input name="subject" placeholder="Subject">
                    <br>
                    <textarea name="message" placeholder="Your message ..."></textarea>
                    <br>
                    <button>Submit</button>
                </form>
        """.trimIndent())
    }

    app.post("/contact-us") { ctx ->
        SimpleEmail().apply {
            setHostName("smtp.gmail.com")
            setSmtpPort(465)
            setAuthenticator(DefaultAuthenticator("hasan.Oryx@gmail.com", "Hasan0Maha"))
            setSSLOnConnect(true)
            setFrom("hasan.Oryx@gmail.com")
            setSubject(ctx.formParam("subject"))
            setMsg(ctx.formParam("message"))
            addTo("hasan.Oryx@gmail.com")
        }.send() // will throw email-exception if something is wrong
        ctx.redirect("/contact-us/success")
    }

    app.get("/contact-us/success") { ctx -> ctx.html("Your message was sent") }

}