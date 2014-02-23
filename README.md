Annotation processor for SpringWebMvc Controllers
================================================

[![Flattr this!](https://api.flattr.com/button/flattr-badge-large.png)](https://flattr.com/submit/auto?user_id=exacode&url=https://github.com/exacode/spring-webmvc-processor&tags=spring,spring-webmvc,preprocessor,java,code,github&category=software) 
[![Build Status](https://travis-ci.org/exacode/spring-webmvc-processor.png?branch=master)](https://travis-ci.org/exacode/spring-webmvc-processor)

An annotation processor for [spring-webmvc](http://projects.spring.io/spring-framework/) controllers. 


What it does?
-------------

- generates meta model for all HTTP GET `@RequestMapping`s declared inside classes annotated with `@Controller`
- enables creation of stringless routings


How to configure?
-----------------

### Maven

Take look on an [example project](https://github.com/exacode/spring-webmvc-processor/blob/master/spring-webmvc-processor-example/pom.xml#L56).

In order to use this processor add [repository](http://github.com/exacode/mvn-repo) location into your `pom.xml` 
and add processor dependency.

		</dependencies>
			<dependency>
				<groupId>net.exacode.spring.web</groupId>
				<artifactId>spring-webmvc-processor-shared</artifactId>
				<version>${version.spring-webmvc-processor}</version>
			</dependency>
		</dependencies>


Although there is also a [second configuration option](https://github.com/exacode/spring-webmvc-processor/blob/master/spring-webmvc-processor-example/pom2.xml#L83), the first one is more IDE friendly.

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

