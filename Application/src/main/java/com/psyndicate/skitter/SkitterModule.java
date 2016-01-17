package com.psyndicate.skitter;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.psyndicate.skitter.model.MockSkitterProvider;
import com.psyndicate.skitter.model.SkitterProvider;

/**
 * Module to define injection bindings
 */
public class SkitterModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(SkitterProvider.class).to(MockSkitterProvider.class);
    }
}
