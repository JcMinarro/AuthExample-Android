package com.jcminarro.authexample.internal.localdatasource;

import com.jcminarro.authexample.internal.network.OAuth;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SessionDatasource {

    void storeOAuthSession(@NotNull OAuth auth);

    @Nullable
    OAuth getOAuthSession();
}
