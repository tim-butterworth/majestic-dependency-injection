package com.reflecty.testModels;

import com.reflecty.annotations.Singleton;
import com.reflecty.providers.ObjectProvider;

@Singleton
public interface NodeChainProvider extends ObjectProvider<NodeChain> {}
