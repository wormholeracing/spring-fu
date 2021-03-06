package org.springframework.boot.kofu.samples

import org.springframework.boot.kofu.application
import org.springframework.boot.kofu.web.client
import org.springframework.boot.kofu.web.jackson

fun clientDsl() {
	application {
		client {
			codecs {
				string()
				jackson()
			}
		}
	}
}
