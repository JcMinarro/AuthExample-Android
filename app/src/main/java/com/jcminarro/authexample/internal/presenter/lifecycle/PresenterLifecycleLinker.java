package com.jcminarro.authexample.internal.presenter.lifecycle;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jcminarro.authexample.internal.interactor.InteractorExecutor;
import com.jcminarro.authexample.internal.presenter.BasePresenter;
import com.jcminarro.authexample.internal.presenter.Presenter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

public class PresenterLifecycleLinker {

    private final Set<BasePresenter> presenters = new HashSet<>();
    private static final Collection<Class<?>> NON_PRESENTED_CLASSES =
            Collections.unmodifiableCollection(
                    Arrays.asList(Activity.class, Fragment.class, View.class, Object.class));
    private final InteractorExecutor executor;

    @Inject
    public PresenterLifecycleLinker(InteractorExecutor executor) {
        this.executor = executor;
    }

    public void initialize(BasePresenter.View view) {
        addAnnotatedPresenters(view);
        setView(view);
        setExecutor();
        initializePresenters();
    }

    /**
     * Initializes all the already registered presenters lifecycle.
     */
    private void initializePresenters() {
        for (BasePresenter presenter : presenters) {
            presenter.initialize();
        }
    }

    /**
     * Updates all the already registered presenters lifecycle and updates the view instance
     * associated to these presenters.
     *
     * @param view to be updated for every registered presenter.
     */
    public void updatePresenters(BasePresenter.View view) {
        if (view == null) {
            throw new IllegalArgumentException(
                    "The view instance used to update the presenters can't be null");
        }
        for (BasePresenter presenter : presenters) {
            presenter.setView(view);
            presenter.update();
        }
    }

    /**
     * Pauses all the already registered presenters lifecycle.
     */
    public void pausePresenters() {
        for (BasePresenter presenter : presenters) {
            presenter.pause();
            presenter.resetView();
        }
    }

    /**
     * Destroys all the already registered presenters lifecycle.
     */
    public void destroyPresenters() {
        for (BasePresenter presenter : presenters) {
            presenter.destroy();
        }
    }

    private void addAnnotatedPresenters(Object source) {
        addAnnotatedPresenters(source.getClass(), source);
    }

    private void addAnnotatedPresenters(Class clazz, Object source) {
        if (isOneOfOurNonPresentedClass(clazz)) {
            return;
        }
        addAnnotatedPresenters(clazz.getSuperclass(), source);

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Presenter.class)) {
                if (Modifier.isPrivate(field.getModifiers())) {
                    throw new PresenterNotAccessibleException(
                            "Presenter must be accessible for this class. The visibility modifier used can't be"
                                    + " private");
                } else {
                    try {
                        field.setAccessible(true);
                        BasePresenter presenter = (BasePresenter) field.get(source);
                        registerPresenter(presenter);
                        presenter.addViewInterfaces(clazz.getInterfaces());
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        PresenterNotAccessibleException exception = new PresenterNotAccessibleException(
                                "The presenter " + field.getName() + " into class " + clazz.getCanonicalName()
                                        + " can not be accessed");
                        exception.initCause(e);
                        throw exception;
                    } catch (ClassCastException e) {
                        throw new PresenterAnnotationException(
                                "The annotation " + Presenter.class.getCanonicalName() + " is being used on an object" +
                                        " that is not a " + BasePresenter.class.getCanonicalName() + " on the class " +
                                        clazz.getCanonicalName()
                        );
                    }
                }
            }
        }
    }

    private boolean isOneOfOurNonPresentedClass(Class clazz) {
        return NON_PRESENTED_CLASSES.contains(clazz);
    }

    private void registerPresenter(BasePresenter presenter) {
        if (presenter == null) {
            throw new IllegalArgumentException("The presenter instance to be registered can't be null");
        }
        presenters.add(presenter);
    }

    private void setView(BasePresenter.View view) {
        for (BasePresenter presenter : presenters) {
            presenter.setView(view);
        }
    }

    private void setExecutor() {
        for (BasePresenter presenter : presenters) {
            presenter.setExecutor(executor);
        }
    }
}