package org.eclipse.papyrus.gef4.scopes;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.gef.mvc.fx.parts.IContentPart;

import com.google.inject.ScopeAnnotation;

/**
 * Identifies dependencies that should be instantiated again
 * for each {@link IContentPart}.
 * Note: this annotation is not compatible with GEF Adapters, as
 * adapters injection is deferred. The PartScope will not longer
 * be active when the Adapters are injected.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ TYPE, METHOD })
@ScopeAnnotation
public @interface PartScoped {

}
