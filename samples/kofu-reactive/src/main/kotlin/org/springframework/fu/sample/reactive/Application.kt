/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.fu.sample.reactive

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.kofu.application
import org.springframework.boot.kofu.mongo.embedded
import org.springframework.boot.kofu.mongo.mongodb
import org.springframework.boot.kofu.ref
import org.springframework.boot.kofu.web.jackson
import org.springframework.boot.kofu.web.mustache
import org.springframework.boot.kofu.web.server

val app = application {
	beans {
		bean<UserRepository>()
		bean<UserHandler>()
		bean {
			routes(ref())
		}
	}
	listener<ApplicationReadyEvent> {
		ref<UserRepository>().init()
	}
	properties<SampleProperties>("sample")
	server {
		port = if (profiles.contains("test")) 8181 else 8080
		mustache()
		codecs {
			string()
			jackson()
		}
	}

	mongodb {
		embedded()
	}
}

fun main(args: Array<String>) = app.run()
