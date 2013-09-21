Annotation processor for models annotated with `@Controller`
============================================================

An annotation processor for [spring-webmvc](http://projects.spring.io/spring-framework/) controllers. 


What it does?
-------------

- generates meta model for all HTTP GET `@RequestMapping`s declared inside classes annotated with `@Controller`
- enables creation of stringless routings


How to configure?
-----------------

### Maven
First invoke *spring-webmvc-processor* during maven build. In order to do this have a look at an example project: [spring-data-mongodb-processor-example](https://github.com/mendlik/spring-data-mongodb-processor/blob/master/spring-webmvc-processor-example/pom.xml#L63).

### Eclipse IDE
To ease the usage of annotation processor use Eclipse IDE with m2e eclipse plugin and configure it to invoke annotation processor automatically:
* Right-click on your project > Properties > Maven > Annotation processing
* Select the Annotation Processing strategy: *Delegate Annotation Processing to maven plugins*. 


Usage example
-------------

Using this annotation processor and [spring-webmvc](http://projects.spring.io/spring-framework/) you can create stringless queries like:

		@Controller
		public class UserController {

			@RequestMapping(value = "/users")
			public String getUsers() {
				return "users.ftl";
			}

		}

		@Controller
		public class SomeFormController {

			@RequestMapping(value = "/some-form", method = RequestMethod.POST)
			public String submitSomeForm() {
				// Some form processing...
				return UserController_.getUsers().mvc().redirect(); // generates: "redirect:/users"
			}
		
		}


Maven repository
----------------

In order to use this library add [repository](http://github.com/mendlik/mvn-repo) location in your `pom.xml`:

		<repositories>
		    <repository>
		        <id>mendlik-releases</id>
		        <url>https://github.com/mendlik/mvn-repo/raw/master/releases</url>
		    </repository>
		</repositories>

