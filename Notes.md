PLAYVISION Play Framework Notes
===

## Installation

### Java on Ubuntu

See http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html

			sudo add-apt-repository ppa:webupd8team/java
			sudo apt-get update
			sudo apt-get install oracle-java8-installer

			sudo update-java-alternatives -s java-7-oracle

			sudo update-java-alternatives -s java-8-oracle

## TODO

* Template Model https://blackrockdigital.github.io/startbootstrap-modern-business/index.html. Use: HOME, SERVICES, CONTACT, MORE (BLOG, DE, FR, EN)
* 404 Page
* Footer Social Links


* Include: Bildfilter für `<img src="img/playvision.png" alt="LOGO" /></li>` nach ` <img src="@routes.Assets("/docs", "home/img/playvision.png")" alt="LOGO" /></li>`
* Java Markdown Parser, der `akutell_de.md` in `aktuell_de.html`übersetzt. Evtl. auch in PDF zum Download und drucken.
* docs aus `@Includes.at("docs/home", "aktuell.html")`, dh. der Ort wo das CMS verzeichnis ist, konfigurierbar machen.
* Kontakt Form Posts in Exel schreiben
* Links im Kontent immer in neuem Fenster aufmachen
* Cache für Files vom File System im Prod Mode.
* Install and Set up Monit for Monitoring - Monit is a very useful monitoring tool that helps rescue your server from failed processes. Install it through apt-get: `apt-get install monit` see <http://www.exratione.com/2012/05/a-mailserver-on-ubuntu-1204-postfix-dovecot-mysql/>


External Content Server
---
Other approach can be just using other subdomain for serving public resources such as images/CSS/JS etc. set with some light HTTP server. So you can just prefix your files with it, ie:

	<img src="http://cdn.myproject.tld/layout/logo.png" />

main benefits:

* You can change these files without redeploying the application via common FTP

* as it's on other domain then main app browser doesn't send a cookies for retrieving every file

* you can set advanced caching rules for these files only with your HTTP server

* you can move whole set easily to some professional CDN offering geo-replication

Of course for more comfortable usage in the future it's good idea to write some small method which will allow you to easy change the host, based ie. on custom config ie:

	<img src='@App.cdnFile("my-cdn", "layout/logo.png")' />
	<img src='@App.cdnFile("azure-storage1", "layout/logo.png")' />
	<img src='@App.cdnFile("amazon-s3", "layout/logo.png")' />


### External Assets

In Play 2.1 there is an [`ExternalAssets`] [1] controller:

 	GET     /test/*file      controllers.ExternalAssets.at(path="/var/www/efco/images", file)

The source file can be backported to Play 2.0.x.

[1]: https://github.com/playframework/Play20/blob/master/framework/src/play/src/main/scala/play/api/controllers/ExternalAssets.scala



Inside the `Build.scala` file you can specify local assets:

	val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
	    ebeanEnabled := true,
	    playAssetsDirectories <+= baseDirectory / "/trombi",
	    playAssetsDirectories <+= baseDirectory / "Images"
	 )

my routes are like this

	GET     /assets/*file       controllers.Assets.at(path="/public",file)
	GET     /trombi/*file       controllers.Assets.at(path="/trombi", file)
	GET     /DossierImages/*file          controllers.Assets.at(path="/Images", file)

and i use like this

	<img src="@routes.Assets.at("/public","images/Tux.png")" width="2%" height="2%" alt="" />
	<img src="@routes.Assets.at("/public","images/link/bouriquet.png")" width="2%" height="2%" alt="" />
	<img src="@routes.Assets.at("/trombi","Tuxa.png")" width="2%" height="2%" alt="bad url" />
	<img src="@routes.Assets.at("/Images","bouriquet.png")" width="2%" height="2%" alt="bad url" />

### Parsing HTML
<http://www.hars.de/2009/01/html-as-xml-in-scala.html>
<http://about.validator.nu/htmlparser/>
Parsing HTML5 in Browser <http://blog.whatwg.org/html5-live-dom-in-javascript>

#### Regex Matching

Tutorial:
<http://www.javacodegeeks.com/2011/10/scala-tutorial-regular-expressions_05.html>

Mit Java Testcases für Bilderendungen
<http://www.mkyong.com/regular-expressions/how-to-validate-image-file-extension-with-regular-expression/>

Sun Tutorial <http://docs.oracle.com/javase/tutorial/essential/regex/index.htmlY>


## Proxy Server
1. Download a windows distribution of lighttpd and install.
2. Copy your javascript library to your LightTPD\htdocs  folder. In my case, I used extjs. I copied the extjs folder to   LightTPD\htdocs folder.
3. Open the LightTPD\conf\lighttpd.conf in notepad.
4. In server.modules uncomment the "mod_proxy" line.
5. At the end of the file add the following lines. Then start the lighthttpd server. Run your play application. Now, extjs will be served by lightttpd and all other urls will be forwarded to 127.0.0.1:9000. Now use the http://localhost URL to launch your play application.


		$HTTP["url"] !~ "/extjs/" {
    		proxy.balance = "round-robin" proxy.server = ( "/" =>
        		( ( "host" => "127.0.0.1", "port" => 9000 ) ) )
		}



# Play Framework

## SBT Console

To do an experiment with a play feature open the sbt console:

	  [playvision] $ console
	  [info] Starting scala interpreter...
	  [info]
	  Welcome to Scala version 2.11.1 (Java HotSpot(TM) 64-Bit Server VM, Java 1.6.0_65).
	  Type in expressions to have them evaluated.
	  Type :help for more information.

Then  start your play app context

    scala> new play.core.StaticApplication(new java.io.File("."))
    [info] application - Application has started
    [info] play - Application started (Prod)
    res2: play.core.StaticApplication = play.core.StaticApplication@490586be


Load the feature to explore

	scala> import play.api.cache.Cache
	import play.api.cache.Cache

Here we use Plays default cache implementation. Be sure to include `cach` into the `build.sbt` `libraryDependencies`:

	libraryDependencies ++= Seq(
	  cache,
	  ...
	)

Now lets try it out:

	scala> Cache.set("item.key", 123.5)

	scala> Cache.get("item.key")
	res5: Option[Any] = Some(123.5)

	scala> Cache.getOrElse("item.key") {300}
	res7: Int = 300

	scala> Cache.get("item.key")
	res8: Option[Any] = Some(300)



## Build It

<https://github.com/playframework/Play20/wiki/BuildingFromSource>

Eclipse Plugin <http://stackoverflow.com/questions/10053201/how-to-setup-eclipse-ide-work-on-the-playframework-2-0/10055419#10055419>

# Java Installieren

http://www.webupd8.org/2012/09/install-oracle-java-8-in-ubuntu-via-ppa.html

	sudo apt-get install oracle-java8-set-default

Native Packaging https://github.com/playframework/playframework/issues/1765



### Routing
There are potentially many ways to address this, which way will be best highly depends on your specific needs, how many routes specific to particular domains you have, etc.

One way to investigate is using multiple routers, and implementing your own Global.onRouteRequest method that uses them.  First of all, upgrade to Play 2.1, and the latest snapshot of Play 2.1 (2.1-RC1 has a bug which will cause problems with multiple routers in the one project, this is fixed in the latest master).

Now, write your main routes file as normal, and for the individual domains, put their routes in files called domain1.routes, domain2.routes etc.  Now, in Global.onRouteRequest, you want to do something roughly like this:

	val domainSpecificRouters = Map("domain1.com" -> domain1.Routes, "domain2.com" -> domain2.Routes)
	override def onRouteRequest(rh: RequestHeader) = {
	  Routes.handlerFor(rh).orElse {
	    for {
	      domainSpecificRouter <- domainSpecificRouters.get(rh.host)
	      handler <- domainSpecificRouter.handlerFor(rh)
	    } yield handler
	  }
	}

The main advantage of the built-in Router/ReverseRouter in Play 2.0, is its type-safety. If your routing scheme is so dynamic it is probably not a good idea to hack it this way.

The Routing component is fully pluggable and you can handle the Routing any way you want into the `onRouteRequest` method. We give you a request header (so you have access to the URL, HTTP method, and HTTP headers), and you give back the handler you want to use to serve the request.

You have the flexibility to write any business logic you want in the middle.

([Source](https://groups.google.com/forum/?fromgroups=#!searchin/play-framework/play$20domain$20name$20get$20scala/play-framework/PkPGHRh4rd4/dAouke5g8-0J))

### EMail
<https://github.com/typesafehub/play-plugins/tree/master/mailer>

Path is wrong in readme - try "com.typesafe" % "play-plugins-mailer_2.9.1" % "2.0.4". notice _2.9.1.



### I18n
Example
<https://groups.google.com/forum/#!searchin/play-framework/change$20implicit$20lang/play-framework/8LRKHNm9D4Q/eWDjfyr4U9AJ>

### Simple Content Management
#### Markdown Datei Namen

Namensgebung: `name.[de|fr].[h|l|m|r].[nr].md`

* Name: Dateiname klein schreiben. Keine *Punkte* und *Leerzeichen* im Namen verwenden! Keine *Umlaute* verwenden.
* Sprache: de, fr: Text in Deutsch, Französich
* Layoout: h = Headline, l = left, m = middle, r = right
* Ordnung: nr = 1, 2, 3.. n - 1 kommt vor 2 in der Spalte

#### Markdown Editoren
Syntax Übersicht [http://www.simplecode.me/2011/12/11/getting-started-with-markdown/][^1]

Für Mac <http://mouapp.com/>.

[^1]:(http://www.simplecode.me/2011/12/11/getting-started-with-markdown/)

[EpicEditor](http://oscargodson.github.com/EpicEditor/)

See also [https://github.com/sebnitu/epiceditor-for-wordpress](https://github.com/sebnitu/epiceditor-for-wordpress)

Matilda [http://www.matildaapp.com/editor/](http://www.matildaapp.com/editor/)

### Application Versioning
Um appVersion automatisch zu vergeben:

	 import scala.compat.Platform
     val appVersion = "1.0." + Platform.currentTime

das ergibt:

	scala> Platform.currentTime
	res1: Long = 1355352651028  

`sbtbuild-info`Integration [Beispiel](https://gist.github.com/2714592).

### Serving Assets from a proxy server:

Here is my workaround for this issue using lighttpd. Until, play team fixes the resource loading slowness in development mode, this can be used as a workaround.

1. Download a windows distribution of lighttpd and install.
2. Copy your javascript library to your LightTPD\htdocs  folder. In my case, I used extjs. I copied the extjs folder to   LightTPD\htdocs folder.
3. Open the LightTPD\conf\lighttpd.conf in notepad.
4. In server.modules uncomment the "mod_proxy" line.
5. At the end of the file add the following lines. Then start the lighthttpd server. Run your play application. Now, extjs will be served by lightttpd and all other urls will be forwarded to 127.0.0.1:9000. Now use the http://localhost URL to launch your play application.

	$HTTP["url"] !~ "/extjs/" {
	    proxy.balance = "round-robin" proxy.server = ( "/" =>
	        ( ( "host" => "127.0.0.1", "port" => 9000 ) ) )
	}

### Mongo DB
To have launchd start mongodb at login:

    ln -sfv /usr/local/opt/mongodb/*.plist ~/Library/LaunchAgents

Then to load mongodb now:

    launchctl load -w ~/Library/LaunchAgents/homebrew.mxcl.mongodb.plist

Or, if you don't want/need launchctl, you can just run:

    mongod

### Forms
Use [HTML5 Tags](https://github.com/loicdescotte/Play2-HTML5Tags/tree/master/samples/scala-form) when on Scala 2.10 and Play 2.1

An alternative is [Play2-HTML5Tags](https://github.com/loicdescotte/Play2-HTML5Tags)

### Building the App
`sbt clean compile stage dist`

		[info] Your package is ready in target/universal/playvision-n.n.n.zip


### AWS


#### Play starten

(Play mit `sudo nohup ./target/start -Dhttp.port=80 & `  starten und Console und prozess mit `ctrl-c` stoppen. `ctrl-c`beendet nur die Console.)


Play mit `sudo nohup ./target/universal/stage/bin/playvision -mem 512 -Dhttp.port=80 & `  starten und Console und prozess mit `ctrl-c` stoppen. `ctrl-c`beendet nur die Console.

Auf dem Server: `sudo nohup ./playvision-0.10.8/bin/playvision -mem 512 -Dhttp.port=80 &
`

vim  target/universal/stage/bin/playvision -> MEMORY auf von 1024 auf 512 HERUNTER SETZEN: `get_mem_opts ()``

#### Logs

Neu `ls -l playvision-0.10/logs/application.log`

* logs/application.log sichern cp logs/application.log app-0.6.1.log
* Prozess mit RUNNING_PID stoppen
* mv target target_old
* unzip target..zip


