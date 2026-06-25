package utilities;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

		annotation.setRetryAnalyzer(RetryAnalyzer.class);
		
        // Apply retry only for regression group
        if (Arrays.asList(annotation.getGroups()).contains("regression")) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
	}
}