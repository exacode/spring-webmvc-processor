<#if (!controller.type.defaultPackage)>package ${controller.type.packageName};</#if>

<#if controller.hasOptionalParameterMappings()>
import net.exacode.spring.web.processor.shared.routing.UriBuilder;
</#if>
import net.exacode.spring.web.processor.shared.routing.UriBuilder.SimpleUriBuilder;
<#assign author="spring-webmvc-processor" />

/**
 * Spring WebMvc meta model of {@link ${controller.qualifiedControllerName}}.
 * 
 * @author ${author}
 */
public class ${controller.type.className} {
	
	<#list controller.methods as method>
	<#if method.hasNoParameters()>
	/**
	 * Represents request mapping from
	 * {@link ${controller.qualifiedControllerName}#${method.originalMethodName}}
	 * 
	 * @return {@link SimpleUriBuilder} for method's request mapping
	 */
	public static SimpleUriBuilder ${method.declaration} {
		return new SimpleUriBuilder("${method.uri}");
	}
	
	<#elseif method.hasOptionalParameters()>
	/**
	 * Represents request mapping from
	 * {@link ${controller.qualifiedControllerName}#${method.originalMethodName}}
	 * with optional parameters.
	 * 
	 * @return {@link ${method.optionalParametersClassName}}
	 */
	public static ${method.optionalParametersClassName} ${method.declaration} {
		${method.optionalParametersClassName} uriBuilder = new ${method.optionalParametersClassName}("${method.uri}");
		<#list method.pathVariables as param>
		uriBuilder.addPathVariable("${param.bindingName}", ${param.methodParameterName});
		</#list>
		<#list method.requestParameters as param>
		uriBuilder.addRequestParameter("${param.bindingName}", ${param.methodParameterName});
		</#list>
		<#list method.matrixVariables as param>
		<#if param.pathName??>
		uriBuilder.addMatrixVariable("${param.pathName}", "${param.bindingName}", ${param.methodParameterName});
		<#else>
		uriBuilder.addMatrixVariable("${param.bindingName}", ${param.methodParameterName});
		</#if>
		</#list>
		return uriBuilder;
	}
	
	<#else>
	/**
	 * Represents request mapping from
	 * {@link ${controller.qualifiedControllerName}#${method.originalMethodName}}.
	 * 
	 * @return {@link SimpleUriBuilder} for method's request mapping
	 */
	public static SimpleUriBuilder ${method.declaration} {
		SimpleUriBuilder uriBuilder = new SimpleUriBuilder("${method.uri}");
		<#list method.pathVariables as param>
		uriBuilder.addPathVariable("${param.bindingName}", ${param.methodParameterName});
		</#list>
		<#list method.requestParameters as param>
		uriBuilder.addRequestParameter("${param.bindingName}", ${param.methodParameterName});
		</#list>
		<#list method.matrixVariables as param>
		<#if param.pathName??>
		uriBuilder.addMatrixVariable("${param.pathName}", "${param.bindingName}", ${param.methodParameterName});
		<#else>
		uriBuilder.addMatrixVariable("${param.bindingName}", ${param.methodParameterName});
		</#if>
		</#list> 
		return uriBuilder;
	}
	
	</#if>
	</#list>
	
	<#list controller.mappingsOptionalParameters as optParams>
	/**
	 * Spring WebMvc meta model of {@link ${controller.qualifiedControllerName}}
	 * with optional parameters.
	 * 
	 * @author ${author}
	 */
	public static class ${optParams.className} extends
			UriBuilder<${optParams.className}> {
		
		public ${optParams.className}(String uriTemplate) {
			super(uriTemplate);
		}

		<#list optParams.pathVariables as param>
		public ${optParams.className} ${param.methodParameterName}(${param.declaration}) {
			this.addPathVariable("${param.bindingName}", ${param.methodParameterName});
			return this;
		}
		</#list>
		<#list optParams.requestParameters as param>
		public ${optParams.className} ${param.methodParameterName}(${param.declaration}) {
			this.addRequestParameter("${param.bindingName}", ${param.methodParameterName});
			return this;
		}
		</#list>
		<#list optParams.matrixVariables as param>
		public ${optParams.className} ${param.methodParameterName}(${param.declaration}) {
			<#if param.pathName??>
			this.addMatrixVariable.addMatrixVariable("${param.pathName}", "${param.bindingName}", ${param.methodParameterName});
			<#else>
			this.addMatrixVariable.addMatrixVariable("${param.bindingName}", ${param.methodParameterName});
			</#if>
			return this;
		}
		</#list> 
		
		@Override
		protected ${optParams.className} self() {
			return this;
		}
		
	}
	
	</#list>
}