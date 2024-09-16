package com.intland.codebeamer.integration.test;

import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import com.intland.codebeamer.integration.CodebeamerPage;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.util.HttpStatus;
import com.microsoft.playwright.ConsoleMessage;
import com.microsoft.playwright.Page;

public class ConsoleErrorLogListener implements IInvokedMethodListener {

	private static final Logger logger = LogManager.getLogger();
	
	private static final String CODEBEAMER_PAGE = "codebeamerPage";
	
	private static final String CONSOLE_ERRORS = "consoleErrors";

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		if (!method.isTestMethod()) {
			return;
		}
		
		try {
			List<ConsoleError> consoleErrors = getConsoleErrors(method, testResult);
			CodebeamerPage codebeamerPage = getCodebeamerPage(testResult);
	
			if (isFailed(testResult)) {
				String errorMessage = buildErrorMessage(testResult.getThrowable().getMessage(), consoleErrors);
				testResult.setThrowable(new AssertionError(errorMessage, testResult.getThrowable()));
			} else {
				checkConsoleError(codebeamerPage, consoleErrors).ifPresent(e -> {
					testResult.setThrowable(e);
					testResult.setStatus(ITestResult.FAILURE);
				});
			}
		} finally {
			clearAttribute(testResult, CONSOLE_ERRORS);
			clearAttribute(testResult, CODEBEAMER_PAGE);
		}
	}

	public static void initContext(ITestResult result, ITestContext testContext, Page page, CodebeamerPage codebeamerPage) {
		LinkedList<ConsoleError> consoleErrors = new LinkedList<ConsoleError>();
		
		setConsoleErrors(result, testContext, consoleErrors);
		setCodebeamerPage(result, testContext, codebeamerPage);
		
		page.onConsoleMessage(msg -> {
			if ("error".equals(msg.type()) && isApplicable(msg)) {
				consoleErrors.add(new ConsoleError(msg.text(), msg.location()));
			}
		});
	}

	// XXX: Temporally fix until we have no better solution
	private static boolean isApplicable(ConsoleMessage msg) {
		if (msg.text().startsWith("The Cross-Origin-Opener-Policy header has been ignored")) {
			return false;
		}
		
		return true;
	}
	
	private String buildErrorMessage(String message, List<ConsoleError> consoleErrors) {
		if (CollectionUtils.isEmpty(consoleErrors)) {
			return message;
		}
		
		StringBuilder builder = new StringBuilder(message)
				.append(System.lineSeparator())
				.append("Console errors:");
	
		IntStream.range(0, consoleErrors.size()).forEach(i -> {
			ConsoleError consoleError = consoleErrors.get(i);
			builder.append(System.lineSeparator());
			builder.append("%s. %s - %s".formatted(Integer.valueOf(i + 1), consoleError.message(), consoleError.source()));
		});
		
		return builder.toString();
	}
	
	private Optional<AssertionError> checkConsoleError(CodebeamerPage codebeamerPage, List<ConsoleError> consoleErrors) {
		if (CollectionUtils.isEmpty(consoleErrors)) {
			return Optional.empty();
		}
		
		try {
			codebeamerPage.getAssertions().assertThat("Test class was executed without any error, but the browser console contains errors", () -> {
				assertTrue(CollectionUtils.isEmpty(consoleErrors));
			});
		} catch (AssertionError e) {
			return Optional.of(new AssertionError(buildErrorMessage(e.getMessage(), consoleErrors), e.getCause()));
		}
		
		return Optional.empty();
	}
	
	private boolean isFailed(ITestResult result) {
		return result.getStatus() == ITestResult.FAILURE;
	}
	
	private CodebeamerPage getCodebeamerPage(ITestResult testResult) {
		return getAttribute(testResult, CODEBEAMER_PAGE);
	}

	private List<ConsoleError> getConsoleErrors(IInvokedMethod method, ITestResult testResult) {
		List<ConsoleError> consoleErrors = getAttribute(testResult, CONSOLE_ERRORS);
		if (consoleErrors == null) {
			return Collections.emptyList();
		}

		HttpStatus[] expectedHttpStatuses = getExpectedHttpStatuses(method);

		List<String> statusCodes = Arrays.stream(expectedHttpStatuses)
				.filter(status -> status != HttpStatus.NONE)
				.map(HttpStatus::getCodeAsString)
				.toList();

		return consoleErrors.stream()
				.filter(e -> statusCodes.stream().noneMatch(e.message()::contains))
				.toList();
	}

	private HttpStatus[] getExpectedHttpStatuses(IInvokedMethod method) {
		TestCase testCaseAnnotation = method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotation(TestCase.class);

		if (testCaseAnnotation == null) {
			return new HttpStatus[] { HttpStatus.NONE };
		}

		return testCaseAnnotation.expectedHttpErrors();
	}

	private static void setConsoleErrors(ITestResult testResult, ITestContext testContext, LinkedList<ConsoleError> consoleErrors) {
		setAttribute(testResult, testContext, CONSOLE_ERRORS, consoleErrors);
	}

	private static void setCodebeamerPage(ITestResult testResult, ITestContext testContext, CodebeamerPage codebeamerPage) {
		setAttribute(testResult, testContext, CODEBEAMER_PAGE, codebeamerPage);
	}
	
	private static void setAttribute(ITestResult testResult, ITestContext testContext, String attributeName, Object value) {
		String uniqueAttributeName = "%s-%s".formatted(getContextKey(testResult), attributeName);
		logger.debug("Value is stored in the context with key: %s, value: %s".formatted(uniqueAttributeName, value));
		testContext.setAttribute(uniqueAttributeName, value);
	}
	
	private static <T> T getAttribute(ITestResult testResult, String attributeName) {
		String uniqueAttributeName = "%s-%s".formatted(getContextKey(testResult), attributeName);
		return (T) testResult.getTestContext().getAttribute(uniqueAttributeName);
	}
	
	private static void clearAttribute(ITestResult testResult, String attributeName) {
		testResult.getTestContext().removeAttribute("%s-%s".formatted(getContextKey(testResult), attributeName));
	}
	
	private static String getContextKey(ITestResult result) {
		String packageName = StringUtils.replace(result.getMethod().getRealClass().getPackageName(), ".", "_");
		String className = result.getMethod().getRealClass().getSimpleName();
		String methodName = result.getMethod().getMethodName();
		return "%s_%s_%s".formatted(packageName, className, methodName);
	}
	
	record ConsoleError(String message, String source) {}
	
}