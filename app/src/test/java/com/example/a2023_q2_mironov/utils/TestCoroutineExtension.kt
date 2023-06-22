package com.example.a2023_q2_mironov.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class TestCoroutineExtension : BeforeEachCallback, AfterEachCallback {

	override fun beforeEach(context: ExtensionContext?) {
		Dispatchers.setMain(UnconfinedTestDispatcher())
	}

	override fun afterEach(context: ExtensionContext?) {
		Dispatchers.resetMain()
	}
}