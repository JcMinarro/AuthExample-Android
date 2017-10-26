package com.jcminarro.authexample.internal.presenter;

import com.jcminarro.authexample.internal.interactor.AsyncInteractor;
import com.jcminarro.authexample.internal.interactor.InteractorExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class BasePresenter<T extends BasePresenter.View> {

    /**
     * Represents the View component inside the Model View Presenter pattern. This interface must be
     * used as base interface for every View interface declared.
     */
    public interface View {

    }

    private T view;
    private LinkedHashSet<Class<?>> viewImplementedInterfaces = new LinkedHashSet<>();
    private InteractorExecutor executor;

    /**
     * Method called in the presenter lifecycle. Invoked when the component containing the presenter
     * is initialized.
     */
    public void initialize() {

    }

    /**
     * Method called in the presenter lifecycle. Invoked when the component containing the presenter
     * is resumed.
     */
    public void update() {

    }

    /**
     * Method called in the presenter lifecycle. Invoked when the component containing the presenter
     * is paused.
     */
    public void pause() {

    }

    /**
     * Method called in the presenter lifecycle. Invoked when the component containing the presenter
     * is destroyed.
     */
    public void destroy() {

    }

    /**
     * Returns the view configured in the presenter which real implementation is an Activity or
     * Fragment using this presenter.
     */
    public final T getView() {
        return view;
    }

    /**
     * Configures the View instance used in this presenter as view.
     */
    public void setView(T view) {
        this.view = view;
    }

    public void addViewInterfaces(Class<?>[] interfaces) {
        viewImplementedInterfaces.addAll(Arrays.asList(interfaces));
    }

    public void setExecutor(InteractorExecutor executor) {
        this.executor = executor;
    }

    /**
     * Changes the current view instance with a dynamic proxy to avoid real UI updates.
     */
    public void resetView() {
        final List<Class<?>> viewClasses = getViewInterfaceClass();
        InvocationHandler emptyHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        };
        ClassLoader classLoader = viewClasses.get(0).getClassLoader();
        this.view = (T) Proxy.newProxyInstance(classLoader, viewClasses.toArray(new Class<?>[0]), emptyHandler);
    }

    protected <I, O, E> void execute(
            AsyncInteractor<I, O, E> interactor,
            I input,
            InteractorExecutor.Callback<O, E> callback) {
        executor.execute(interactor, input, callback);
    }

    protected <I, O, E> void execute(
            AsyncInteractor<I, O, E> interactor,
            I input) {
        execute(interactor, input, new InteractorExecutor.NullCallback<O, E>());
    }

    private List<Class<?>> getViewInterfaceClass() {
        Class<?> matchingClass = BasePresenter.View.class;
        List<Class<?>> interfaceClasses = new ArrayList<>();

        for (Class<?> interfaceCandidate : viewImplementedInterfaces) {
            if (matchingClass.isAssignableFrom(interfaceCandidate)) {
                interfaceClasses.add(interfaceCandidate);
            }
        }

        return interfaceClasses;
    }
}
