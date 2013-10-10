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
First invoke *spring-webmvc-processor* during maven build. In order to do this have a look at an example project: [spring-webmvc-processor-example](https://github.com/exacode/spring-webmvc-processor/blob/master/spring-webmvc-processor-example/pom.xml#L63).
 
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
			// Note that all optional parameters are chained therefore also optional in meta controller.
		
		}


Maven dependency
----------------

In order to use this library add [repository](http://github.com/exacode/mvn-repo) location into your `pom.xml` 
and add appropriate dependencies and build plugin.

		</dependencies>
			<dependency>
				<groupId>net.exacode.spring.web</groupId>
				<artifactId>spring-webmvc-processor-shared</artifactId>
				<version>${version.spring-webmvc-processor}</version>
			</dependency>
		</dependencies>

		<build>
			<plugins>
				<plugin>
					<groupId>org.codehaus.mojo</groupId>
					<artifactId>build-helper-maven-plugin</artifactId>
					<version>1.7</version>
					<executions>
						<execution>
							<id>add-test-source</id>
							<phase>generate-test-sources</phase>
							<goals>
								<goal>add-test-source</goal>
							</goals>
							<configuration>
								<sources>
									<source>${project.build.directory}/generated-sources/apt</source>
								</sources>
							</configuration>
						</execution>
					</executions>
				</plugin>
				<plugin> 
					<groupId>org.bsc.maven</groupId>
					<artifactId>maven-processor-plugin</artifactId>
					<version>2.2.4</version>
					<executions>
						<execution>
							<id>process</id>
							<goals>
								<goal>process</goal>
							</goals>
							<phase>generate-sources</phase>
							<configuration>
								<processors>
									<processor>net.exacode.spring.web.processor.ControllerProcessor</processor>
								</processors>
							</configuration>
						</execution>
					</executions>
					<dependencies>
						<dependency>
							<groupId>net.exacode.spring.web</groupId>
							<artifactId>spring-webmvc-processor</artifactId>
							<version>${version.spring-webmvc-processor}</version>
						</dependency>
					</dependencies>
				</plugin>
			</plugins>
		</build>

<a href='http://www.pledgie.com/campaigns/22342'><img alt='Click here to lend your support to: Exacode open projects and make a donation at www.pledgie.com !' src='http://www.pledgie.com/campaigns/22342.png?skin_name=chrome' border='0' /></a>
