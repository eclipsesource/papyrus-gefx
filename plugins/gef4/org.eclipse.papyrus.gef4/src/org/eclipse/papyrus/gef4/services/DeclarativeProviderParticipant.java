package org.eclipse.papyrus.gef4.services;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.inject.Provider;

public class DeclarativeProviderParticipant<T> implements HelperProviderParticipant<T> {

	private static final Predicate<IVisualPart<?>> ANY_MATCHER = any -> true;
	
	private final Provider<? extends T> instanceProvider;
	private final double priority;
	private final Predicate<IVisualPart<?>> matcher;
	
	public DeclarativeProviderParticipant(Provider<? extends T> instanceProvider) {
		this(Double.NEGATIVE_INFINITY, instanceProvider, ANY_MATCHER);
	}
	
	public DeclarativeProviderParticipant(double priority, Provider<? extends T> instanceProvider) {
		this(priority, instanceProvider, ANY_MATCHER);
	}
	
	public DeclarativeProviderParticipant(double priority, Provider<? extends T> instanceProvider, Class<? extends IVisualPart<?>> forClass) {
		this(priority, instanceProvider, forClass::isInstance);
	}
	
	@SafeVarargs
	public DeclarativeProviderParticipant(double priority, Provider<? extends T> instanceProvider, Class<? extends IVisualPart<?>> forClass,
			Class<? extends IVisualPart<?>>... forClasses) {
		this(priority, instanceProvider, instance -> Stream.concat(Stream.of(forClass), Arrays.stream(forClasses))
				.anyMatch(c -> c.isInstance(instance)));
	}
	
	public DeclarativeProviderParticipant(double priority, Provider<? extends T> instanceProvider, Predicate<IVisualPart<?>> matcher) {
		this.instanceProvider = instanceProvider;
		this.priority = priority;
		this.matcher = matcher;
	}
	
	@Override
	public T get(IVisualPart<?> part) {
		return instanceProvider.get();
	}

	@Override
	public double getPriority(IVisualPart<?> part) {
		return matcher.test(part) ? priority : Double.NaN;
	}

}
