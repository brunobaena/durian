/*
 * Original Guava code is copyright (C) 2015 The Guava Authors.
 * Modifications from Guava are copyright (C) 2016 DiffPlug.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.common.util.concurrent;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.function.Function;

/**
 * Unit tests for {@link Futures#transform(ListenableFuture, Function)}.
 *
 * @author Nishant Thakkar
 */
public class FuturesTransformTest
		extends AbstractChainedListenableFutureTest<String> {
	private static final String RESULT_DATA = "SUCCESS";

	@Override
	protected ListenableFuture<String> buildChainingFuture(
			ListenableFuture<Integer> inputFuture) {
		return Futures.transform(inputFuture,
				new ComposeFunction());
	}

	@Override
	protected String getSuccessfulResult() {
		return RESULT_DATA;
	}

	private class ComposeFunction
			implements Function<Integer, String> {
		@Override
		public String apply(Integer input) {
			if (input.intValue() == VALID_INPUT_DATA) {
				return RESULT_DATA;
			} else {
				throw new UndeclaredThrowableException(EXCEPTION);
			}
		}
	}

	public void testFutureGetThrowsFunctionException() throws Exception {
		inputFuture.set(EXCEPTION_DATA);
		listener.assertException(EXCEPTION);
	}
}