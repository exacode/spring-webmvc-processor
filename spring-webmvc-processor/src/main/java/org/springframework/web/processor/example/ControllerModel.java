package org.springframework.web.processor.example;

import org.springframework.web.processor.routing.UriBuilder;
import org.springframework.web.processor.routing.UriBuilder.SimpleUriBuilder;

public class ControllerModel {

	public SimpleUriBuilder getUsers() {
		return new SimpleUriBuilder("/users");
	}

	public SimpleUriBuilder getUser(String id) {
		SimpleUriBuilder uriBuilder = new SimpleUriBuilder("/users/{userId}");
		uriBuilder.addPathVariable("userId", id);
		return uriBuilder;
	}

	public GetUsersOptionalParams getUser2(String id) {
		return new GetUsersOptionalParams("/users");
	}

	public static class GetUsersOptionalParams extends
			UriBuilder<GetUsersOptionalParams> {

		public GetUsersOptionalParams(String uriTemplate) {
			super(uriTemplate);
		}

		@Override
		protected GetUsersOptionalParams self() {
			return this;
		}

		public GetUsersOptionalParams id(String id) {
			addRequestParameter("id", id);
			return this;
		}

	}

}
