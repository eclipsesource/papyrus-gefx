package org.eclipse.papyrus.gef4.gmf.services;

import java.util.function.Predicate;

import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.gef4.gmf.utils.GMFPartUtil;
import org.eclipse.papyrus.gef4.parts.BaseContentPart;
import org.eclipse.papyrus.gef4.services.DeclarativeProviderParticipant;

import com.google.inject.Provider;

public class GMFProviderParticipant<T> extends DeclarativeProviderParticipant<T> {

	public GMFProviderParticipant(Provider<T> instanceProvider) {
		super(instanceProvider);
	}

	public GMFProviderParticipant(double priority, Provider<? extends T> instanceProvider) {
		super(priority, instanceProvider);
	}

	public GMFProviderParticipant(double priority, Provider<? extends T> instanceProvider,
			Class<? extends BaseContentPart<? extends View, ?>> forClass) {
		super(priority, instanceProvider, forClass);
	}

	@SafeVarargs
	public GMFProviderParticipant(double priority, Provider<? extends T> instanceProvider,
			Class<? extends BaseContentPart<? extends View, ?>> forClass,
			Class<? extends BaseContentPart<? extends View, ?>>... forClasses) {
		super(priority, instanceProvider, forClass, forClasses);
	}

	public GMFProviderParticipant(double priority, Provider<? extends T> instanceProvider,
			Predicate<BaseContentPart<? extends View, ?>> matcher) {
		super(priority, instanceProvider, wrapMatcher(matcher));
	}

	private static Predicate<IVisualPart<?>> wrapMatcher(Predicate<BaseContentPart<? extends View, ?>> matcher) {
		return visualPart -> {
			BaseContentPart<? extends View, ?> basePart = GMFPartUtil.getBasePart(visualPart);
			return basePart == null ? false : matcher.test(basePart);
		};
	}

	@Override
	public double getPriority(IVisualPart<?> part) {
		if (GMFPartUtil.isBaseNotationPart(part)) {
			return super.getPriority(part);
		}
		return Double.NaN;
	}

}
