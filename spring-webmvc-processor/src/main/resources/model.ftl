<#if (!controller.type.defaultPackage)>package ${controller.type.packageName};</#if>

<#if controller.hasOptionalParameterMappings()>
import org.springframework.web.processor.routing.UriBuilder;
</#if>
import org.springframework.web.processor.routing.UriBuilder.SimpleUriBuilder;

public class ${controller.type.className} {
	
	<#list controller.methods as method>
	<#if method.hasNoParameters()>
	public static SimpleUriBuilder ${method.declaration} {
		return new SimpleUriBuilder("${method.uri}");
	}
	
	<#elseif method.hasOptionalParameters()>
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