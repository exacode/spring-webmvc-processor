Annotation processor of SpringMvc Controllers
=============================================

An annotation processor for [spring-webmvc](http://projects.spring.io/spring-framework/) controllers. 


What it does?
-------------

- generates meta model for all HTTP GET `@RequestMapping`s declared inside classes annotated with `@Controller`
- enables creation of stringless routings


How to configure?
-----------------

### Maven
First invoke *spring-webmvc-processor* during maven build. In order to do this have a look at an example project: [spring-webmvc-processor-example](https://github.com/mendlik/spring-data-mongodb-processor/blob/master/spring-webmvc-processor-example/pom.xml#L63).

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

This annotation processor handles also parameters of every type ([@RequestParam](http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html), [@PathVariable](http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/bind/annotation/PathVariable.html), [@MatrixParameter](http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/web/bind/annotation/MatrixVariable.html)) and both required and optional:

		@Controller
		public class UserController {

			@RequestMapping(value = "/users/{userId}/{resource}")
			public void getUserResource(@PathVariable String userId, @PathVariable String resource) {
				// ...
			}

			// metamodel: UserController_.getUserResource("user123", "image").mvc().redirect(); 
			// generates: "redirect:/users/user123/image"
			// Note that all required parameters are also required by generated meta controller.

			@RequestMapping(value = "/users/{userId}")
			public void getUser(@PathVariable String userId, @RequestParameter(required=false) String query) {
				// ...
			}

			// metamodel: UserController_.getUsers("user123").query("some-value").mvc().redirect(); 
			// generates: "redirect:/users/user123?query=some-value"
			// Note that all optional parameters are chained and optional in meta controller.
		
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

